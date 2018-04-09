package com.tgbotv4.persistence.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by yaoun on 09.04.2018.
 */@Getter
@Setter
@Entity
@Table(name = "cities")
public class Cities {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;

    @Column
    private String city;

    @ManyToOne
    @JoinColumn(name = "countryId")
    private Countries countryName;
}
