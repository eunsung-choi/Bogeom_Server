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
    private Long marketCode; //마켓 크롤링 코드
    private String marketLogo; //마켓 링크

    private Long marketPrice; //판매가격
    private int marketDeliverFee;
    private String marketLink;

    public Market(){

    }

    //==연관관계 메서드==//
    public void setItem(Item item) {
        this.item = item;
        item.getMarkets().add(this); //market 추가
    }

    //==생성 메서드==//
    public static Market createMarket(Item item, String marketName, Long marketCode, String marketLogo,
                                      Long marketPrice, int marketDeliverFee, String marketLink) {

        Market market = new Market();
        market.setItem(item);
        market.setMarketName(marketName);
        market.setMarketCode(marketCode);
        market.setMarketLogo(marketLogo);
        market.setMarketPrice(marketPrice);
        market.setMarketDeliverFee(marketDeliverFee);
        market.setMarketLink(marketLink);

        return market;
    }

//    // 연관관계 편의 메서드
//    public void setItem(Item item){
//        this.item = item;
//        item.getMarkets().add(this);
//    }

    //==market 삭제 메서드==//

}
