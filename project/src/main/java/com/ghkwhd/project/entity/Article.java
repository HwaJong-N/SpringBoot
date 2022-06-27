package com.ghkwhd.project.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity // DB가 해당 객체를 인식 가능
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Getter
public class Article {

    @Id // 대푯값을 지정
    @GeneratedValue // 자동 생성을 위한 어노테이션
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

}
