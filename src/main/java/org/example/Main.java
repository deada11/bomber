package org.example;


import com.fasterxml.jackson.core.JsonProcessingException;

import static org.example.client.UUIDGetter.getAccountUUIDs;


public class Main {
    public static void main(String[] args) throws JsonProcessingException {

        getAccountUUIDs();

    }
}