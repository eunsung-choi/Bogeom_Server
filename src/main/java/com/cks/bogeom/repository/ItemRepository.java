package com.cks.bogeom.repository;

import com.cks.bogeom.domain.Item;
import com.cks.bogeom.domain.review.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {
    private final EntityManager em;

    //item 저장
    public void save(Item item){
        if(item.getId()==null){ //신규 아이템 저장
            em.persist(item);
        }
        else { //이미 저장된 item이면 merge(업데이트)
            em.merge(item);
        }
    }

    //item 하나 조회
    public Item findOne(Long id){
        return em.find(Item.class, id);
    }

    //item 이름으로 조회 (item 속성으로 조회하므로 연관된 엔티티로 검색하는 ItemSearch는 따로 안만듬)
    public List<Item> findByName(String name){
        return em.createQuery("select i from Item i where i.itemName = :name", Item.class)
                .setParameter("name", name)
                .getResultList();
    }

    //item 전부 조회
    public List<Item> findAll(){
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}