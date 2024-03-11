package org.example.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.exception.InformationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;

import static org.example.client.AccountRegistration.registerAccountsAndReturnTheirNames;

public class UUIDGetter {

    final static String GET_ACCOUNT_ID_URL = "https://admin-billing-2.testms-test.lognex.ru/api/bones/1.0/account?name=";
    static final Logger logger = LoggerFactory.getLogger(InformationException.class);
    static ObjectMapper objectMapper = new ObjectMapper();
    static List<String> identificatorsOfAccounts = new ArrayList<>();
    static WebClient client = WebClient.create();
    public static List getAccountUUIDs() throws JsonProcessingException {

        for (Object accountName : registerAccountsAndReturnTheirNames()) {
            WebClient.ResponseSpec responseSpec = client.get()
                    .uri(GET_ACCOUNT_ID_URL + accountName)
                    .retrieve();
            String responseBody = responseSpec.bodyToMono(String.class).block();
            identificatorsOfAccounts.add(parseResponse(responseBody));
        }
        logger.info(identificatorsOfAccounts.toString());
        return identificatorsOfAccounts;
    }

    private static String parseResponse(String responseBody) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(responseBody);
        return jsonNode.get("response").get("id").asText();
    }
}
