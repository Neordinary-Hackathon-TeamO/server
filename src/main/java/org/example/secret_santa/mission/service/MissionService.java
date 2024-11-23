package org.example.secret_santa.mission.service;

import lombok.RequiredArgsConstructor;
import org.example.secret_santa.match.entity.Match;
import org.example.secret_santa.match.repository.MatchRepository;
import org.example.secret_santa.mission.entity.Mission;
import org.example.secret_santa.mission.repository.MissionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionService {

    private final MissionRepository missionRepository;
    private final MatchRepository matchRepository;

    public Mission getOwnMission(Long memberId) {

        Match match = matchRepository.findAllByGiver_member_id(memberId);
        Mission mission = missionRepository.findAllByMatch(match.getId());

        return mission;
    }

}
