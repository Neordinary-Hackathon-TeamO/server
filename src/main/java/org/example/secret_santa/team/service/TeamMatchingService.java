package org.example.secret_santa.team.service;

import lombok.RequiredArgsConstructor;
import org.example.secret_santa.matching.entity.Matching;
import org.example.secret_santa.matching.repository.MatchingRepository;
import org.example.secret_santa.member.entity.Member;
import org.example.secret_santa.member.repository.MemberRepository;
import org.example.secret_santa.team.entity.Team;
import org.example.secret_santa.team.mapping.MemberTeam;
import org.example.secret_santa.team.repository.MemberTeamRepository;
import org.example.secret_santa.team.repository.TeamRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamMatchingService {

    private final MatchingRepository matchingRepository;
    private final MemberRepository memberRepository;
    private final MemberTeamRepository memberTeamRepository;
    private final TeamRepository teamRepository;

    // 팀에 속한 멤버들을 랜덤으로 매칭하는 메서드
    public void matchMembers(Long teamId) {
        // 1. 팀 정보를 조회
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new RuntimeException("Team not found"));

        // 2. 팀에 속한 멤버 리스트 가져오기
        List<MemberTeam> memberTeams = memberTeamRepository.findByTeamId(teamId);
        List<Member> members = memberTeams.stream()
                .map(memberTeam -> memberTeam.getMember())
                .collect(Collectors.toList());

        // 3. 멤버들을 랜덤으로 섞기
        Collections.shuffle(members);

        // 4. 랜덤 매칭: 매칭된 리스트에 따라 Matching 객체 생성
        for (int i = 0; i < members.size(); i++) {
            Member giver = members.get(i);
            Member receiver = members.get((i + 1) % members.size());  // 순차적으로 다음 멤버와 매칭 (마지막 멤버는 첫 번째 멤버와 매칭)

            // 5. 매칭 생성
            Matching matching = Matching.builder()
                    .giverMember(giver)
                    .receiverMember(receiver)
                    .team(team)
                    .isSuccess(false)  // 초기 상태로 미성공 처리
                    .build();

            // 6. 매칭 정보 저장
            matchingRepository.save(matching);
        }
    }
}
