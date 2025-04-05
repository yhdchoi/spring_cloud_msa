package com.yhdc.video_catalog_server;

import com.yhdc.video_catalog_server.data.VideoInfo;
import com.yhdc.video_catalog_server.data.VideoInfoRepository;
import com.yhdc.video_catalog_server.object.VideoInfoDto;
import com.yhdc.video_catalog_server.object.VideoInfoSaveRecord;
import com.yhdc.video_catalog_server.object.VideoInfoUpdateRecord;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class VideoInfoServiceImpl {

    private static final String VIDEO_BASE_DIR = "classpath:videos/";

    private final VideoInfoRepository videoInfoRepository;


    /**
     * SAVE VIDEO INFO
     *
     * @param videoInfoSaveRecordList
     * @implNote
     */
    @Transactional
    public List<VideoInfo> createVideoInfo(List<VideoInfoSaveRecord> videoInfoSaveRecordList) {
        List<VideoInfo> videoInfoList = videoInfoSaveRecordList.stream().map(this::convertVideoInfoDtoToVideoInfo).toList();
        return videoInfoRepository.saveAll(videoInfoList);
    }

    private VideoInfo convertVideoInfoDtoToVideoInfo(VideoInfoSaveRecord videoInfoSaveRecord) {
        VideoInfo videoInfo = new VideoInfo();
        videoInfo.setTitle(videoInfoSaveRecord.title());
        videoInfo.setDescription(videoInfoSaveRecord.description());
        videoInfo.setFilePath(VIDEO_BASE_DIR + videoInfoSaveRecord.videoTitleExt());
        return videoInfo;
    }


    /**
     * LIST VIDEO INFO
     *
     * @return
     */
    @Transactional(readOnly = true)
    public List<VideoInfo> getVideoInfoList() {
        return videoInfoRepository.findAll();
    }


    /**
     * GET VIDEO PATH
     *
     * @param videoInfoId
     * @return
     */
    @Transactional(readOnly = true)
    public String getVideoPathByVideoInfoId(String videoInfoId) {
        Optional<VideoInfo> videoInfoOptional = videoInfoRepository.findById(UUID.fromString(videoInfoId));
        if (videoInfoOptional.isPresent()) {
            VideoInfo videoInfo = videoInfoOptional.get();

            log.info("videoInfo: {}", videoInfo);
            return videoInfo.getFilePath();
        } else {
            throw new NullPointerException("videoInfo not found");
        }
    }


    /**
     * UPDATE VIDEO INFO ONBJECT
     *
     * @param videoInfoUpdateRecord
     * @implNote
     */
    @Transactional
    public ResponseEntity<?> updateVideoInfo(VideoInfoUpdateRecord videoInfoUpdateRecord) {
        Optional<VideoInfo> videoInfoOptional = videoInfoRepository.findById(UUID.fromString(videoInfoUpdateRecord.videoInfoId()));
        if (videoInfoOptional.isPresent()) {
            VideoInfo videoInfo = videoInfoOptional.get();
            videoInfo.setTitle(videoInfoUpdateRecord.title());
            videoInfo.setDescription(videoInfoUpdateRecord.description());
            videoInfo.setFilePath(VIDEO_BASE_DIR + videoInfoUpdateRecord.videoPath());

            VideoInfoDto response = convertVideoInfoToVideoInfoDto(videoInfo);
            log.info("videoInfo: {}", videoInfo);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("VideoInfo not found!!!", HttpStatus.NOT_FOUND);
        }
    }

    private VideoInfoDto convertVideoInfoToVideoInfoDto(VideoInfo videoInfo) {
        VideoInfoDto videoInfoDto = new VideoInfoDto();
        videoInfoDto.setTitle(videoInfo.getTitle());
        videoInfoDto.setDescription(videoInfo.getDescription());
        videoInfoDto.setFilePath(videoInfo.getFilePath());
        return videoInfoDto;
    }

}
