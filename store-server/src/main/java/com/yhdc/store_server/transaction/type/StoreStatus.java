package com.yhdc.store_server.transaction.type;

public enum StoreStatus {

    ACTIVE("ACTIVE", "활성"),
    INACTIVE("INACTIVE", "휴먼"),
    SUSPENDED("SUSPENDED", "정지");

    public final String selection;
    public final String koMessage;

    StoreStatus(String selection, String koMessage) {
        this.selection = selection;
        this.koMessage = koMessage;
    }

    public String selection() {
        return selection;
    }
}
