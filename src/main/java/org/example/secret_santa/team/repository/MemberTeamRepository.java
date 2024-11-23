package org.example.secret_santa.team.repository;

import org.example.secret_santa.team.mapping.MemberTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface MemberTeamRepository extends JpaRepository<MemberTeam, Long> {
    List<MemberTeam> findByMemberId(Long memberId);
}
