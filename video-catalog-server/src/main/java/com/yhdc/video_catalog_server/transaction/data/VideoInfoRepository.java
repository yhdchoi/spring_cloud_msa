package com.yhdc.video_catalog_server.transaction.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface VideoInfoRepository extends JpaRepository<VideoInfo, UUID> {

    List<VideoInfo> findAllByUserIdAndProductId(String userId, String productId);

}
