package org.example.secret_santa.matching.repository;

import org.example.secret_santa.matching.entity.Matching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MatchRepository extends JpaRepository<Matching, Long> {
    Page<Matching> findAllByTeamId(Long team_id, Pageable pageable);

    Optional<Matching> findByGiverMemberIdAndTeamId(Long giverMemberId, Long teamId);

}
