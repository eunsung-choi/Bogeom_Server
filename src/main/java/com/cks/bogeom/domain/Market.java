package com.cks.bogeom.domain;

import lombok.*;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.FetchType.LAZY;

@Entity
@Table(name = "markets") //테이블명
@Getter @Setter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Market {

    @Id @GeneratedValue
    @Column(name = "market_id") //pk이름
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id") //매핑을 뭘로 할건지,외래키
    private Item item;

    private String marketName; //판매점 이름
    private long marketPrice; //판매가격

    public Market(String marketName){
        this.marketName = marketName;
    }

    public Market(String marketName, long marketPrice, Item item){
        this.marketName = marketName;
        this.marketPrice = marketPrice;
        if (item != null){
            changeItem(item);
        }
    }

    //==생성 메서드==//
    public static Market createMarket(Item item, String marketName, long marketPrice) {
        Market market = new Market();
        market.setItem(item);
        market.setMarketName(marketName);
        market.setMarketPrice(marketPrice);

        return market;
    }

//    @Builder
//    public Market(String marketName,long marketPrice, Item item){
//        this.marketName = marketName;
//        this.marketPrice = marketPrice;
//        this.item = item;
//    }
//
    public Market(){

    }

    //==연관관계 메서드==//
    public void setItem(Item item){
        this.item = item;
        item.getMarkets().add(this);
    }
//
    // 연관관계 편의 메서드
    public void changeItem(Item item){
        this.item = item;
        item.getMarkets().add(this);
    }

}
