package org.example.secret_santa.matching.repository;

import org.example.secret_santa.matching.entity.Matching;
import org.example.secret_santa.member.entity.Member;
import org.example.secret_santa.team.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MatchingRepository extends JpaRepository<Matching, Long> {

    Matching findByGiverMemberAndTeam(Member member, Team team);
    //Matching findByGiverMember(Long giverMemberId);

}
