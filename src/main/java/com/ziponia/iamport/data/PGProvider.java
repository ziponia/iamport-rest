package com.ziponia.iamport.data;

public enum PGProvider {
    KAKAO("kakao"),
    INICIS_HTML5("html5_inicis"),
    INICIS("inicis"),
    NICE("nice"),
    JTNET("jtnet"),
    UPLUS("uplus"),
    DANAL("danal"),
    PAYCO("payco"),
    SYRUP("syrup"),
    PAYPAL("paypal");

    private String name;

    PGProvider(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
