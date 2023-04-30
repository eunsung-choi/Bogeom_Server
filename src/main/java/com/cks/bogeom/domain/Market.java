package com.cks.bogeom.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "markets") //테이블명
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Market {

    @Id
    @GeneratedValue
    @Column(name = "market_id") //pk이름
    private Long id;

    private int marketName; //판매점 이름
    private int marketPrice; //판매가격

}
