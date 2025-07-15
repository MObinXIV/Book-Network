package com.mobin.book.file;

import com.mobin.book.book.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import static java.io.File.*;
import static java.lang.System.currentTimeMillis;

@Service
@Slf4j
public class FileServiceStorage {
    @Value("${application.file.upload.photos-output-path}")
    private String fileUploadPath;
    public String saveFile(@NonNull MultipartFile sourceFile,
                           @NonNull UUID userId) {
        final String fileUploadSubPath= "users" + separator + userId;
        return uploadFile(sourceFile,fileUploadSubPath);
    }

    private String uploadFile(@NonNull MultipartFile sourceFile,
                              @NonNull String fileUploadSubPath) {
        final String finalUploadPath = fileUploadPath + separator + fileUploadSubPath;
        File targetFolder = new File(finalUploadPath);
        if(!targetFolder.exists()){
            boolean folderCreated = targetFolder.mkdirs();
            if(!folderCreated) {
                log.warn("Failed to create the target folder");
                return null;
            }
        }
        final String fileExtension = getFileExtension(sourceFile.getOriginalFilename());
        String targetFilePath = fileUploadSubPath + separator + currentTimeMillis() + "."+ fileExtension;
        Path targetPath = Paths.get(targetFilePath);
        try {
            Files.write(targetPath,sourceFile.getBytes());
            log.info("File save to {}", targetFilePath);;
            return targetFilePath;
        }catch (IOException e){
            log.error("File was not saved",e);
        }
        return null;
    }

    private String getFileExtension(String fileName) {
        if(fileName== null || fileName.isEmpty()) return "";

        // something.jpg like that
        int lastDotIndex= fileName.lastIndexOf(".");

        if(lastDotIndex==-1) return "";

        return fileName.substring(lastDotIndex+1).toLowerCase();
    }
}
