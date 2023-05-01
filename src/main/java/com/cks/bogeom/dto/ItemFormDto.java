package com.cks.bogeom.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class ItemFormDto {
    @NotBlank(message = "상품 이름은 필수 입력 값입니다.")
    private String itemName;

    @NotEmpty(message = "상세 설명 사진은 필수 입력 값입니다.")
    private String detailImg;

    @NotEmpty(message = "상품 사진은 필수 입력 값입니다.")
    private String itemImg;
}
