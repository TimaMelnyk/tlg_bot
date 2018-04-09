package com.tgbotv4.services;

import com.tgbotv4.persistence.entities.Categories;
import com.tgbotv4.persistence.repositories.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by yaoun on 09.04.2018.
 */
@Service
public class CategoriesService {@Autowired
    CategoriesRepository categoriesRepository;

    public boolean categoryExist (int message) {
        List<Categories> categories = categoriesRepository.findAll();
        return categories.stream().anyMatch(o -> o.getId().equals(message));
    }

    public List<Categories> getCategories() {
        return categoriesRepository.findAll();
    }
 }
