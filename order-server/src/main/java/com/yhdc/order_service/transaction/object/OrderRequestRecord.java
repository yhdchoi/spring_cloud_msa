package com.yhdc.order_service.transaction.object;

import java.util.List;

public record OrderRequestRecord(String userId,
                                 List<StoreData> storeDataList,
                                 String totalPrice,
                                 UserDetail userDetail) {

    public record UserDetail(String username, String firsName, String lastName, String userEmail){}

}
