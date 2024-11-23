package org.example.secret_santa.member.service;

import lombok.RequiredArgsConstructor;
import org.example.secret_santa.member.dto.post.RegisterInfo;
import org.example.secret_santa.member.entity.Member;
import org.example.secret_santa.member.exception.MemberNotFoundException;
import org.example.secret_santa.member.repository.MemberRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    public Long registerMember(RegisterInfo registerInfo) {
        Member member = RegisterInfo.toEntity(registerInfo);
        member.hashPassword(bCryptPasswordEncoder);
        return memberRepository.save(member).getId();
    }
    public void deleteMember(Long memberId) {
        memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        memberRepository.deleteById(memberId);
    }

}
//
