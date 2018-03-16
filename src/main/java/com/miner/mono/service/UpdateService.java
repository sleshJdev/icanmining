package com.miner.mono.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.miner.mono.persistence.model.ExchangeRate;
import com.miner.mono.persistence.model.Wallet;
import com.miner.mono.persistence.model.WalletStat;
import com.miner.mono.persistence.repository.ExchangeRateRepository;
import com.miner.mono.persistence.repository.WalletRepository;
import com.miner.mono.persistence.repository.WalletStatRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import static com.miner.mono.util.TimeUtils.utcNowDateTime;
import static java.util.Collections.singletonMap;
import static org.springframework.web.util.UriComponentsBuilder.fromHttpUrl;

@Service
public class UpdateService {
    public static final String ADDR_PARAM = "addr";
    public static final String RESULT_PROPERTY = "result";
    public static final String PAYMENTS_PROPERTY = "payments";
    public static final String AMOUNT_PROPERTY = "amount";
    public static final String EXCHANGES_PROPERTY = "exchanges";
    public static final String COIN_PROPERTY = "coin";
    private final String walletStatsUrlFormat;
    private final String infoUrl;
    private final WalletRepository walletRepository;
    private final WalletStatRepository walletStatRepository;
    private final ExchangeRateRepository exchangeRateRepository;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    public UpdateService(@Value("${app.nicehash.wallet_stats_url_format}") String walletStatsUrlFormat,
                         @Value("${app.nicehash.info_url}") String infoUrl,
                         WalletRepository walletRepository,
                         WalletStatRepository walletStatRepository, ExchangeRateRepository exchangeRateRepository,
                         RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.walletStatsUrlFormat = walletStatsUrlFormat;
        this.infoUrl = infoUrl;
        this.walletRepository = walletRepository;
        this.walletStatRepository = walletStatRepository;
        this.exchangeRateRepository = exchangeRateRepository;
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public void updateExchangeRate() {
        try {
            ArrayNode exchanges = (ArrayNode) objectMapper.readTree(doGet(URI.create(infoUrl)))
                    .get(RESULT_PROPERTY).get(EXCHANGES_PROPERTY);
            for (ExchangeRate exchangeRate : exchangeRateRepository.findAll()) {
                for (int i = 0; i < exchanges.size(); i++) {
                    ObjectNode rate = (ObjectNode) exchanges.get(i);
                    String currency = exchangeRate.getCurrency().name();
                    String coin = exchangeRate.getCoin().name();
                    if (rate.has(currency) && rate.get(COIN_PROPERTY).asText().equals(coin)) {
                        exchangeRate.setRate(new BigDecimal(rate.get(currency).asText()));
                        exchangeRateRepository.save(exchangeRate);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void updateBalance() {
        try {
            Wallet wallet = walletRepository.findWallet();
            String json = doGet(fromHttpUrl(walletStatsUrlFormat)
                    .buildAndExpand(singletonMap(ADDR_PARAM, wallet.getAddress()))
                    .toUri());
            List<JsonNode> balances = objectMapper.readTree(json)
                    .get(RESULT_PROPERTY).get(PAYMENTS_PROPERTY).findValues(AMOUNT_PROPERTY);
            BigDecimal balance = balances.stream()
                    .map(JsonNode::asText)
                    .map(BigDecimal::new)
                    .reduce(BigDecimal.ZERO, BigDecimal::add, BigDecimal::add);

            WalletStat lastStat = walletStatRepository.findLastStat();
            BigDecimal lastBalance = Optional.ofNullable(lastStat)
                    .map(WalletStat::getBalance).orElse(BigDecimal.ZERO);

            BigDecimal income = balance.subtract(lastBalance);
            if (income.signum() > 0) {
                BigDecimal newBalance = wallet.getBalance().add(income);
                wallet.setBalance(newBalance);
                walletRepository.save(wallet);
            }

            walletStatRepository.save(new WalletStat(wallet, balance, utcNowDateTime(), json));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String doGet(URI url) {
        ResponseEntity<String> exchange = restTemplate.exchange(
                new RequestEntity<>(HttpMethod.GET, url), String.class);
        return exchange.getBody();
    }
}