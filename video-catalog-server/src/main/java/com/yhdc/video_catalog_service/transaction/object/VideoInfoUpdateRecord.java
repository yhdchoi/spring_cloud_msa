package com.yhdc.video_catalog_service.transaction.object;

public record VideoInfoUpdateRecord(String videoInfoId, String title, String description, String videoPath) {
}
