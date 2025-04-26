package com.yhdc.image_service.transaction;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
@Service
public class ImageFileService {


    /**
     * SAVE FILE TO A DIRECTORY
     *
     * @param multipartFile
     * @param directory
     */
    public void saveFileToDir(MultipartFile multipartFile, String directory) {
        log.info("New file: {}", multipartFile.getOriginalFilename());
        try {
            final String fileName = multipartFile.getOriginalFilename();
            final byte[] fileData = multipartFile.getBytes();
            File file = new File(directory + fileName);
            OutputStream outputStream = new FileOutputStream(file);
            outputStream.write(fileData);
            outputStream.close();
        } catch (IOException ioe) {
            log.error("Error saving file: {}", directory + "/" + multipartFile.getOriginalFilename());
            throw new RuntimeException(ioe.getMessage());
        }
    }

    /**
     * DELETE FILE FROM A DIRECTORY
     *
     * @param directory
     * @param fileName
     */
    public void deleteFileFromDir(String directory, String fileName) {
        log.info("File name: {}", fileName);
        File file = new File(directory + fileName);
        if (file.exists()) {
            boolean isDeleted = file.delete();
            log.warn("{} deleted: {}", fileName, isDeleted);
        } else {
            log.warn("{} does not exist: {}", fileName, directory);
        }
    }


    /**
     * CREATE ZIP FILE FOR DOWNLOAD
     *
     * @param filDirectory
     * @param zippedFileDir
     * @param zipFileName
     */
    public Resource createZipFile(String filDirectory, String zippedFileDir, String zipFileName) {

        try {
            // Zip directory
            FileOutputStream fos = new FileOutputStream(zippedFileDir + "/" + zipFileName);
            ZipOutputStream zipOut = new ZipOutputStream(fos);
            File dirToZip = new File(filDirectory);
            zipFile(dirToZip, dirToZip.getName(), zipOut);
            zipOut.close();
            fos.close();

            // Download zip file
            Path filePath = Paths.get(zippedFileDir).resolve(zipFileName).normalize();
            return new UrlResource(filePath.toUri());

        } catch (FileNotFoundException fnfe) {
            throw new RuntimeException(fnfe);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }


    /**
     * ZIP DIRECTORY ALONG WITH ITS SUBDIRECTORIES AND FILES
     *
     * @param dirToZip
     * @param dirName
     * @param zipOut
     */
    private static void zipFile(File dirToZip, String dirName, ZipOutputStream zipOut) {

        try {
            // Check if the directory is hidden
            if (dirToZip.isHidden()) {
                throw new RuntimeException(dirName + " is hidden");
            }

            if (dirToZip.isDirectory()) {
                // If subdirectory is present
                if (dirName.endsWith("/")) {
                    zipOut.putNextEntry(new ZipEntry(dirName));
                    zipOut.closeEntry();
                } else {
                    zipOut.putNextEntry(new ZipEntry(dirName + "/"));
                    zipOut.closeEntry();
                }
                // Zip files in the directory
                File[] children = dirToZip.listFiles();
                for (File childFile : children) {
                    zipFile(childFile, dirName + "/" + childFile.getName(), zipOut);
                }
                return;
            }

            // Write zip file
            FileInputStream fis = new FileInputStream(dirToZip);
            ZipEntry zipEntry = new ZipEntry(dirName);
            zipOut.putNextEntry(zipEntry);
            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }
            fis.close();

        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
    }
}
