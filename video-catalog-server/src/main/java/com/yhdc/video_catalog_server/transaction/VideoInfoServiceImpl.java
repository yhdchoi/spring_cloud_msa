package com.yhdc.video_catalog_server.transaction;

import com.yhdc.video_catalog_server.transaction.data.VideoInfo;
import com.yhdc.video_catalog_server.transaction.data.VideoInfoRepository;
import com.yhdc.video_catalog_server.transaction.object.VideoInfoDto;
import com.yhdc.video_catalog_server.transaction.object.VideoInfoSaveRecord;
import com.yhdc.video_catalog_server.transaction.object.VideoInfoUpdateRecord;
import com.yhdc.video_catalog_server.transaction.util.DataConverter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.yhdc.video_catalog_server.transaction.type.Constants.VIDEO_BASE_DIR;

@Slf4j
@RequiredArgsConstructor
@Service
public class VideoInfoServiceImpl implements VideoInfoService {

    private final VideoInfoRepository videoInfoRepository;
    private final VideoFileRestClientService videoFileRestClientService;
    private final DataConverter dataConverter;

    /**
     * FIND VIDEO-INFO FROM DB
     *
     * @param videoInfoId
     * @implNote
     * @implSpec
     */
    protected VideoInfo findVideoInfo(String videoInfoId) {
        Optional<VideoInfo> videoInfoOptional = videoInfoRepository.findById(UUID.fromString(videoInfoId));
        return videoInfoOptional.orElse(null);
    }

    /**
     * SAVE VIDEO INFO
     *
     * @param videoInfoSaveRecordList
     * @implNote
     */
    @Override
    @Transactional
    public List<VideoInfo> createVideoInfo(List<VideoInfoSaveRecord> videoInfoSaveRecordList) {
        List<VideoInfo> videoInfoList = videoInfoSaveRecordList.stream().map(dataConverter::convertVideoInfoDtoToVideoInfo).toList();
        return videoInfoRepository.saveAll(videoInfoList);
    }


    /**
     * LIST VIDEO INFO
     *
     * @return
     */
    @Override
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
    @Override
    @Transactional(readOnly = true)
    public String getVideoPathByVideoInfoId(String videoInfoId) {
        VideoInfo videoInfo = findVideoInfo(videoInfoId);
        log.info("videoInfo: {}", videoInfo);
        return videoInfo.getVideoPath();
    }


    /**
     * UPDATE VIDEO INFO ONBJECT
     *
     * @param videoInfoUpdateRecord
     * @implNote
     */
    @Override
    @Transactional
    public ResponseEntity<?> updateVideoInfo(VideoInfoUpdateRecord videoInfoUpdateRecord) {
        Optional<VideoInfo> videoInfoOptional = videoInfoRepository.findById(UUID.fromString(videoInfoUpdateRecord.videoInfoId()));
        if (videoInfoOptional.isPresent()) {
            VideoInfo videoInfo = videoInfoOptional.get();
            videoInfo.setTitle(videoInfoUpdateRecord.title());
            videoInfo.setDescription(videoInfoUpdateRecord.description());
            videoInfo.setVideoPath(VIDEO_BASE_DIR + videoInfoUpdateRecord.videoPath());

            VideoInfoDto response = dataConverter.convertVideoInfoToVideoInfoDto(videoInfo);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("VideoInfo not found!!!", HttpStatus.NOT_FOUND);
        }
    }


    /**
     * DELETE VIDEO INFORMATION AND VIDEO FILE
     *
     * @param videoInfoId
     * @implNote
     * @implSpec
     */
    @Override
    @Transactional
    public ResponseEntity<?> deleteVideoInfo(String videoInfoId) {
        try {
            VideoInfo videoInfo = findVideoInfo(videoInfoId);
            if (videoInfo != null) {
                ResponseEntity<?> clientResponse
                        = videoFileRestClientService.deleteVideoFiles(videoInfo.getVideoPath());
                if (clientResponse.getStatusCode() == HttpStatus.OK) {
                    videoInfoRepository.delete(videoInfo);
                    return new ResponseEntity<>(HttpStatus.OK);
                } else {
                    return new ResponseEntity<>(clientResponse, HttpStatus.BAD_REQUEST);
                }
            } else {
                return new ResponseEntity<>("VideoInfo not found!!!", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

}
