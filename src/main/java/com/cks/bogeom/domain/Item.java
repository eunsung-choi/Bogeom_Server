package com.cks.bogeom.domain;

import com.cks.bogeom.domain.review.Review;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
public class Item {
    @Id @GeneratedValue
    @Column(name = "item_id") //pk 이름
    private Long id;
    private String itemName;
    private String itemImg;
    private String detailImg;

    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "item")
    private List<Review> reviews = new ArrayList<>();

    @JsonIgnore
    @OneToMany(mappedBy = "item")
    private List<Market> markets = new ArrayList<>();



}
