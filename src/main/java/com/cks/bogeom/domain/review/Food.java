package com.cks.bogeom.domain.review;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("F")
@Getter
@Setter
public class Food extends Review{

    private Long spicy;
    private Long amount;
    private Long taste;
    private Long sugar;
}
