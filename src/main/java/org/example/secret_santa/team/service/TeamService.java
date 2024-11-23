package org.example.secret_santa.team.service;

import lombok.RequiredArgsConstructor;
import org.example.secret_santa.common.converter.UserAuthorizationConverter;
import org.example.secret_santa.matching.entity.Matching;
import org.example.secret_santa.matching.repository.MatchingRepository;
import org.example.secret_santa.member.entity.Member;
import org.example.secret_santa.member.exception.MemberNotFoundException;
import org.example.secret_santa.member.repository.MemberRepository;
import org.example.secret_santa.mission.entity.Mission;
import org.example.secret_santa.team.dto.*;
import org.example.secret_santa.team.entity.Team;
import org.example.secret_santa.team.mapping.MemberTeam;
import org.example.secret_santa.team.repository.MemberTeamRepository;
import org.example.secret_santa.team.repository.TeamRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeamService {
    private final TeamRepository teamRepository;
    final MemberRepository memberRepository;
    final MemberTeamRepository memberTeamRepository;
    private final UserAuthorizationConverter userAuthorizationConverter;
    final MatchingRepository matchingRepository;


    //    public Team createTeam(TeamRequestDto.AddTeamDto teamDto) {
//        Team team = TeamRequestDto.AddTeamDto.convertDtoToTeam(teamDto);
//        team.generateInviteCode(); // 초대 코드 생성
//        team.setCurrentHeadCount(0);
//
//        // 방장(Member) 정보 가져오기
//        Member creator = memberRepository.findById(userAuthorizationConverter.getAuthenticatedMember().getId())
//                .orElseThrow(() -> new RuntimeException("Member not found"));
//
//        // 방장을 Team에 추가
//        Team savedTeam = teamRepository.save(team);
//        MemberTeam memberTeam = new MemberTeam(savedTeam, creator);
//        team.addMemberTeam(memberTeam); // Team에 MemberTeam 추가
//        team.plusCurrentHeadCount(); // 인원수 증가
//
//        creator.addMemberTeam(memberTeam); // Member에 MemberTeam 추가
//
//        // 저장
//        memberTeamRepository.save(memberTeam);
//
//        return teamRepository.save(team);
//    }
    @Transactional
    public Team createTeam(TeamRequestDto.AddTeamDto teamDto) {
        Team team = TeamRequestDto.AddTeamDto.convertDtoToTeam(teamDto);
        team.generateInviteCode(); // 초대 코드 생성

        return teamRepository.save(team);
    }

    @Transactional
    public void joinTeam(String inviteCode) {
        // 초대 코드로 팀 찾기
        Team team = teamRepository.findByInviteCode(inviteCode)
                .orElseThrow(() -> new RuntimeException("Invalid invite code"));

        // 멤버 팀에 등록
        Member member = memberRepository.findById(userAuthorizationConverter.getAuthenticatedMember().getId())
                .orElseThrow(() -> new RuntimeException("Member not found"));

        MemberTeam memberTeam = new MemberTeam(team, member);

        // 팀에 멤버를 추가하고 팀의 현재 인원수 증가
        team.addMemberTeam(memberTeam);
        team.plusCurrentHeadCount();

        // 멤버에 팀을 추가
        member.addMemberTeam(memberTeam);

        memberTeamRepository.save(memberTeam);
    }

    @Transactional
    public List<TeamResponseDto.GetTeamList> findAllByMemberId() {

        // 해당 멤버가 속한 MemberTeam 목록을 가져옴
        Member member = memberRepository.findById(userAuthorizationConverter.getAuthenticatedMember().getId()).get();
        List<MemberTeam> memberTeams = memberTeamRepository.findMemberTeamsByMember(member);

        // MemberTeam에서 팀 정보만 추출하여 DTO로 변환
        return memberTeams.stream()
                .map(MemberTeam::getTeam)  // MemberTeam에서 Team 객체를 추출
                .map(TeamResponseDto.GetTeamList::fromTeam)  // Team을 GetTeamList DTO로 변환
                .collect(Collectors.toList());
    }
    /**
     * 채팅방으로 이동
     * =>>> 로빈님 코드 올리신 후 연동
     * case 1 : 아직 모집되지 않음 -> 대기방으로 이동
     * case 2 : 모집완료 -> 마니또를 보여줌
     * case 3 : 다시 들어감 -> 미션방으로 이동
     *
     * */
    @Transactional
    public GoRoomResponseDTO goRoom(Long teamId) {
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new IllegalArgumentException("팀을 찾을 수 없습니다."));

        if (team.getCurrentHeadCount() < team.getHeadCount()) {
            // 대기방으로 이동
            List<String> nicknames = findAllMembersByTeamId(teamId);
            return new GoRoomResponseDTO("대기방", team.getHeadCount(), team.getCurrentHeadCount(), nicknames, null, null);

        } else if (team.getCurrentHeadCount() >= team.getHeadCount() && !isOpenedFromMember(teamId)) {
            // 마니또를 보여줌
            MatchingMessageDTO matchingMessageDTO = showMatching(teamId, userAuthorizationConverter.getAuthenticatedMember().getId());
            memberTeamRepository.updateIsOpenedStatus(teamId, userAuthorizationConverter.getAuthenticatedMember().getId());

            teamRepository.save(team);
            return new GoRoomResponseDTO("매칭확인", null, null, null, matchingMessageDTO, null);

        } else if (isOpenedFromMember(teamId)) {
            // 미션방으로 이동
            MissionDTO missionDTO = getTodayMission(teamId, userAuthorizationConverter.getAuthenticatedMember().getId());
            return new GoRoomResponseDTO("미션방", null, null, null, null, missionDTO );
        }

        return new GoRoomResponseDTO("알 수 없음", null,  null, null, null, null);
    }
    public boolean isOpenedFromMember(Long teamId) {
        return memberTeamRepository.findIsOpenedByTeamIdAndMemberId(teamId, userAuthorizationConverter.getAuthenticatedMember().getId());

    }

    @Transactional

    public List<String> findAllMembersByTeamId(Long teamId) {
//        Team team = teamRepository.findById(teamId).get();
        List<String> nickNames = memberTeamRepository.findMembersById(teamId);
        return nickNames;

    }

    @Transactional
    public MissionDTO getTodayMission(Long teamId, Long memberId) {
//        // 예시: Matching 객체를 가져오는 로직
        Matching matching = matchingRepository.findByGiverMemberIdAndTeamId(teamId, memberId).get();
//        // 예시: 해당 Matching에 맞는 미션을 가져오는 로직
//        Mission todayMission = missionService.findByMatchingId(matching.getMatchingId());
//
//        // 미션의 제목과 설명을 반환
//        return new MissionDTO(todayMission.getTitle(), todayMission.getDescription());
        return new MissionDTO(1L, "미션제목", "닉네임5678", "닉네임1234");
    }

    @Transactional
    public MatchingMessageDTO showMatching(Long teamId, Long memberId) {
        // 예시: 매칭을 처리하는 로직
        Member member = memberRepository.findById(userAuthorizationConverter.getAuthenticatedMember().getId()).get();
        String giverNickname = member.getNickName(); // 선물하는 사람의 닉네임
        Matching matching = matchingRepository.findByGiverMemberIdAndTeamId(memberId, teamId).get(); // 마니또 받을 사람의 닉네임

        String receiverNickname = matching.getReceiverMember().getNickName();

        // 마니또 메시지 포맷
        MatchingMessageDTO matchingMessageDTO = new MatchingMessageDTO(giverNickname, receiverNickname);

        return matchingMessageDTO;
    }

}





















