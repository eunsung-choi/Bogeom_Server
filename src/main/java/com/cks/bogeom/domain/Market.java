package com.cks.bogeom.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "markets") //테이블명
@Getter @Setter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Market {
    @Id
    @GeneratedValue
    @Column(name = "market_id") //pk이름
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id") //외래키
    private Item item;

    private String marketName; //판매점 이름

    private Long marketPrice; //판매가격

    public Market(){

    }

    //==연관관계 메서드==//
    public void setItem(Item item) {
        this.item = item;
        item.getMarkets().add(this); //market 추가
    }

    public Market(String marketName){
        this.marketName = marketName;
    }

    public Market(String marketName, Long marketPrice, Item item){
        this.marketName = marketName;
        this.marketPrice = marketPrice;
        if (item != null){
            setItem(item);
        }
    }

    //==생성 메서드==//
    public static Market createMarket(Item item, String marketName, Long marketPrice) {

        Market market = new Market();
        market.setItem(item);
        market.setMarketName(marketName);
        market.setMarketPrice(marketPrice);

        return market;
    }

//    // 연관관계 편의 메서드
//    public void setItem(Item item){
//        this.item = item;
//        item.getMarkets().add(this);
//    }

    //==market 삭제 메서드==//

}
