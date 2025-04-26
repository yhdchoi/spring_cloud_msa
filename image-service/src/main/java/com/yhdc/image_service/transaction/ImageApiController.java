package com.yhdc.image_service.transaction;

import com.yhdc.image_service.transaction.object.CommonResponseDto;
import com.yhdc.image_service.transaction.object.DeleteImageDto;
import com.yhdc.image_service.transaction.object.ImageInfoListDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ImageApiController {

    private final ImageServiceImpl imageServiceImpl;

    /**
     * SAVE IMAGE FILE
     *
     * @param dirId
     * @param fileArray
     */
    @PostMapping("/save/image")
    public ResponseEntity<CommonResponseDto> saveImage(@RequestPart(value = "dirId") String dirId,
                                                       @RequestPart(value = "fileArray") MultipartFile[] fileArray) {
        return imageServiceImpl.saveImages(dirId, fileArray);
    }

    /**
     * PATCH IMAGE FILE
     *
     * @param dirId
     */
    @PatchMapping("/patch/image")
    public ResponseEntity<CommonResponseDto> patchImage(@RequestPart(value = "dirId") String dirId,
                                                        @RequestPart(value = "fileArray") MultipartFile[] fileArray) {
        return imageServiceImpl.patchImage(dirId, fileArray);
    }


    /**
     * LOAD IMAGE INFORMATION LIST
     *
     * @param dirId
     */
    @GetMapping("/load/image-info")
    public ResponseEntity<ImageInfoListDto> loadAllImageInfo(@RequestParam String dirId) {
        return imageServiceImpl.loadAllImages(dirId);
    }

    /**
     * DOWNLOAD AN IMAGE
     *
     * @param dirId
     * @param fileName
     * @return
     */
    @GetMapping("/download/image")
    public ResponseEntity<Resource> downloadImage(@RequestParam String dirId,
                                                  @RequestParam String fileName) {
        return imageServiceImpl.loadImage(dirId, fileName);
    }

    /**
     * DOWNLOAD ALL IMAGES
     *
     * @param dirId
     * @apiNote Zip image files
     */
    @GetMapping("/download/image-zip")
    public ResponseEntity<Resource> downloadImageZip(@RequestParam String dirId) {
        return imageServiceImpl.downloadImageDirZip(dirId);
    }


    /**
     * DELETE SELECTED IMAGE FILES
     *
     * @param deleteImageDto
     */
    @DeleteMapping("/delete/image-selected")
    public ResponseEntity<CommonResponseDto> deleteImageSelected(@RequestBody DeleteImageDto deleteImageDto) {
        return imageServiceImpl.deleteImagesFromDir(deleteImageDto.getDirId(), deleteImageDto.getImageFileNameList());
    }

    /**
     * DELETE AN IMAGE DIRECTORY
     *
     * @param dirId
     * @apiNote Deletes all subdirectories and files
     */
    @DeleteMapping("/delete/image-dir")
    public ResponseEntity<?> deleteImage(@RequestParam String dirId) {
        return imageServiceImpl.deleteImageDir(dirId);
    }

}
