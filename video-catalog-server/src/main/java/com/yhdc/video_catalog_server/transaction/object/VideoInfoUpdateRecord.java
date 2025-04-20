package com.yhdc.video_catalog_server.transaction.object;

public record VideoInfoUpdateRecord(String videoInfoId, String title, String description, String videoPath) {
}
