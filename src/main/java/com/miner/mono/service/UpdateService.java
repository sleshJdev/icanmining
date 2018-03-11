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
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static com.miner.mono.util.TimeUtils.utcNowDateTime;

@Service
public class UpdateService {
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
            ResponseEntity<String> exchange = restTemplate.exchange(
                    new RequestEntity<>(HttpMethod.GET, URI.create(infoUrl)), String.class);
            ArrayNode exchanges = (ArrayNode) objectMapper.readTree(exchange.getBody())
                    .get("result").get("exchanges");
            for (ExchangeRate exchangeRate : exchangeRateRepository.findAll()) {
                for (int i = 0; i < exchanges.size(); i++) {
                    ObjectNode rate = (ObjectNode) exchanges.get(i);
                    String currency = exchangeRate.getCurrency().name();
                    String coin = exchangeRate.getCoin().name();
                    if (rate.has(currency) && rate.get("coin").asText().equals(coin)) {
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
            String url = String.format(walletStatsUrlFormat, wallet.getAddress());
            ResponseEntity<String> exchange = restTemplate.exchange(
                    new RequestEntity<>(HttpMethod.GET, URI.create(url)), String.class);
            String json = exchange.getBody();
            List<JsonNode> balances = objectMapper.readTree(json)
                    .get("result").get("payments").findValues("amount");
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
}