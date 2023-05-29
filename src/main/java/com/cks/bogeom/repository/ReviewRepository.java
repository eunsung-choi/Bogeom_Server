package com.cks.bogeom.repository;

import com.cks.bogeom.domain.Market;
import com.cks.bogeom.domain.review.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ReviewRepository {
    private final EntityManager em;

    public void save(Review review){ //리뷰 저장
        em.persist(review);
    }

    public Review findOne(Long id){ //리뷰 1개 조회
        return em.find(Review.class, id);
    }

    public List<Review> findAll(){ //리뷰 전체 조회
        return em.createQuery("select r from Review r", Review.class)
                .getResultList();
    }

    // 리뷰에 name속성 없으므로 이름으로 조회 불가능, 단지 리뷰 리스트 열람 방식
    //테스트 위해 코드 추가
    public List<Review> findByItemName(String reviewContent){
        return em.createQuery("select m from Review m where m.reviewName = :name", Review.class)
                .setParameter("reviewContent", reviewContent)
                .getResultList();
    }
    // 리뷰에 name속성 없으므로 이름으로 조회 불가능, 단지 리뷰 리스트 열람 방식
    //굳이 ReviewSearch 만들 필요 없음 - id로만 조회하기때문, 확장성 고려
    public List<Review> findAllByString(ReviewSearch reviewSearch) {
        String jpql = "select r from Review r join r.item i";
        boolean isFirstCondition = true;

        //Item id 검색
        if (reviewSearch.getItemId()>=0) {
            if (isFirstCondition) {
                jpql+= " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " item.id like :id";
        }

        TypedQuery<Review> query = em.createQuery(jpql, Review.class)
                .setMaxResults(1000);
        if (reviewSearch.getItemId() >= 0) {
            query = query.setParameter("id", reviewSearch.getItemId());
        }
        return query.getResultList();
    }

    //item id로 review 조회
    public List<Review> findById(Long itemId) {
        String jpql = "select r from Review r join r.item i";
        boolean isFirstCondition = true;

        //Item id 검색

            if (isFirstCondition) {
                jpql+= " where";
                isFirstCondition = false;
            } else {
                jpql += " and";
            }
            jpql += " i.id like :id";
        TypedQuery<Review> query = em.createQuery(jpql, Review.class).setMaxResults(1000);

        query = query.setParameter("id", itemId);
        return query.getResultList();

    }


}

