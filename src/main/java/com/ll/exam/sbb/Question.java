package com.ll.exam.sbb;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
// 아래 클래스는 엔티티 클래스
// 아래 클래스와 1:1로 매칭되는 테이블이 DB에 존재하지 않는다면 자동 생성
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Integer id;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;
}