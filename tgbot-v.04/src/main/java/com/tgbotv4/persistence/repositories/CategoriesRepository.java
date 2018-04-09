package com.tgbotv4.persistence.repositories;

import com.tgbotv4.persistence.entities.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by yaoun on 09.04.2018.
 */
public interface CategoriesRepository extends JpaRepository<Categories, Integer> {
}
