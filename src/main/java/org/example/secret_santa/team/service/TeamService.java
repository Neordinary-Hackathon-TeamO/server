package org.example.secret_santa.team.service;

import lombok.RequiredArgsConstructor;
import org.example.secret_santa.common.converter.UserAuthorizationConverter;
import org.example.secret_santa.member.entity.Member;
import org.example.secret_santa.member.exception.MemberNotFoundException;
import org.example.secret_santa.member.repository.MemberRepository;
import org.example.secret_santa.team.dto.TeamRequestDto;
import org.example.secret_santa.team.dto.TeamResponseDto;
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
    public List<String> findAllMembersByTeamId(Long teamId) {
//        Team team = teamRepository.findById(teamId).get();
        List<String> nickNames = memberTeamRepository.findMembersByTeamId(teamId);
        return nickNames;

    }
//    @Transactional
//    public List<> showMatching(Long memberId) {
//
//    }
//    @Transactional
//    public List<> showMatching(Long memberId) {
//
//    }


}





















