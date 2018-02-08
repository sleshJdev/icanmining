package by.miner.mono.schedule;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class BalanceUpdateTaskTest {

    @Autowired
    private BalanceUpdateService balanceUpdateService;

    @Test
    public void testUpdateBalance() throws IOException {
        balanceUpdateService.updateBalance();
    }

    @Test
    public void testCalculateBalance() throws JSONException, IOException {
        String response =
                "{\n" +
                "   \"result\":{\n" +
                "      \"stats\":[\n" +
                "         {\n" +
                "            \"balance\":\"0.0000063\",\n" +
                "            \"rejected_speed\":\"0\",\n" +
                "            \"algo\":8,\n" +
                "            \"accepted_speed\":\"0\"\n" +
                "         },\n" +
                "         {\n" +
                "            \"balance\":\"0.00086064\",\n" +
                "            \"rejected_speed\":\"0\",\n" +
                "            \"algo\":14,\n" +
                "            \"accepted_speed\":\"0.0357914\"\n" +
                "         },\n" +
                "         {\n" +
                "            \"balance\":\"0.00000014\",\n" +
                "            \"rejected_speed\":\"0\",\n" +
                "            \"algo\":21,\n" +
                "            \"accepted_speed\":\"0\"\n" +
                "         },\n" +
                "         {\n" +
                "            \"balance\":\"0.0001283\",\n" +
                "            \"rejected_speed\":\"0\",\n" +
                "            \"algo\":22,\n" +
                "            \"accepted_speed\":\"0.00000067\"\n" +
                "         },\n" +
                "         {\n" +
                "            \"balance\":\"0.00005801\",\n" +
                "            \"rejected_speed\":\"0\",\n" +
                "            \"algo\":24,\n" +
                "            \"accepted_speed\":\"0\"\n" +
                "         }\n" +
                "      ],\n" +
                "      \"payments\":[ ],\n" +
                "      \"addr\":\"3J85FLJrjyqz8Dsj5F9pJ9kkQkqJsByt15\"\n" +
                "   },\n" +
                "   \"method\":\"stats.provider\"\n" +
                "}";
        ObjectMapper mapper = new ObjectMapper();
        List<JsonNode> balances = mapper.readTree(response).get("result").get("stats").findValues("balance");
        BigDecimal balance = balances.stream()
                .map(JsonNode::asDouble)
                .map(BigDecimal::valueOf)
                .reduce(BigDecimal.ZERO, BigDecimal::add, BigDecimal::add);
        Assert.assertTrue(balance.doubleValue() > 0D);
    }
}