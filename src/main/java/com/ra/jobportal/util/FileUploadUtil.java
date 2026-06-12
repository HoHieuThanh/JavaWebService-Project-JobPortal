package com.ra.jobportal.util;

import com.ra.jobportal.exception.BadRequestException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

public class FileUploadUtil {
    private FileUploadUtil() {}

    public static String saveFile(MultipartFile file, String uploadDir) {
        try {
            Path path = Paths.get(uploadDir);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }
            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Files.copy(
                    file.getInputStream(),
                    path.resolve(fileName),
                    StandardCopyOption.REPLACE_EXISTING
            );
            return fileName;
        } catch (IOException e) {
            throw new BadRequestException("Upload file failed");
        }
    }
}