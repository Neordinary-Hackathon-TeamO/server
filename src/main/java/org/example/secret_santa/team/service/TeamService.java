package org.example.secret_santa.team.service;

import lombok.RequiredArgsConstructor;
import org.example.secret_santa.member.entity.Member;
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

    public Team createTeam(TeamRequestDto.AddTeamDto teamDto) {
        Team team = TeamRequestDto.AddTeamDto.convertDtoToTeam(teamDto);
        team.generateInviteCode(); // 초대 코드 생성
        return teamRepository.save(team);
    }
    @Transactional
    public void joinTeam(String inviteCode, Long memberId) {
        // 초대 코드로 팀 찾기
        Team team = teamRepository.findByInviteCode(inviteCode)
                .orElseThrow(() -> new RuntimeException("Invalid invite code"));

        // 멤버 팀에 등록
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));

        MemberTeam memberTeam = new MemberTeam(team, member);
        team.addMemberTeam(memberTeam);
        team.plusCurrentHeadCount();
        member.addMemberTeam(memberTeam);
        memberRepository.save(member);
        memberTeamRepository.save(memberTeam);

    }
    @Transactional
    public List<TeamResponseDto.GetTeamList> findAllByMemberId(Long memberId) {

        // 해당 멤버가 속한 MemberTeam 목록을 가져옴
        Member member = memberRepository.findById(memberId).get();
        List<MemberTeam> memberTeams = memberTeamRepository.findMemberTeamsByMember(member);

        // MemberTeam에서 팀 정보만 추출하여 DTO로 변환
        return memberTeams.stream()
                .map(MemberTeam::getTeam)  // MemberTeam에서 Team 객체를 추출
                .map(TeamResponseDto.GetTeamList::fromTeam)  // Team을 GetTeamList DTO로 변환
                .collect(Collectors.toList());
    }


}
