package com.cks.bogeom.repository;

import com.cks.bogeom.domain.Market;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MarketRepository {
    private final EntityManager em;

    //마켓 저장
    public Long save(Market market){
        em.persist(market);
        return market.getId();
    }
    //마켓 하나 찾기
    public Market findOne(Long id){return em.find(Market.class, id);}
    //전체 마켓 찾기
    public List<Market> findAll(){
        return em.createQuery("select m from Market m", Market.class)
                .getResultList();
    }
    //마켓 이름으로 찾기
    public List<Market> findByName(String name){
        return em.createQuery("select m from Market m where m.marketName = :name", Market.class)
                .setParameter("name", name)
                .getResultList();
    }
}
