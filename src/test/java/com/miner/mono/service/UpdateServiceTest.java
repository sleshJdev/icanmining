package com.miner.mono.service;

import com.miner.mono.dto.WalletDto;
import com.miner.mono.enums.Coin;
import com.miner.mono.enums.Currency;
import com.miner.mono.persistence.model.ExchangeRate;
import com.miner.mono.persistence.model.WalletStat;
import com.miner.mono.persistence.repository.ExchangeRateRepository;
import com.miner.mono.persistence.repository.WalletStatRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureMockRestServiceServer;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.http.client.MockClientHttpResponse;
import org.springframework.test.web.client.MockRestServiceServer;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static com.miner.mono.service.UpdateService.ADDR_PARAM;
import static java.util.Collections.singletonMap;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.springframework.test.web.client.ExpectedCount.times;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;
import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;


@AutoConfigureWebClient(registerRestTemplate = true)
@AutoConfigureMockRestServiceServer
public class UpdateServiceTest extends AbstractServiceTest {
    @Autowired
    private UpdateService updateService;
    @Autowired
    private MockRestServiceServer mockRestServiceServer;
    @Autowired
    private WalletService walletService;
    @Autowired
    private WalletStatRepository walletStatRepository;
    @Autowired
    private ExchangeRateRepository exchangeRateRepository;
    @Value("${app.nicehash.wallet_stats_url_format}")
    private String walletStatsUrlFormat;
    @Value("${app.nicehash.info_url}")
    private String infoUrl;
    private String walletStatsUrl;

    @Before
    public void setUp() {
        walletService.updateBalance(BigDecimal.ZERO);
        walletStatsUrl = fromHttpUrl(walletStatsUrlFormat)
                .buildAndExpand(singletonMap(ADDR_PARAM, walletService.findWalletAddress()))
                .toUriString();
    }

    @Test
    public void updateExchangeRate() {
        BigDecimal expectedExchangeRate = BigDecimal.valueOf(123.4);
        mockRestServiceServer.expect(requestTo(infoUrl))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withSuccess(
                        String.format(
                                "{\"result\": {\"exchanges\":[{\"USD\":\"%f\",\"coin\":\"BTC\"}]}}",
                                expectedExchangeRate.doubleValue()),
                        MediaType.APPLICATION_JSON_UTF8
                ));
        updateService.updateExchangeRate();
        ExchangeRate exchangeRate = exchangeRateRepository.findByCurrency(Currency.USD);
        assertThat(exchangeRate.getCoin(), equalTo(Coin.BTC));
        assertThat(exchangeRate.getCurrency(), equalTo(Currency.USD));
        assertThat(exchangeRate.getRate(), comparesEqualTo(expectedExchangeRate));
        mockRestServiceServer.verify();
    }

    @Test
    public void updateBalance() throws InterruptedException {
        BigDecimal[] balances = {
                BigDecimal.valueOf(1.3D),
                BigDecimal.valueOf(0.3D),
                BigDecimal.valueOf(0.4D)};
        BigDecimal balance = BigDecimal.valueOf(1.4D);//1.3 + delta(0.4-0.3)
        AtomicInteger reqNo = new AtomicInteger();
        mockRestServiceServer
                .expect(times(3), requestTo(walletStatsUrl))
                .andExpect(method(HttpMethod.GET))
                .andRespond(request -> new MockClientHttpResponse(
                        String.format(
                                "{\"result\": {\"payments\": [{\"amount\": %f}]}}",
                                balances[reqNo.getAndIncrement()].doubleValue()
                        ).getBytes(StandardCharsets.UTF_8),
                        HttpStatus.OK));

        //TODO: TimeUnit.SECONDS.sleep(1); -
        //TODO: rework sql query or find a way how to prevent none unique result by max(date)
        updateService.updateBalance();
        TimeUnit.SECONDS.sleep(1);
        updateService.updateBalance();
        TimeUnit.SECONDS.sleep(1);
        updateService.updateBalance();

        List<WalletStat> walletStats = walletStatRepository.findAll();
        assertThat(walletStats, hasSize(3));

        WalletDto wallet = walletService.findWallet();
        assertThat(wallet.getBalance(), comparesEqualTo(balance));
        mockRestServiceServer.verify();
    }
}