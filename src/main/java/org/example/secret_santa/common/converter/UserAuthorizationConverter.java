package org.example.secret_santa.common.converter;

import lombok.RequiredArgsConstructor;
import org.example.secret_santa.member.entity.Member;
import org.example.secret_santa.member.repository.MemberRepository;
import org.example.secret_santa.security.service.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserAuthorizationConverter {
    private final MemberRepository memberRepository;

    public UserAuthorizationConverter(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    // 인증된 사용자의 memberId를 이용해 Member 엔티티를 반환
    public Member getAuthenticatedMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated()) {
            // 인증된 사용자 정보에서 memId를 추출
            CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
            String memId = userDetails.getUsername();  // memId는 username으로 처리된다고 가정

            // memId로 Member 엔티티 조회
            return memberRepository.findByMemId(memId)
                    .orElseThrow(() -> new RuntimeException("Member not found with memId: " + memId));
        } else {
            throw new RuntimeException("User not authenticated");
        }
    }
}

