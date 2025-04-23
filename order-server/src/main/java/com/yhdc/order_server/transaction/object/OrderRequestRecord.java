package com.yhdc.order_server.transaction.object;

import java.util.List;

public record OrderRequestRecord(String userId,
                                 List<StoreData> storeDataList,
                                 String totalPrice,
                                 UserDetail userDetail) {

    public record UserDetail(String username, String userEmail){}

}
