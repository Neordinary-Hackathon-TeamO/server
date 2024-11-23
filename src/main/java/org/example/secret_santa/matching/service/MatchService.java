package org.example.secret_santa.matching.service;

import lombok.RequiredArgsConstructor;
import org.example.secret_santa.matching.dto.ViewMatch;
import org.example.secret_santa.matching.entity.Matching;
import org.example.secret_santa.matching.repository.MatchRepository;
import org.example.secret_santa.member.repository.MemberRepository;
import org.example.secret_santa.team.repository.MemberTeamRepository;
import org.hibernate.dialect.function.LpadRpadPadEmulation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchService {
    private final MatchRepository matchRepository;
    private final MemberTeamRepository memberTeamRepository;
    private final MemberRepository memberRepository;
    @Transactional
    public Page<ViewMatch> viewAllMatches(Pageable pageable, Long teamId) {
        Page<Matching> matchings = matchRepository.findAllByTeamId(teamId, pageable);
        return matchings.map(matching -> ViewMatch.of(matching));
    }
}
