package org.example.secret_santa.security.service;

import org.example.secret_santa.member.entity.Member;
import org.example.secret_santa.member.repository.MemberRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final MemberRepository memberRepository;

    public CustomUserDetailsService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String memId) throws UsernameNotFoundException {
        //DB에서 조회
        Optional<Member> member = memberRepository.findByMemId(memId);
        if (member.isPresent()) {
            return new CustomUserDetails(member.get());
        }
        return null;
    }
}
