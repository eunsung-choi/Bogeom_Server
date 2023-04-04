package com.cks.bogeom.domain.review;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("D")
@Getter
@Setter
public class Daily extends Review{

    private Long scent; //향
    private Long clean; //세정력
    private Long stimulation; //자극도
}
