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
    public void save(Market market){
        if(market.getId()==null){
            em.persist(market);
        } else{ //이미 저장된 market이면 업데이트
            em.merge(market);
        }

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
