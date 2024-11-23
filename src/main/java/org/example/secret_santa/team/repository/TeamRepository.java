package org.example.secret_santa.team.repository;

import org.example.secret_santa.team.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    // inviteCode로 팀을 조회하는 메서드
    Optional<Team> findByInviteCode(String inviteCode);


}
