package com.yhdc.store_server.type;

public enum ProductStatus {

    ACTIVE("FOR_SALE", "판매중"),
    INACTIVE("SOLD_OUT", "매진"),
    SUSPENDED("SUSPENDED", "정지");

    public final String selection;
    public final String koMessage;

    ProductStatus(String selection, String koMessage) {
        this.selection = selection;
        this.koMessage = koMessage;
    }

    public String selection() {
        return selection;
    }

}
