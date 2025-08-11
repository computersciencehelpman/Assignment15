package com.coderscampus.Assignment15.service;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RecaptchaResponse {

    private boolean success;

    @JsonProperty("challenge_ts")
    private String challengeTs;

    private String hostname;

    // Getters
    public boolean isSuccess() {
        return success;
    }

    public String getChallengeTs() {
        return challengeTs;
    }

    public String getHostname() {
        return hostname;
    }

    // Setters
    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setChallengeTs(String challengeTs) {
        this.challengeTs = challengeTs;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }
}
