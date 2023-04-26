package com.cks.bogeom.repository;

import com.cks.bogeom.domain.Market;
import com.cks.bogeom.domain.review.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
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
    public List<Review> findByName(String reviewContent){
        return em.createQuery("select m from Review m where m.reviewName = :name", Review.class)
                .setParameter("reviewContent", reviewContent)
                .getResultList();
    }

}

