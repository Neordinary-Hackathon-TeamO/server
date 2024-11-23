package org.example.secret_santa.matching.service;


import lombok.RequiredArgsConstructor;
import org.example.secret_santa.matching.dto.ViewMatch;
import org.example.secret_santa.matching.entity.Matching;
import org.example.secret_santa.matching.repository.MatchingRepository;
import org.example.secret_santa.member.entity.Member;
import org.example.secret_santa.member.exception.MemberNotFoundException;
import org.example.secret_santa.member.repository.MemberRepository;
import org.example.secret_santa.team.entity.Team;
import org.example.secret_santa.team.exception.TeamNotFoundException;
import org.example.secret_santa.team.repository.TeamRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MatchingService {

    private final MatchingRepository matchingRepository;
    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;


    public String getReceiverNickname(Long memberId, Long groupId) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(MemberNotFoundException::new);

        Team team = teamRepository.findById(groupId)
            .orElseThrow(TeamNotFoundException::new);
        Matching matching = matchingRepository.findByGiverMemberAndTeam(member, team);
        Member member2 = matching.getReceiverMember();
        Member member3 = memberRepository.findById(member2.getId())
            .orElseThrow(MemberNotFoundException::new);

        return member3.getNickName();
    }

    @Transactional
    public Page<ViewMatch> viewAllMatches(Pageable pageable, Long teamId) {
        Page<Matching> matchings = matchingRepository.findAllByTeamId(teamId, pageable);
        return matchings.map(matching -> ViewMatch.of(matching));
    }

    @Transactional
    public Long guessManitto(Long teamId, Long manittoId, String memId) {
        Member member = memberRepository.findByMemId(memId).get();
        Optional<Matching> match = matchingRepository.findByGiverMemberIdAndTeamId(manittoId, teamId);
        System.out.println("match.get().getReceiverMember().getId() = " + match.get().getReceiverMember().getId());
        System.out.println("member.getId() = " + member.getId());
        if(match.get().getReceiverMember().getId().equals(member.getId())) {
            match.get().setIsSuccess(true);
            System.out.println("match = " + match.get().getTeam());
        }
        return member.getId();
    }



}
