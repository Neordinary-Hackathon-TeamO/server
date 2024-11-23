package org.example.secret_santa.mission.repository;

import org.example.secret_santa.mission.entity.Mission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionRepository extends JpaRepository<Mission, Long> {

    Mission findAllByMatch(Long matchId);

}
