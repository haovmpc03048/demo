package com.fpoly.duantotnghiep.API.AdminRest;

import org.hibernate.mapping.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;



@RestController
@RequestMapping("/api")
public class FileUploadController {

    @Value("${file.upload.dir}") // Thư mục đích cho tệp tin tải lên
    private String uploadDir;

    @PostMapping("/upload")
    public ResponseEntity<java.util.Map<String, String>> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            // Tạo thư mục nếu nó không tồn tại
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Lưu tệp tin vào thư mục đích
            Path filePath = Paths.get(uploadDir, file.getOriginalFilename());
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            java.util.Map<String, String> response = new HashMap<>();
            response.put("message", "Tải lên tệp tin thành công");

            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IOException e) {
            java.util.Map<String, String> response = new HashMap<>();
            response.put("error", "Lỗi tải lên tệp tin");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

}
