package by.miner.mono.schedule;

import by.miner.mono.persistence.model.ExchangeRate;
import by.miner.mono.persistence.model.Wallet;
import by.miner.mono.persistence.repository.ExchangeRateRepository;
import by.miner.mono.persistence.repository.WalletRepository;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.util.List;

@Service
public class BalanceUpdateService {
    private static final String WALLET_STATS_URL_FORMAT = "https://api.nicehash.com/api?method=stats.provider&addr=%s";
    private static final String INFO_URL = "https://api.nicehash.com/api?method=nicehash.service.info";

    private final WalletRepository walletRepository;
    private final ExchangeRateRepository exchangeRateRepository;
    private final RestTemplate restTemplate;
    private static final ObjectMapper mapper = new ObjectMapper();

    public BalanceUpdateService(WalletRepository walletRepository, ExchangeRateRepository exchangeRateRepository,
                                RestTemplate restTemplate) {
        this.walletRepository = walletRepository;
        this.exchangeRateRepository = exchangeRateRepository;
        this.restTemplate = restTemplate;
    }

    @Scheduled(fixedDelayString = "${app.exchange-rate.update.delay}")
    public void updateExchangeRate() throws IOException {
        ResponseEntity<String> exchange = restTemplate.exchange(
                new RequestEntity<>(HttpMethod.GET, URI.create(INFO_URL)), String.class);
        ArrayNode exchanges = (ArrayNode) mapper.readTree(exchange.getBody()).get("result").get("exchanges");
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
        String url = String.format(WALLET_STATS_URL_FORMAT, wallet.getAddress());
        ResponseEntity<String> exchange = restTemplate.exchange(
                new RequestEntity<>(HttpMethod.GET, URI.create(url)), String.class);
        List<JsonNode> balances = mapper.readTree(exchange.getBody())
                .get("result").get("stats").findValues("balance");
        BigDecimal balance = balances.stream()
                .map(JsonNode::asDouble)
                .map(BigDecimal::valueOf)
                .reduce(BigDecimal.ZERO, BigDecimal::add, BigDecimal::add);
        wallet.setBalance(balance);
        walletRepository.save(wallet);
    }

}
