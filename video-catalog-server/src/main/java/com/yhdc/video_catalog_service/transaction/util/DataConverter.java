package com.yhdc.video_catalog_service.transaction.util;

import com.yhdc.video_catalog_service.transaction.data.VideoInfo;
import com.yhdc.video_catalog_service.transaction.object.VideoInfoDto;
import com.yhdc.video_catalog_service.transaction.object.VideoInfoSaveRecord;
import org.springframework.stereotype.Component;

import java.io.File;

import static com.yhdc.video_catalog_service.transaction.type.Constants.VIDEO_BASE_DIR;

@Component
public class DataConverter {

    /**
     *
     * @param videoInfoSaveRecord
     * @return VideoInfo
     */
    public VideoInfo convertVideoInfoDtoToVideoInfo(VideoInfoSaveRecord videoInfoSaveRecord) {
        VideoInfo videoInfo = new VideoInfo();
        videoInfo.setUserId(videoInfoSaveRecord.userId());
        videoInfo.setProductId(videoInfoSaveRecord.productId());
        videoInfo.setFileName(videoInfoSaveRecord.fileName());
        videoInfo.setDescription(videoInfoSaveRecord.description());
        videoInfo.setVideoPath(VIDEO_BASE_DIR
                + videoInfoSaveRecord.userId() + File.separator
                + videoInfoSaveRecord.productId() + File.separator
                + videoInfoSaveRecord.fileName());
        return videoInfo;
    }


    /**
     *
     * @param videoInfo
     * @return VideoInfoDto
     */
    public VideoInfoDto convertVideoInfoToVideoInfoDto(VideoInfo videoInfo) {
        VideoInfoDto videoInfoDto = new VideoInfoDto();
        videoInfoDto.setUserId(videoInfo.getUserId());
        videoInfoDto.setProductId(videoInfo.getProductId());
        videoInfoDto.setFileName(videoInfo.getFileName());
        videoInfoDto.setDescription(videoInfo.getDescription());
        videoInfoDto.setVideoPath(videoInfo.getVideoPath());
        return videoInfoDto;
    }
}
