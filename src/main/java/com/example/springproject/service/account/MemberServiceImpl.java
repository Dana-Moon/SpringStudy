package com.example.springproject.service.account;

import com.example.springproject.entity.account.Member;
import com.example.springproject.repository.account.MemberRepository;
import com.example.springproject.service.encrypt.EncryptAES256;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberServiceImpl implements MemberService{

    private final MemberRepository memberRepo;

    private final EncryptAES256 encryptAES256;

    @Autowired
    protected MemberServiceImpl(MemberRepository memberRepo, EncryptAES256 encryptAES256) {
        this.memberRepo = memberRepo;
        this.encryptAES256 = encryptAES256;
    }

    //(List<Member>) : 뒤에 있는 결과값을 형변환
    //memberRepo : @Autowired MemberRepository를 통해 기능 실행
    //findAll() : memberRepo에 있는 모든 정보 가져오기 메서드 실행
    //return에서 (List<Member>) 부분을 8/22일 member와 board 이어주면서 추가했는데 회색으로 떠있다. 굳이 해야하는 설정인지는 모르겠다. redundant(불필요한) 이라고 떠있다. 지워야겠다.
    @Override
    public List<Member> getMemberList() {
        return memberRepo.findAll();
    }

    //회원 1명의 정보를 Entiiy에 맞춰서 DB에 저장
    @Override
    public void insertMember(Member member) {
        memberRepo.save(member);
    }

    @Override
    public Member getMember(Member member) {
        return memberRepo.findById(member.getSeq()).get();
    }

    @Override
    public void updateMember(Member member) {
        //1. seq를 통해서 튜플 정보를 모두 가져오기
        //2. 가져온 튜플 정보 중 수정할 내용 적용
        //3. DB에 저장 (덮어쓰기)
        //findById().get() : 고유키 기준으로 튜플 전체 데이터 가져오기
        Member findMember = memberRepo.findById(member.getSeq()).get();
        //튜플 전체 내용 중에 ID/email 주소 수정 (setter)
        findMember.setId(member.getId());
        findMember.setEmail(member.getEmail());
        //CrudRepo의 save 메서드를 통해 데이터 저장
        memberRepo.save(findMember);

        /* 고유키를 쓰는 이유
        1. 다른 튜플을 식별할 수 있는 값 (데이터 한 줄) : DB관점에서 보는 것
        2. 다른 테이블의 튜플과 연동하기 위한 값(JOIN, 외래키) : DB관점
        3. 객체지향 방법으로 DB를 저장 (트랜잭션 ACID)
        3-1. 영속성 : DB에 영구저장
        3-2. *고립성(Isolation) : 다른 트랜잭션 작업에 연관되지 않도록 해주는 것
        3-3. 예시) 관리자 1은 seq = 10의 회원정보를 바꿨다. 이미 접속해있던 관라자 2가 seq=10 회원의 정보를 조회, 수정.
        -> 10번회원의 회원정보를 바꾸는 작업이 한 개의 트랜잭션. 관리자2의 seq10 회원의 정보를 조회하고, 수정하는 작업도
        한 개의 트랜잭션
        -> 관리자 1의 트랜잭션 작업이 완료될 때까지 관리자2의 seq=10 회원 정보의 엣날 정보를 조회하고 있고,
           관리자 1의 트랜잭션 작업이 완료되는 순간까지 관리자2는 seq10회원 정보를 수정할 수 없다.
        다른 필드 값은 수정이 가능해도, seq는 튜플의 식별자로써 수정이 불가하여, 관리자1, 2의 트랜잭션 작업을 스프링부트에서
        구분할 수 있다.
         */
    }

    @Override
    public void deleteMember(Member member) {
        memberRepo.deleteById(member.getSeq());
    }


    //List<Member> : 리턴타입은 List, 속성은 Member
    /*
    @Override
    public List<Member> getMemberWhereIdOrEmail(String Id, String Email) {
        return memberRepo.findMemberByIdOrEmail(Id, Email);
    }
     */
    @Override
    public Member getMemberWhereIdOrEmail(String Id, String Email) {
        return memberRepo.findMemberByIdOrEmail(Id, Email);
    };

//    @Override
//    public Member getMemberWhereIdAndROWNUL1(String id) {
//        return memberRepo.findFirstById(id);
//    }

    @Override
    public List<Member> descMember(Member member) {
        return memberRepo.findAll(Sort.by(Sort.Direction.DESC,"seq"));
    }

    @Override
    public List<Member> getSearchEmail(String keyword) {
        return memberRepo.findMembesrByEmail(keyword);
    }

    @Override
    public List<Member> getMemberListEncodingByMemberList(List<Member> memberList) {
        for (Member member : memberList) {
            try {
                member.setPassword(encryptAES256.encrypt(member.getPassword()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return memberList;
    }
}
