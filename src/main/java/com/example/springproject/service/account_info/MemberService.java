package com.example.springproject.service.account_info;

import com.example.springproject.domain.account_info.Member;

import java.util.List;

//이 구조에 맞추어서 구현을 해주는 애가 있다. 그게 바로 MemberServiceLmple이다.
public interface MemberService {
    //Email 또는 ID를 조회하여 튜플을 찾기
    /*
    List<Member> getMemberWhereIdOrEmail(String Id, String Email);
     */
    Member getMemberWhereIdOrEmail(String Id, String Email);

    Member getMemberWhereIdAndROWNUL1(String Id);
    //Member 타입으로 리턴하겠다.
    List<Member> getMemberList();

    void insertMember(Member member);

    Member getMember(Member member);

    void updateMember(Member member);

    void deleteMember(Member member);
}
