package com.Mitesh.Bajaj.service;

import com.Mitesh.Bajaj.model.WebhookRequest;
import com.Mitesh.Bajaj.model.WebhookResponse;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WebhookService {

    private final RestTemplate restTemplate = new RestTemplate();

    public void runChallengeFlow() {

        WebhookRequest request = new WebhookRequest("Mitesh Malviya", "0827CI221091", "miteshmalviya220856@acropolis.in");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<WebhookRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<WebhookResponse> response = restTemplate.postForEntity(
                "https://bfhldevapigw.healthrx.co.in/hiring/testWebhook/JAVA",
                entity,
                WebhookResponse.class
        );

        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            String webhookUrl = response.getBody().getWebhookUrl();
            String token = response.getBody().getAccessToken();

            // 2. Hardcoded SQL query for now
            String finalQuery = "SELECT \n" +
                    "    p.AMOUNT AS SALARY,\n" +
                    "    CONCAT(e.FIRST_NAME, ' ', e.LAST_NAME) AS NAME,\n" +
                    "    TIMESTAMPDIFF(YEAR, e.DOB, CURDATE()) AS AGE,\n" +
                    "    d.DEPARTMENT_NAME\n" +
                    "FROM \n" +
                    "    PAYMENTS p\n" +
                    "JOIN \n" +
                    "    EMPLOYEE e ON p.EMP_ID = e.EMP_ID\n" +
                    "JOIN \n" +
                    "    DEPARTMENT d ON e.DEPARTMENT = d.DEPARTMENT_ID\n" +
                    "WHERE \n" +
                    "    DAY(p.PAYMENT_TIME) != 1\n" +
                    "ORDER BY \n" +
                    "    p.AMOUNT DESC\n" +
                    "LIMIT 1;";

            submitFinalQuery(webhookUrl, token, finalQuery);
        }
    }

    private void submitFinalQuery(String webhookUrl, String token, String finalQuery) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        String jsonBody = "{ \"finalQuery\": \"" + finalQuery.replace("\"", "\\\"") + "\" }";

        HttpEntity<String> entity = new HttpEntity<>(jsonBody, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(webhookUrl, entity, String.class);
        System.out.println("Submission response: " + response.getBody());
    }
}
