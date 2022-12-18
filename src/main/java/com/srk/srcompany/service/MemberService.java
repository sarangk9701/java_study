package com.srk.srcompany.service;

import com.srk.srcompany.domain.Member;
import com.srk.srcompany.repository.MemberRepository;
import com.srk.srcompany.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//MemberService 클래스에 command+shift+t : test > java > service > MemberServiceTest 자동 생성
//@Service
public class MemberService {

    private final MemberRepository memberRepository;

    //@Autowired
    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }

    /**
     * 회원가입
     */
    public Long join(Member member){
        //같은 이름이 있는 중복 회원X
        //command + option + v : optional<Member> byName 자동으로 만들어
        //Optional<Member> result = memberRepository.findByName(member.getName());
        //result.ifPresent(m -> {

        validateDuplicateMember(member); //중복 회원 검증

        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName())
                .ifPresent(m -> {
                     throw new IllegalStateException("이미 존재하는 회원입니다.");
                 });
        //option + T : 메서드 추출 , command + m : validateDuplicateMember 생성 중복회원검증
    }

    /**
     * 전체 회원 조회
     */
    public List<Member> findMembers(){
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId){
        return memberRepository.findById(memberId);
    }
}
