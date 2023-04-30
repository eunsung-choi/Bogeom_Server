package com.cks.bogeom.domain.review;

import com.cks.bogeom.domain.Item;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("D")
@Getter
//@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Daily extends Review{

    private Long scent; //향
    private Long clean; //세정력
    private Long stimulation; //자극도

//    @Builder
//    public Daily(Long id, Item item, String reviewContent, int reviewDate, Long reviewRate,
//                 Long scent, Long clean, Long stimulation){
//        super(id, item, reviewContent, reviewDate, reviewRate);
//        this.scent = scent;
//        this.clean = clean;
//        this.stimulation = stimulation;
//
//    }

}
