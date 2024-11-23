package org.example.secret_santa.team.controller;

import lombok.RequiredArgsConstructor;
import org.example.secret_santa.common.ApiResponse;
import org.example.secret_santa.team.dto.TeamRequestDto;
import org.example.secret_santa.team.service.TeamService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api")
public class TeamController {

    final TeamService teamService;

    @PostMapping("/team")
    public ApiResponse<?> registerTeam(@RequestBody TeamRequestDto.AddTeamDto teamDto) {

        // tODO 예외처리
        return ApiResponse.created(teamService.createTeam(teamDto));

    }



}
