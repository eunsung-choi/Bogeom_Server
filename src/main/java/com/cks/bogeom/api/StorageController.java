package com.cks.bogeom.api;

import com.cks.bogeom.domain.ImageData;
import com.cks.bogeom.service.StorageService;
import com.cks.bogeom.utils.ImageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class StorageController {

    RestTemplate restTemplate = new RestTemplate();

    final private StorageService storageService;

    // Upload image
    @PostMapping("/image")
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file) throws IOException {

        // post에서 이미지 보이게 하기
        byte[] uploadImage = ImageUtils.compressImage(file.getBytes());
        byte[] downloadImage = ImageUtils.decompressImage(uploadImage);

        // Trigger the downloadImage method
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.IMAGE_PNG)
                .body(downloadImage);

        // 기존 방법
//        String uploadImage = storageService.uploadImage(file);
//        return ResponseEntity.status(HttpStatus.OK)
//                .body(uploadImage);
    }

    // Download image
    @GetMapping("image/{fileName}")
    public ResponseEntity<?> downloadImage(@PathVariable("fileName") String fileName) {

        byte[] downloadImage = storageService.downloadImage(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.IMAGE_PNG)
                .body(downloadImage);
    }
}