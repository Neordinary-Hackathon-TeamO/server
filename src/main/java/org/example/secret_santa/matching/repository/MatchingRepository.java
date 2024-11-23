package org.example.secret_santa.matching.repository;

import org.example.secret_santa.matching.entity.Matching;
import org.example.secret_santa.member.entity.Member;
import org.example.secret_santa.team.entity.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MatchingRepository extends JpaRepository<Matching, Long> {

    Matching findByGiverMemberAndTeam(Member member, Team team);
    //Matching findByGiverMember(Long giverMemberId);

    Page<Matching> findAllByTeamId(Long team_id, Pageable pageable);

    Optional<Matching> findByGiverMemberIdAndTeamId(Long giverMemberId, Long teamId);
}
