package com.yhdc.video_catalog_server.transaction;

import com.yhdc.video_catalog_server.transaction.data.VideoInfo;
import com.yhdc.video_catalog_server.transaction.object.VideoInfoDto;
import com.yhdc.video_catalog_server.transaction.object.VideoInfoSaveRecord;
import org.springframework.stereotype.Component;

import static com.yhdc.video_catalog_server.transaction.Constants.VIDEO_BASE_DIR;

@Component
public class DataConverter {


    public VideoInfo convertVideoInfoDtoToVideoInfo(VideoInfoSaveRecord videoInfoSaveRecord) {
        VideoInfo videoInfo = new VideoInfo();
        videoInfo.setTitle(videoInfoSaveRecord.title());
        videoInfo.setDescription(videoInfoSaveRecord.description());
        videoInfo.setVideoPath(VIDEO_BASE_DIR + videoInfoSaveRecord.videoTitleExt());
        return videoInfo;
    }

    public VideoInfoDto convertVideoInfoToVideoInfoDto(VideoInfo videoInfo) {
        VideoInfoDto videoInfoDto = new VideoInfoDto();
        videoInfoDto.setTitle(videoInfo.getTitle());
        videoInfoDto.setDescription(videoInfo.getDescription());
        videoInfoDto.setVideoPath(videoInfo.getVideoPath());
        return videoInfoDto;
    }
}
