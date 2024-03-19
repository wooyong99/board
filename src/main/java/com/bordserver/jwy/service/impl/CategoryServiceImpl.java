package com.bordserver.jwy.service.impl;

import com.bordserver.jwy.dao.CategoryDao;
import com.bordserver.jwy.dto.CategoryDTO;
import com.bordserver.jwy.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryDao dao;

    @Override
    public void register(String accountId, CategoryDTO categoryDTO) {

    }

    @Override
    public void update(CategoryDTO categoryDTO) {

    }

    @Override
    public void delete(int categoryId) {

    }
}
