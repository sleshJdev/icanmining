package by.miner.mono.schedule;

import by.miner.mono.persistence.model.ExchangeRate;
import by.miner.mono.persistence.model.Wallet;
import by.miner.mono.persistence.repository.ExchangeRateRepository;
import by.miner.mono.persistence.repository.WalletRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

@Component
public class UpdateSchedule {
    private final String walletStatsUrlFormat;
    private final String infoUrl;
    private final WalletRepository walletRepository;
    private final ExchangeRateRepository exchangeRateRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public UpdateSchedule(@Value("${app.nicehash.wallet_stats_url_format}") String walletStatsUrlFormat,
                          @Value("${app.nicehash.info_url}") String infoUrl,
                          WalletRepository walletRepository,
                          ExchangeRateRepository exchangeRateRepository,
                          RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.walletStatsUrlFormat = walletStatsUrlFormat;
        this.infoUrl = infoUrl;
        this.walletRepository = walletRepository;
        this.exchangeRateRepository = exchangeRateRepository;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @Scheduled(fixedDelayString = "${app.exchange-rate.update.delay}")
    public void updateExchangeRate() throws IOException {
        ResponseEntity<String> exchange = restTemplate.exchange(
                new RequestEntity<>(HttpMethod.GET, URI.create(infoUrl)), String.class);
        ArrayNode exchanges = (ArrayNode) objectMapper.readTree(exchange.getBody()).get("result").get("exchanges");
        for (ExchangeRate exchangeRate : exchangeRateRepository.findAll()) {
            for (int i = 0; i < exchanges.size(); i++) {
                ObjectNode rate = (ObjectNode) exchanges.get(i);
                if (rate.has(exchangeRate.getCurrency().name()) &&
                        rate.get("coin").asText().equals(exchangeRate.getCoin().name())) {
                    exchangeRate.setRate(BigDecimal.valueOf(rate.get(exchangeRate.getCurrency().name()).asDouble()));
                    exchangeRateRepository.save(exchangeRate);
                }
            }
        }
    }

    @Transactional
    @Scheduled(fixedDelayString = "${app.wallet.update.delay}")
    public void updateBalance() throws IOException {
        Wallet wallet = walletRepository.findWallet();
        String url = String.format(walletStatsUrlFormat, wallet.getAddress());
        ResponseEntity<String> exchange = restTemplate.exchange(
                new RequestEntity<>(HttpMethod.GET, URI.create(url)), String.class);
        List<JsonNode> balances = objectMapper.readTree(exchange.getBody())
                .get("result").get("stats").findValues("balance");
        BigDecimal balance = balances.stream()
                .map(JsonNode::asDouble)
                .map(BigDecimal::valueOf)
                .reduce(BigDecimal.ZERO, BigDecimal::add, BigDecimal::add);
        wallet.setBalance(balance);
        walletRepository.save(wallet);
    }
}