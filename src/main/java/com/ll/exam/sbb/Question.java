package com.ll.exam.sbb;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
// 아래 클래스는 엔티티 클래스
// 아래 클래스와 1:1로 매칭되는 테이블이 DB에 존재하지 않는다면 자동 생성
@Entity
public class Question {
    @Id // PK 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Integer id;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    @OneToMany(mappedBy = "question", cascade = {CascadeType.ALL}) // PERSIST : 관련된 내용들이 저장될때 함께 저장한다.
    private List<Answer> answerList = new ArrayList<>();

    public void addAnswer(Answer answer) {
        answer.setQuestion(this); // 해당하는 Answer의 Question부분에 이 Question 객체를 저장한다.
        getAnswerList().add(answer);
    }
}