package com.cks.bogeom.domain;

import lombok.*;

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

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id") //외래키
    private Item item;

    private String marketName; //판매점 이름
    private int marketPrice; //판매가격


    //==연관관계 메서드==//
    public void setItem(Item item) {
        this.item = item;
        item.getMarkets().add(this); //market 추가
    }

    //==생성 메서드==//
    public static Market createMarket(Item item, String marketName, int marketPrice) {
        Market market = new Market();
        market.setItem(item);
        market.setMarketName(marketName);
        market.setMarketPrice(marketPrice);
        return market;
    }

    //==market 삭제 메서드==//

}
