package com.novatech.cybertech.models;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;


//@Setter
@Getter
@Builder
@ToString
@EqualsAndHashCode
public class Review {

    private Long id;

    private User user;

    private Integer rating;

    private String comment;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private Product product;
}