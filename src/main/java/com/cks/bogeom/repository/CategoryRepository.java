package com.cks.bogeom.repository;

import com.cks.bogeom.domain.Category;
import com.cks.bogeom.domain.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CategoryRepository {
    @PersistenceContext
    private final EntityManager em;

    //category 저장
    //item 저장
    public void save(Category category){
        if(category.getId()==null){ //저장되지않은 신규 아이템이면 저장
            em.persist(category);
        } else{ //이미 저장된 item이면 업데이트(merge)
            em.merge(category);
        }
    }


//    public void saveCategoryItem(Long category_id, Long item_id){
//        em.createNativeQuery("INSERT INTO categoryitem (category_id, item_id) VALUES (:category_id, :item_id)")
//                .setParameter("category_id", category_id)
//                .setParameter("item_id", item_id)
//                .executeUpdate();
//    }

//    //category 하나 조회
//    public Item findOne(Long id){
//        return em.find(Item.class, id);
//    }
//
//    //category 이름으로 조회 (item 속성으로 조회하므로 연관된 엔티티로 검색하는 ItemSearch는 따로 안만듬)
//    public List<Item> findByName(String name){
//        return em.createQuery("select i from Item i where i.itemName = :name", Item.class)
//                .setParameter("name", name)
//                .getResultList();
//    }
//
//    //item 전부 조회
//    public List<Item> findAll(){
//        return em.createQuery("select i from Item i", Item.class)
//                .getResultList();
//    }

}
