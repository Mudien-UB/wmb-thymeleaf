package com.hehe.wmb.entity;

import com.hehe.wmb.entity.enums.MenuCategory;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;

@Entity
@Table(name = "menu")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, updatable = false, nullable = false)
    private Long id;

    @Column(name = "name", unique = true, length = 100, nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    @Builder.Default
    private Double price = 0d;

    @Column(name = "menu_category", nullable = false)
    @Enumerated(EnumType.STRING)
    private MenuCategory menuCategory;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = new Timestamp(System.currentTimeMillis());
    }

}
