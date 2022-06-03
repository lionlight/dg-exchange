package ru.alfa.app.services.enums;

public enum OpenExchangeRatesJsonProperties {
    DISCLAIMER("disclaimer"),
    LICENSE("license"),
    TIMESTAMP("timestamp"),
    BASE("base"),
    RATES("rates");

    private final String propertyName;

    OpenExchangeRatesJsonProperties(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyName() {
        return this.propertyName;
    }
}
