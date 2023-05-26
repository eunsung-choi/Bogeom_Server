package com.cks.bogeom.service;

import com.cks.bogeom.domain.ImageData;
import com.cks.bogeom.repository.StorageRepository;
import com.cks.bogeom.utils.ImageUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class StorageService {

    private final StorageRepository storageRepository;

    public String uploadImage(MultipartFile file) throws IOException {
        log.info("upload file: {}", file);
        ImageData imageData = storageRepository.save(
                ImageData.builder()
                        .name(file.getOriginalFilename())
                        .type(file.getContentType())
                        .imageData(ImageUtils.compressImage(file.getBytes()))
                        .build());
        if (imageData != null) {
            log.info("imageData: {}", imageData);
            return "file uploaded successfully : " + file.getOriginalFilename();
        }

        return null;
    }

    // 이미지 파일로 압축하기
    public byte[] downloadImage(String fileName) {
        ImageData imageData = storageRepository.findByName(fileName)
                .orElseThrow(RuntimeException::new);

        log.info("download imageData: {}", imageData);

        return ImageUtils.decompressImage(imageData.getImageData());
    }
    public byte[] downloadImage1(Long fileId) {
        ImageData imageData = storageRepository.findById(fileId)
                .orElseThrow(RuntimeException::new);

        log.info("download imageData: {}", imageData);

        return ImageUtils.decompressImage(imageData.getImageData());
    }



}