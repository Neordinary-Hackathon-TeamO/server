package org.example.secret_santa.team.repository;

import org.example.secret_santa.team.mapping.MemberTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberTeamRepository extends JpaRepository<MemberTeam, Long> {
}
