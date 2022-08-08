package com.app.api.category.service;

import com.app.api.category.entity.Category;
import com.app.api.category.repository.CategoryRepository;
import com.app.api.core.exception.BizException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> getCategoryList() {
        List<Category> categoryList = categoryRepository.findAll();

        if (categoryList.isEmpty()) {
            throw BizException.withUserMessageKey("exception.category.list.not.found").build();
        }

        return categoryList;
    }
}
