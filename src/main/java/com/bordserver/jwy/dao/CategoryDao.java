package com.bordserver.jwy.dao;

import com.bordserver.jwy.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryDao extends JpaRepository<Category, Long> {
}
