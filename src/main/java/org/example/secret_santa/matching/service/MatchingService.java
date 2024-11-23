package org.example.secret_santa.matching.service;


import lombok.RequiredArgsConstructor;
import org.example.secret_santa.common.converter.UserAuthorizationConverter;
import org.example.secret_santa.matching.entity.Matching;
import org.example.secret_santa.matching.repository.MatchingRepository;
import org.example.secret_santa.member.entity.Member;
import org.example.secret_santa.member.exception.MemberNotFoundException;
import org.example.secret_santa.member.repository.MemberRepository;
import org.example.secret_santa.team.entity.Team;
import org.example.secret_santa.team.exception.TeamNotFoundException;
import org.example.secret_santa.team.repository.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MatchingService {

    private final MatchingRepository matchingRepository;
    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;
    private final UserAuthorizationConverter userAuthorizationConverter;


    public String getReceiverNickname(Long groupId) {
        Member member = memberRepository.findById(userAuthorizationConverter.getAuthenticatedMember().getId())
            .orElseThrow(() -> new RuntimeException("Member not found"));




        Team team = teamRepository.findById(groupId)
            .orElseThrow(TeamNotFoundException::new);
        Matching matching = matchingRepository.findByGiverMemberAndTeam(member, team);
        Member member2 = matching.getReceiverMember();
        Member member3 = memberRepository.findById(member2.getId())
            .orElseThrow(MemberNotFoundException::new);

        return member3.getNickName();
    }



}
