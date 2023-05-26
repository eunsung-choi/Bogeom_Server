package com.cks.bogeom.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "ImageData")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ImageData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    private String name;

    private String type;

    @Lob
    @Column(name = "imageData", length = 100)
    private byte[] imageData;

    @Builder
    public ImageData(String name, String type, byte[] imageData) {
        this.name = name;
        this.type = type;
        this.imageData = imageData;
    }
}