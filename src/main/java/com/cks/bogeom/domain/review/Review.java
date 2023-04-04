package com.cks.bogeom.domain.review;

import com.cks.bogeom.domain.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Review {

    @Id
    @GeneratedValue
    @Column(name = "review_id")
    private Long id;

    private String reviewContent;
    private LocalDateTime reviewDate;
    private Long reviewRate;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;
}
