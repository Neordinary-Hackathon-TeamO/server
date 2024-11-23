package org.example.secret_santa.common;

import lombok.RequiredArgsConstructor;
import org.example.secret_santa.member.entity.Member;
import org.example.secret_santa.member.repository.MemberRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/*
@Configuration
@RequiredArgsConstructor
public class DataLoader {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public CommandLineRunner loadTestData(MemberRepository memberRepository)
    {
        return args -> {
            Member member = Member.builder()
                    .memId("song")
                    .nickName("송성훈")
                    .password("1234")
                    .profileImage("aopfshdoisdahfoi")
                    .build();
            member.hashPassword(bCryptPasswordEncoder);

            memberRepository.save(member);
        };
    }
}

 */
