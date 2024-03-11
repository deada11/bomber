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

public class AccountRegistration {
    final static String REGISTER_URL = "https://online-billing-2.testms-test.lognex.ru/api/remap/1.2/register";
    final static Logger logger = LoggerFactory.getLogger(InformationException.class);
    static ObjectMapper objectMapper = new ObjectMapper();
    static WebClient client = WebClient.create();
    public static List registerAccountsAndReturnTheirNames() throws JsonProcessingException {

        List<String> uuidList = new ArrayList<>();

        for (int i = 0; i <= 2; i++) {
            String responseSpec = client.post()
                    .uri(REGISTER_URL)
                    .header("content-type", "application/x-www-form-urlencoded")
                    .header("Accept", "application/json;charset=utf-8")
                    .bodyValue(buildDataUrlEncoded())
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
            uuidList.add(parseResponse(responseSpec));
            logger.info(uuidList.toString());
        }

        return uuidList;
    }

    private static String buildDataUrlEncoded() {
        String email = "pnikitin@moysklad.ru";
        String requestJson = "email=" + email;
        return requestJson;
    }

    private static String parseResponse (String responseSpec) throws JsonProcessingException {
        JsonNode jsonNode = objectMapper.readTree(responseSpec);
        return jsonNode.get("accountName").asText();
    }
}
