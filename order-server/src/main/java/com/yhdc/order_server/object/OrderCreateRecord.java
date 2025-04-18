package com.yhdc.order_server.object;

import java.util.List;

public record OrderCreateRecord(String userId, List<StoreData> storeDataList, String totalPrice) {
}
