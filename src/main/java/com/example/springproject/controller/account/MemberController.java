package com.example.springproject.controller.account;

import com.example.springproject.entity.account_info.Member;
import com.example.springproject.service.account.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

//디스패쳐 서블릿이 컨트롤러를 찾기 위해서 @Controller라고 선언
// 8/12 아래 path 설정 완료
@Controller
@RequestMapping(path = "/account")
public class MemberController {
    //게시판 : 사용자 관점, 시스템 관리 관점(회원관리, 게시판관리, 컨텐츠관리) [웹 솔루션을 관리하는 오너를 위한 사이트]
    //따라서 클라이언트가 두 분류인 것을 염두해야 한다.
    // 8/12 getAccountList, deleteAccount, updateAccount

    /* 8/18 Autowired란, MemberController 클래스가 실행되면 MemberService를 불러와서 주입 당하는 것
    Autowired를 사용해서 MemberController는 MemberService를 주입당하겠다고 선언
    Springboot는 인식 함 : MemberController가 실행하려면 MemberService가 필요함.
    -장점 : MemberController가 실행되는 시점에서 필요한 객체만 실행할 수 있는 절약
    -장점2 : 이미 컨테이너에 있는 객체를 활용하여 최대한 인스턴스(객체)를 최소한 사용
    아래 @Autowired란, 필드 주입 방식이다.
    객체의 데이터 = 필드, 생성자, 메서드
    필드 주입의 경우에는, 2개 이상 주입할 시 어떤 게 먼저 주입당하는지를 모름
    주입 당하는 A와 B가 서로 주입당할 경우, 어떤 게 먼저 생성할 지 모르는 문제가 발생함. (IoC. 제어의 역전)

    일반 자바라면, 실행하는 클래스(main) 안에서 인스턴스를 만들어서 인스턴스 안에 있는 메서드를 실행 (static : 불러옴)
    실행되는 클래스(main)가 먼저 존재하고 인스턴스를 후에 생성
    그렇다면, 인스턴스는 언제 생성되는가? 이것에 대해 아는 것이 Di 개념 중 하나이다.
    스프링 버전.
     */
    /*
    MemberService 라는 객체를 선언
    필드주입 장식은 @Autowired를 통해 컨테이너에 주입당함(할당). 순환참조.
    final은 변하지 않는 1개 : MemberController는 안심하고 MemberService 사용
     */
    private final MemberService memberService;

    /*
    생성자 주입방식은 아래 생성자에 @Autowired를 붙혀서 컨테이너에 주입 당함. 순환참조 방지.
    MemberController 클래스의 생성자를 선언
    필드 주입방식, 생성자 주입방식은 @Autowired가 어디에 붙어 있는지에 따른다.
    매개변수를 MemberService로 받아서 위에 있는 필드값 MemberService에 할당
    -장점 : 객체 생성 시점에서 생성자를 통해 주입 받기 때문에, 순서 명확해집니다. 따라서 생성자 주입 방식을 권장함.
     */

    /*
    3번째 setter 방식이 있다. setter 메소드를 통해 집어넣을 수 있다. 단점 : 명료화하지 않다. 어느 시점에서 setter를 쓰는지 알 수 없기 때문에 에러가 발생할 수 있다. (setter 방식은 문제를 야기할 경우가 높다.)
     */
    @Autowired
    protected MemberController(MemberService memberService) {
        this.memberService = memberService;
    }



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
    public String getAccountList(Model model, Member member) {
        //model : 컨트롤러에서 작업한 결과물을 HTML에 전달하기 위한 매개체
        //addAttribute : key/value로 데이터를 저장
        //attributeName(key) : 뒤에 있는 value를 호출하기 위한 문자열(key)
        //memberService.getMemberList() : @Autowired로 선언된 MemberService 클래스를 호출하여
        //getMemberList() 메서드 실행
//        List<Member> memberList = memberService.getMemberList();
//        model.addAttribute("memberList", memberService.getMemberList());
        model.addAttribute("memberList", memberService.descMember(member));
        return "/account/getAccountList";
    }

    // 8/22 수업 내용 8/29 추가함
    /*
    @GetMapping("/getAccountList")
    public String getAccountList(Model model) {
        model.addAttribute("memberList", memberService.getMemberListEncodingByMemberList(memberService.getMemberList()));
        return "/account/getAccountList";
    }
     */

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

    //updateAccount : 회원정보 업데이트 8/16
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
     > 장점1: 최신회원들을 관리하는 마케팅부서에게 도움됨
     > 장점2: DB 백업할 때도 테이블 파편화로 트랜잭션 위험 또는 시간 절약
     > 단점 : Admin(관리자)는 모든 회원정보를 볼 때, 다수의 테이블을 동시에 봐야 하기 때문에 JOIN을 써서 속도를 느림
    */

    // 8/17 select
    @GetMapping("/selectAccount")
    public String selectAccount() {
        return "account/selectAccount";
    }

    @PostMapping("/selectAccount")
    public String selectAccount(Member member, Model model) {
        model.addAttribute("member", memberService.getMemberWhereIdOrEmail(member.getEmail(), member.getId()));
        return "account/resultAccount";
    }

    // 8/18 search
    @GetMapping("/search")
    public String searchEmail() {
        return "/account/searchEmail";
    }

    @PostMapping("/search")
    public String searchEmail(@RequestParam("keyword") String keyword, Model model, Member member) {
        System.out.println(member.getId());
        System.out.println(member.getPassword());
        System.out.println(member.getEmail());
        System.out.println(keyword);
        model.addAttribute("memberEmail", memberService.getSearchEmail(keyword));
        return "account/searchEmail";
    }
}
