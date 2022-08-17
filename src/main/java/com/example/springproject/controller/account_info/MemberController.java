package com.example.springproject.controller.account_info;

import com.example.springproject.domain.account_info.Member;
import com.example.springproject.service.account_info.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

//디스패쳐 서블릿이 컨트롤러를 찾기 위해서 @Controller라고 선언
// 8/12 아래 path 설정 완료
@Controller
@RequestMapping(path = "/account")
public class MemberController {
    //게시판 : 사용자 관점, 시스템 관리 관점(회원관리, 게시판관리, 컨텐츠관리) [웹 솔루션을 관리하는 오너를 위한 사이트]
    //따라서 클라이언트가 두 분류인 것을 염두해야 한다.
    // 8/12 getAccountList, deleteAccount, updateAccount
    @Autowired
    private MemberService memberService;

    //return 타입이 String인 이유 : HTML 파일명을 찾기 위해
    @GetMapping("/insertAccount")
    public String insertAccount() {
        return "/account/insertAccount";
    }

    //Member 라는 매개변수로 controller에 전달
    //Member(Entity)이고 DTO(Data Transfer Object)
    //DTO를 설명할 수 있으면 좋지만, DTO가 머냐고 했을 때, Entity라고 해도 50점은 맞는다.
    //어디선가 받거나 만든 데이터를 객체로 만드는 것
    //DTO에서 setter만 뺀 게 VO
    @PostMapping("/insertAccount")
    public String insertAccount(Member member) {
        //클라이언트에서 ID/ PW만 가지고 들어옴. 오류 날 것이다.
        //createDate
        //updateDate
        member.setCreateDate(new Date());
        member.setUpdateDate(new Date());

        memberService.insertMember(member);
        return "redirect:/account/getAccountList";
    }

    //member : 클라이언트에서 서버로 데이터를 받는 Entity
    //
    //getAccountList : 전체 회원 목록 보기 : 웹솔루션에서 웹시스템을 관리하는 관리자의 기능
    @GetMapping("/getAccountList")
    public String getAccountList(Model model) {
        //model : 컨트롤러에서 작업한 결과물을 HTML에 전달하기 위한 매개체
        //addAttribute : key/value로 데이터를 저장
        //attributeName(key) : 뒤에 있는 value를 호출하기 위한 문자열(key)
        //memberService.getMemberList() : @Autowired로 선언된 MemberService 클래스를 호출하여
        //getMemberList() 메서드 실행
//        List<Member> memberList = memberService.getMemberList();
        model.addAttribute("memberList", memberService.getMemberList());
        return "/account/getAccountList";
    }

//    @GetMapping("/getAccountList")
//    public String getAccountList() {
//        return "/account/getAccountList";
//    }

    //회원정보 상세보기
    @GetMapping("/getAccount")
    public String getAccount(Member member, Model model) {
        model.addAttribute("member", memberService.getMember(member));
        return "/account/getAccount";
    }

    // 8/12
    //deleteAccount : 회원정보 삭제
    @GetMapping("/deleteAccount")
    public String deleteBoard(Member member) {
        memberService.deleteMember(member);
        return "redirect:/account/getAccountList";
    }

    //updateAccount : 회원정보 업데이트
    @PostMapping ("/updateAccount")
    public String updateMember(Member member) {
        System.out.println("--------------------");
        System.out.println(member.getSeq());
        System.out.println(member.getId());
        System.out.println(member.getEmail());
        System.out.println(member.getPassword());
        memberService.updateMember(member);
        return "redirect:getAccountList";
    }

//    @GetMapping("/updateAccount")
//    public String updateBoardView(Member member, Model model) {
//        model.addAttribute("member", memberService.getMember(member));
//        return "insertAccount";
//    }



    //updateAccount : 회원정보 수정

    //기존데이터의 무결성 체크를 위한 데이터 전체 조회와 일부 수정작업(sql 특정 컬럼의 값을 모두 gmail.com > naver.com
    //email
    //백업 entity
    //회원정보가 일정 수치까지 다다르면 (혹은 기능적 이벤트가 발생) updateAccountAll이라는 메서드를 통해
    //기존 entity의 테이블의 정보를 모두 백엔드 entity에 저장
    //CrudRepository를 보면 인터페이스 메서드 findAll 회원정보 모두 불러온 뒤 saveAll 메서드로 저장

   /* +회원정보를 1개의 테이블에서 관리하지 않는다. > 회원정보가 누적이 되면, 1년 이상 로그인을 안한 장기 휴식회원들이 생긴다.
    1년 미접속 계정은 따로 테이블에 옮겨서 저장 (예전 스타일)
    날짜별로 (로그인 한 지) 1년이 지나면 새로 테이블을 생성해서 회원을 관리합니다. (회원가입한 날짜)
     > 최신회원들을 관리하는 마케팅부서에게 도움됨
     > DB 백업할 때도 테이블 파편화로 트랜잭션 위험 또는 시간 절약
     > 단점 : Admin(관리자)는 모든 회원정보를 볼 때, 다수의 테이블을 동시에 봐야 하기 때문에 JOIN을 써서 속도를 느림
    */

    // 8/16
    @GetMapping("/selectAccount")
    public String selectAccount() {
        return "account/selectAccount";
    }

    @PostMapping("/selectAccount")
    public String selectAccount(Member member, Model model) {
        model.addAttribute("member", memberService.getMemberWhereIdOrEmail(member.getEmail(), member.getId()));
        return "account/resultAccount";
    }

//    @GetMapping("/resultAccount")
//    public String resultAccount(Member member, Model model) {
//        model.addAttribute("member", memberService.)
//    }
}
