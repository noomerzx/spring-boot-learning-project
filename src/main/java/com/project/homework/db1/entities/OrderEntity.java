package com.project.homework.db1.entities;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "Order")
@Table(name = "t_order")
public class OrderEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String couponCode;
    private Float discount;
    private Float net;
    private String status;
    private Timestamp createTime;
    private Timestamp updateTime;
    private Integer itemCount;
    @OneToMany(targetEntity=OrderItemEntity.class)
    private List<OrderItemEntity> orderItems;
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "order_by_user_id", nullable = false)
    @MapsId
    private UserEntity user;
}