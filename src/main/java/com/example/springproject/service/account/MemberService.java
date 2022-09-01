package com.example.springproject.service.account;

import com.example.springproject.entity.account.Member;

import java.util.List;

//이 구조에 맞추어서 구현을 해주는 애가 있다. 그게 바로 MemberServiceLmple이다.
public interface MemberService {
    //Email 또는 ID를 조회하여 튜플을 찾기
    /*
    List<Member> getMemberWhereIdOrEmail(String Id, String Email);
     */
    Member getMemberWhereIdOrEmail(String Id, String Email);

//    Member getMemberWhereIdAndROWNUL1(String Id);
    //Member 타입으로 리턴하겠다.
    List<Member> getMemberList();

    void insertMember(Member member);

    Member getMember(Member member);

    void updateMember(Member member);

    void deleteMember(Member member);

    List<Member> descMember(Member member);


    List<Member> getSearchEmail(String keyword);

    //3조
    //결과값 : 입력 받은 정보(email, id, pw)가 유사사실유무 확인 후 비밀번호 변경(updateMember의 password)
//    boolean booleanSearchUserByEmail(Member member);
//    boolean booleanSearchUserById(Member member);
//    boolean booleanSearchUserByPassword(Member member);
//
//    //6조
//    //별표처리 MemberList (replace)
//    List<Member> getMemberListEmailSecurityStarByMemberList(List<Member> memberList);
//
//    //2,5조
//    //민감데이터 (SHA256...?)
    List<Member> getMemberListEncodingByMemberList(List<Member> memberList);
//
//    //작성자의 모든 게시글 출력
//    List<Member> getBoardListAllBoardByMemberId(Member member);
//
//    //board의 작성자와 회원이 같은지 확인
//    boolean booleanMemberIdEqualsBoardWriterByMember(Member member);

    //4조
    //키워드분석
    //doNounsAnalysis

    //getAutoKeywordBoardList

    //1조
    //email @앞에 문자열과 id가 동일할 경우
//    boolean booleanEmailEqualsIdByMemberEmail(List<>)
    //id와 pw가 동일할 경우
//    boolean
    //30일 지난 회원에게 변경 페이지 안내
    //30일 후 redirect로 다른 페이지로 가도록 만들면 된다.
//    boolean booleanAfter30DaysChangePasswordByMemberUpdateDate(Member member);
    //비밀빈호 변경 테이블 생성 후 변경한 기록을 남긴 후, 변경 내용 최신 3회 내용과 비교
    //boolean booleanChangedPassword3CheckByMemberPassword(Member member);
}
