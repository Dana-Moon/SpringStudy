package com.example.springproject.entity.board;

import com.example.springproject.entity.account_info.Member;
import com.example.springproject.entity.base.BaseTimeEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class Comments extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @Column
    private String commentid;

    private String comments;

    @Transient
    private Long board_seq;

    //CascadeType.Remove를 왜 쓰는지 생각해 볼 것.
    //board_seq랑 seq를 가지고 비교? join?
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "board_seq", referencedColumnName = "seq")
    private Member board;
}
