package com.bordserver.jwy.service;

import com.bordserver.jwy.dto.CategoryDTO;
import org.springframework.stereotype.Service;

@Service
public interface CategoryService {
    void register(String accountId, CategoryDTO categoryDTO);

    void update(CategoryDTO categoryDTO);

    void delete(int categoryId);
}
