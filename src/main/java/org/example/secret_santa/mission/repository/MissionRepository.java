package org.example.secret_santa.mission.repository;

import org.example.secret_santa.matching.entity.Matching;
import org.example.secret_santa.mission.entity.Mission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MissionRepository extends JpaRepository<Mission, Long> {


    Mission findByMatching(Matching matching);

    List<Mission> findAllByIsSuccessTrue();

    @Query("SELECT Mission FROM Mission mi JOIN Matching ma ON mi.matching.id = ma.id WHERE ma.giverMember.id= :memberId")
    Mission findByMemberId(Long memberId);



}
