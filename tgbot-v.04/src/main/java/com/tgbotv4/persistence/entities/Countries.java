package com.tgbotv4.persistence.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by yaoun on 09.04.2018.
 */
@Getter
@Setter
@Entity
@Table(name = "countries")
public class Countries {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column
    private String country;


}
