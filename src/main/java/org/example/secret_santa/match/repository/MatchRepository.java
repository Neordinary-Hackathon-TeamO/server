package org.example.secret_santa.match.repository;

import org.example.secret_santa.match.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<Match, Long> {

    Match findAllByGiver_member_id(Long memberId);

}
