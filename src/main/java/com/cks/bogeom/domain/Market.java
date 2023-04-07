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
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Market {

    @Id @GeneratedValue
    @Column(name = "market_id") //pk이름
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id") //매핑을 뭘로 할건지,외래키
    private Item item;

    private int marketName; //판매점 이름
    private int marketPrice; //판매가격

//    //==연관관계 메서드==//
//    public void setItem(Item item){
//        this.item = item;
//        item.getMarkets().add(this);
//    }
//
//    //==생성 메서드==//
//    public static Market createMarket(Item item, int marketPrice) {
//        Market market = new Market();
//        market.setItem(item);
//        market.setMarketPrice(marketPrice);
//
//        return market;
//    }
}
