package com.cks.bogeom.service;

import com.cks.bogeom.domain.Category;
import com.cks.bogeom.domain.Item;
import com.cks.bogeom.repository.CategoryRepository;
import com.cks.bogeom.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

//    @Transactional
//    public void update(Long itemId, String itemName, String img, String dImg, String reviewClassCode, String enuriLink){
//        Item item = itemRepository.findOne(itemId);
//        item.setItemName(itemName);
//        item.setItemImg(img);
//        item.setDetailImg(dImg);
//        item.setReviewClassCode(reviewClassCode);
//        item.setEnuriLink(enuriLink);
//    }

    @Transactional
    public Long saveCategory(String categoryName) {
        Category category = new Category();
        category.setCategoryName(categoryName);

        //repo에 저장
        categoryRepository.save(category);
        return category.getId();
    }


//    public Long saveItemCategory(Long categoryId, Long itemId) {
//        categoryRepository.saveCategoryItem(categoryId, itemId);
//        return categoryId;
//    }


}
