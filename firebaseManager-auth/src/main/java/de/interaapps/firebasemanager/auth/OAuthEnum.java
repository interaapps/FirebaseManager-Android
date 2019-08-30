package de.interaapps.firebasemanager.auth;

import org.jetbrains.annotations.Contract;

public enum OAuthEnum {

    GITHUB("github.com"),
    MICROSOFT("microsoft.com"),
    TWITTER("twitter.com"),
    YAHOO("yahoo.com");

    private String providerID;
    private OAuthEnum(String providerID) {
        this.providerID = providerID;
    }

    @Contract(pure = true)
    public String getProviderID() {
        return providerID;
    }
}
