package com.example.jpos_server.service;

import com.example.jpos_server.domain.Category;
import com.example.jpos_server.domain.Store;
import com.example.jpos_server.domain.request.CategoryRequest;
import com.example.jpos_server.dto.CategoryDto;
import com.example.jpos_server.repository.CategoryRepository;
import com.example.jpos_server.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final StoreRepository storeRepository;

    @Transactional(readOnly = true)
    public List<CategoryDto> searchCategories(Store store){
        return categoryRepository.findByStore(store);
    }

    @Transactional(readOnly = true)
    public Category searchCategory(Long id) {return categoryRepository.findById(id).get();}

    public void addCategory(CategoryRequest categoryRequest){
        Category category = new Category();
        category.setCategoryName(categoryRequest.getCategoryName());
        category.setStore(storeRepository.findById(categoryRequest.getStoreId()).get());
        categoryRepository.save(category);
    }

    public void deleteCategory(Long categoryId){
        categoryRepository.deleteById(categoryId);
    }
}
