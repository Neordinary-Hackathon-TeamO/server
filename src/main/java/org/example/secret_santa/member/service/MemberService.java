package org.example.secret_santa.member.service;

import lombok.RequiredArgsConstructor;
import org.example.secret_santa.member.dto.RegisterInfo;
import org.example.secret_santa.member.dto.UpdateInfo;
import org.example.secret_santa.member.dto.ViewMyInfo;
import org.example.secret_santa.member.entity.Member;
import org.example.secret_santa.member.exception.MemberDuplicationException;
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
        Optional<Member> byMemId = memberRepository.findByMemId(registerInfo.getMemId());
        if(byMemId.isPresent())
            throw new MemberDuplicationException();
        Member member = RegisterInfo.toEntity(registerInfo);
        member.hashPassword(bCryptPasswordEncoder);
        return memberRepository.save(member).getId();
    }
    public ViewMyInfo viewMyInfo(String memId) {
        Optional<Member> member = memberRepository.findByMemId(memId);
        if(member.isEmpty())
            throw new MemberNotFoundException();
        return ViewMyInfo.of(member.get());
    }
    public void deleteMember(Long memberId) {
        memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        memberRepository.deleteById(memberId);
    }
    public Long updateMember(Long memberId, UpdateInfo updateInfo) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(MemberNotFoundException::new);
        member.updateInfo(updateInfo);
        return memberId;
    }

    public String getNickname(Long memberId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(MemberNotFoundException::new);

        return member.getNickName();
    }

}
//
