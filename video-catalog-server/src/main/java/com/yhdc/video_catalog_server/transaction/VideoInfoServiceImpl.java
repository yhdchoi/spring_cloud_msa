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

import java.util.ArrayList;
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
     * @param videoInfoSaveRecord
     * @implNote
     */
    @Override
    @Transactional
    public ResponseEntity<?> createVideoInfo(VideoInfoSaveRecord videoInfoSaveRecord) {
        VideoInfo videoInfo = dataConverter.convertVideoInfoDtoToVideoInfo(videoInfoSaveRecord);
        VideoInfo body = videoInfoRepository.save(videoInfo);
        return new ResponseEntity<>(body, HttpStatus.CREATED);
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
            videoInfo.setFileName(videoInfoUpdateRecord.title());
            videoInfo.setDescription(videoInfoUpdateRecord.description());
            videoInfo.setVideoPath(VIDEO_BASE_DIR + videoInfoUpdateRecord.videoPath());

            VideoInfoDto response = dataConverter.convertVideoInfoToVideoInfoDto(videoInfo);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("VideoInfo not found!!!", HttpStatus.NOT_FOUND);
        }
    }


    /**
     * DELETE SELECTED VIDEO CATALOG AND VIDEOS
     *
     * @param userId
     * @param videoInfoIdList
     * @implNote
     * @implSpec
     */
    @Override
    @Transactional
    public ResponseEntity<?> deleteSelectedVideoInfo(String userId, List<String> videoInfoIdList) {
        try {
            List<VideoInfo> videoInfoList = new ArrayList<>();
            List<String> videoPathList = new ArrayList<>();

            for (String videoInfoId : videoInfoIdList) {
                VideoInfo videoInfo = findVideoInfo(videoInfoId);
                if (videoInfo != null) {
                    videoInfoList.add(videoInfo);
                    videoPathList.add(videoInfo.getVideoPath());
                } else {
                    log.error("Video catalog not found!!! [ {} ]", videoInfoId);
                }
            }
            ResponseEntity<?> clientResponse
                    = videoFileRestClientService.deleteSelectedVideoFiles(videoPathList);
            if (clientResponse.getStatusCode() == HttpStatus.OK) {
                videoInfoRepository.deleteAll(videoInfoList);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(clientResponse, HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            throw new RuntimeException();
        }
    }


    /**
     * DELETE ALL VIDEO CATALOG AND VIDEOS
     *
     * @param userId
     * @param productId
     */
    @Override
    @Transactional
    public ResponseEntity<?> deleteProductVideoInfo(String userId, String productId) {
        try {
            ResponseEntity<?> clientResponse
                    = videoFileRestClientService.deleteAllProductVideoFiles(userId, productId);
            if (clientResponse.getStatusCode() == HttpStatus.OK) {
                List<VideoInfo> videoInfoList = videoInfoRepository.findAllByUserIdAndProductId(userId, productId);
                videoInfoRepository.deleteAll(videoInfoList);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                return new ResponseEntity<>(clientResponse.getBody(), HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

}
