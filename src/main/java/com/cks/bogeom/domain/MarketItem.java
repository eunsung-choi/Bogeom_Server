package com.cks.bogeom.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MarketItem {
    @Id
    @GeneratedValue
    @Column(name = "market_item_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "market_id")
    private Market market;

    //==생성 메서드==// : 마켓과 item 매핑하는 역할
    public static MarketItem createMarketItem(Item item){
        MarketItem marketItem = new MarketItem();
        marketItem.setItem(item);
        return marketItem;
    }



}
