package com.example.jpos_server.service;

import com.example.jpos_server.domain.Store;
import com.example.jpos_server.dto.CategoryDto;
import com.example.jpos_server.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<CategoryDto> searchCategories(Store store){
        return categoryRepository.findByStore(store);
    }
}
