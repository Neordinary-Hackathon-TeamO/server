package org.example.secret_santa.mission.service;

import lombok.RequiredArgsConstructor;
import org.example.secret_santa.common.converter.UserAuthorizationConverter;
import org.example.secret_santa.matching.entity.Matching;
import org.example.secret_santa.matching.repository.MatchingRepository;
import org.example.secret_santa.member.entity.Member;
import org.example.secret_santa.member.exception.MemberNotFoundException;
import org.example.secret_santa.member.repository.MemberRepository;
import org.example.secret_santa.mission.dto.MissionRequestDto.ExecuteMission;
import org.example.secret_santa.mission.entity.Mission;
import org.example.secret_santa.mission.repository.MissionRepository;
import org.example.secret_santa.team.entity.Team;
import org.example.secret_santa.team.exception.TeamNotFoundException;
import org.example.secret_santa.team.repository.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MissionService {


    private final MissionRepository missionRepository;
    private final MatchingRepository matchingRepository;
    private final MemberRepository memberRepository;
    private final TeamRepository teamRepository;

    private final UserAuthorizationConverter userAuthorizationConverter;


    public Mission getOwnMission( Long groupId) {



        Member member = memberRepository.findById(userAuthorizationConverter.getAuthenticatedMember().getId())
            .orElseThrow(() -> new RuntimeException("Member not found"));



        Team team = teamRepository.findById(groupId)
            .orElseThrow(TeamNotFoundException::new);

        Matching match = matchingRepository.findByGiverMemberAndTeam(member, team);
        Mission mission = missionRepository.findByMatching(match);

        return mission;
    }




    public void putMission(MultipartFile image, String message) {

        Member member = memberRepository.findById(userAuthorizationConverter.getAuthenticatedMember().getId())
            .orElseThrow(() -> new RuntimeException("Member not found"));

        Mission mission = missionRepository.findByMemberId(member.getId());

        mission.setMissionMessage(message);
        //mission.사진업로드


    }

}
