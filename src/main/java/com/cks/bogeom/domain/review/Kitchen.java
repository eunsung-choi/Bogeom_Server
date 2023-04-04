package com.cks.bogeom.domain.review;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("K")
@Getter @Setter
public class Kitchen extends Review{

    private Long solidity; //견고함
    private Long afterFeel; //사용감
}
