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

    public void setCategory (Categories category) {
        List<Categories> categories = categoriesRepository.findAll();
        boolean state = false;
        for(int i =0;i<categories.size();i++) {
            if (categories.get(i).getCategoryName().equals(category.getCategoryName()))
                state = true;
        }
        if(!state) {categoriesRepository.save(category); }
    }
 }
