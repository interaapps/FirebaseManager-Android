package de.interaapps.firebasemanager.core.auth;

public enum  ProviderEnum {
    GOOGLE("google.com"),
    GITHUB("github.com"),
    ANONYM("anonym"),
    MICROSOFT("microsoft.com"),
    TWITTER("twitter.com"),
    FACEBOOK("facebook"),
    EMAIL("password");

    private String providerId;
    ProviderEnum(String providerId) {
        this.providerId = providerId;
    }

    public String getProviderId() {
        return providerId;
    }
}
