package com.cks.bogeom.repository;

import com.cks.bogeom.domain.ImageData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StorageRepository extends JpaRepository<ImageData, Long> {

    Optional<ImageData> findByName(String fileName);
    Optional<ImageData> findById(Long fileId);
}