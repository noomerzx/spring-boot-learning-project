package com.project.homework.entities;

import java.sql.Timestamp;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "TokenHistory")
@Table(name = "token_history")
public class TokenHistoryEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column
    private String token;

    @Column
    private Long ttl;

    @Column
    private Long userId;

    private Timestamp createTime;
}