package com.yhdc.video_catalog_service.transaction.object;

public record VideoInfoSaveRecord(String userId, String productId,
                                  String fileName, String description) {
}
