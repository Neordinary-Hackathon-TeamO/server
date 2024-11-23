package org.example.secret_santa.mission.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionService {

    /*
    private final MissionRepository missionRepository;
    private final MatchRepository matchRepository;

    public Mission getOwnMission(Long memberId) {

        Matching match = matchRepository.findAllByGiver_member_id(memberId);
        Mission mission = missionRepository.findAllByMatch(match.getId());

        return mission;
    }

     */
}

    /*
    public void putMission(Long memberId, ExecuteMission executeMission) {

        Mission mission = missionRepository.findAllByMatch(memberId);



}

     */
