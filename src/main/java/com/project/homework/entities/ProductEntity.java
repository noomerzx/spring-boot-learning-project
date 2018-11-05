package com.project.homework.entities;

import java.sql.Timestamp;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Product")
@Table(name = "t_product")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Float price;
    private Float cost;
    private Integer stock;
    private Timestamp createTime;
    private Timestamp updateTime;
}