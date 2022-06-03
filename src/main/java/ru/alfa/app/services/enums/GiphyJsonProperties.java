package ru.alfa.app.services.enums;

public enum GiphyJsonProperties {
    DATA("data"), IMAGES("images"), ORIGINAL("original"), URL("url");

    private final String propertyName;

    GiphyJsonProperties(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyName() {
        return this.propertyName;
    }
}
