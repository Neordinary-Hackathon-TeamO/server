package org.example.secret_santa.mission.controller;

import lombok.RequiredArgsConstructor;
import org.example.secret_santa.common.ApiResponse;
import org.example.secret_santa.mission.converter.MissionConverter;
import org.example.secret_santa.mission.dto.MissionResponseDto;
import org.example.secret_santa.mission.entity.Mission;
import org.example.secret_santa.mission.service.MissionService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api")
public class MissionController {

    private final MissionService missionService;

    @GetMapping("/mission/{memberId}")
    public ApiResponse<MissionResponseDto.OwnMission> ownMission(
        @PathVariable("memberId") Long memberId) {
        Mission mission = missionService.getOwnMission(memberId);
        return ApiResponse.ok(MissionConverter.toOwnMission(mission));
    }




}
