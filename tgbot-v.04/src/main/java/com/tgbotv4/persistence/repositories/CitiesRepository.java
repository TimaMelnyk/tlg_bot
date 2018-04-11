package com.tgbotv4.persistence.repositories;

import com.tgbotv4.persistence.entities.Cities;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by yaoun on 10.04.2018.
 */
public interface CitiesRepository extends JpaRepository<Cities, Integer> {
}
