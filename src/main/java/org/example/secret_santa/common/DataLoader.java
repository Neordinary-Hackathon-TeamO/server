package org.example.secret_santa.common;

import lombok.RequiredArgsConstructor;
import org.example.secret_santa.common.enums.TeamType;
import org.example.secret_santa.matching.entity.Matching;
import org.example.secret_santa.matching.repository.MatchRepository;
import org.example.secret_santa.member.entity.Member;
import org.example.secret_santa.member.repository.MemberRepository;
import org.example.secret_santa.team.entity.Team;
import org.example.secret_santa.team.mapping.MemberTeam;
import org.example.secret_santa.team.repository.MemberTeamRepository;
import org.example.secret_santa.team.repository.TeamRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class DataLoader {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Bean
    public CommandLineRunner loadTestData(MemberRepository memberRepository,
                                          TeamRepository teamRepository,
                                          MemberTeamRepository memberTeamRepository,
                                          MatchRepository matchRepository)
    {
        return args -> {
            Team team = Team.builder()
                    .name("팀1")
                    .teamType(TeamType.WORKER)
                    .headCount(5)
                    .build();
            teamRepository.save(team);

            for(int i=0; i<50; i++) {
                Member member = Member.builder()
                        .memId("song" + i)
                        .nickName("송성훈" + i)
                        .password("1234")
                        .profileImage("aopfshdoisdahfoi")
                        .build();
                member.hashPassword(bCryptPasswordEncoder);

                MemberTeam memberTeam = MemberTeam.builder()
                        .team(team)
                        .member(member)
                        .build();

                memberRepository.save(member);
                memberTeamRepository.save(memberTeam);
            }



            for(long i=1; i<10; i++) {
                Member member1 = memberRepository.findById(i).get();
                Member member2 = memberRepository.findById(i+1).get();

                Matching matching = Matching.builder()
                        .giverMember(member1)
                        .receiverMember(member2)
                        .team(team)
                        .build();
                matchRepository.save(matching);
            }


        };
    }
}
