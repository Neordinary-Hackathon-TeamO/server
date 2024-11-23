package org.example.secret_santa.team.controller;

import lombok.RequiredArgsConstructor;
import org.example.secret_santa.common.ApiResponse;
import org.example.secret_santa.team.dto.TeamRequestDto;
import org.example.secret_santa.team.dto.TeamResponseDto;
import org.example.secret_santa.team.entity.Team;
import org.example.secret_santa.team.service.TeamService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/team")
public class TeamController {

    final TeamService teamService;

    @GetMapping("/rooms")
    public ApiResponse<List<TeamResponseDto.GetTeamList>> getTeamList(@RequestParam(value = "memberId") Long memberId) {
        List<TeamResponseDto.GetTeamList> teamList = teamService.findAllByMemberId(memberId);
        return ApiResponse.ok(teamList); // API 응답 반환
    }
    /**
     * 채팅방으로 이동
     * case 1 : 아직 모집되지 않음 -> 대기방으로 이동
     * case 2 : 모
     *
     * */
    @GetMapping("/room")

    @PostMapping("/")
    public ApiResponse<?> registerTeam(@RequestBody TeamRequestDto.AddTeamDto teamDto) {

        // tODO 예외처리
        return ApiResponse.created(teamService.createTeam(teamDto));

    }

    // 초대 코드로 팀에 입장
    @PostMapping("/join")
    public ApiResponse<?> joinTeam(@RequestParam String inviteCode, @RequestParam Long memberId) {

        // 팀에 멤버를 등록하는 로직
        teamService.joinTeam(inviteCode, memberId);

        return ApiResponse.ok();
    }




}
