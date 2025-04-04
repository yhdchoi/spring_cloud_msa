package com.yhdc.video_catalog_server;

import com.yhdc.video_catalog_server.data.VideoInfo;
import com.yhdc.video_catalog_server.data.VideoInfoRepository;
import com.yhdc.video_catalog_server.object.VideoInfoDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class VideoInfoServiceImpl {

    private static final String VIDEO_BASE_DIR = "/Users/daniel/Documents/test/";

    private final VideoInfoRepository videoInfoRepository;


    @Transactional
    public List<VideoInfo> createVideoInfo(List<VideoInfoDto> videoInfoDtoList) {
        List<VideoInfo> videoInfoList = videoInfoDtoList.stream().map(this::convertVideoInfoDtoToVideoInfoDto).toList();
        return videoInfoRepository.saveAll(videoInfoList);
    }

    private VideoInfo convertVideoInfoDtoToVideoInfoDto(VideoInfoDto videoInfoDto) {
        VideoInfo videoInfo = new VideoInfo();
        videoInfo.setTitle(videoInfoDto.getTitle());
        videoInfo.setDescription(videoInfoDto.getDescription());
        videoInfo.setFilePath(VIDEO_BASE_DIR + videoInfoDto.getFilePath());
        return videoInfo;
    }


    @Transactional(readOnly = true)
    public List<VideoInfo> getVideoInfoList() {
        return videoInfoRepository.findAll();
    }


    @Transactional(readOnly = true)
    public String getVideoPathByVideoInfoId(String videoInfoId) {
        Optional<VideoInfo> videoInfoOptional = videoInfoRepository.findById(UUID.fromString(videoInfoId));
        if (videoInfoOptional.isPresent()) {
            VideoInfo videoInfo = videoInfoOptional.get();
            return videoInfo.getFilePath();
        } else {
            throw new NullPointerException("videoInfo not found");
        }
    }

}
