package com.example.demo.model.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.entities.ProductCategory;
@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Long> {
	
}
