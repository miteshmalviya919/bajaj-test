package com.Mitesh.Bajaj.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WebhookResponse {
    @JsonProperty("webhookUrl")
    private String webhookUrl;

    @JsonProperty("accessToken")
    private String accessToken;

    public String getWebhookUrl() {
        return webhookUrl;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
