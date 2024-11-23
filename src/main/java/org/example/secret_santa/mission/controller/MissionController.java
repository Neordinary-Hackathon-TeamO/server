package org.example.secret_santa.mission.controller;

import jakarta.validation.constraints.Null;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

import lombok.RequiredArgsConstructor;
import org.example.secret_santa.common.ApiResponse;
import org.example.secret_santa.matching.service.MatchingService;
import org.example.secret_santa.member.dto.ViewMyInfo;
import org.example.secret_santa.member.service.MemberService;
import org.example.secret_santa.mission.converter.MissionConverter;
import org.example.secret_santa.mission.dto.MissionComplete;
import org.example.secret_santa.mission.dto.MissionRequestDto.ExecuteMission;
import org.example.secret_santa.mission.dto.MissionResponseDto;
import org.example.secret_santa.mission.dto.ViewMissionComplete;
import org.example.secret_santa.mission.entity.Mission;
import org.example.secret_santa.mission.service.MissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@Validated
public class MissionController {

    private final MissionService missionService;
    private final MemberService memberService;
    private final MatchingService matchingService;

    @GetMapping("/mission/list/{teamId}")
    public ResponseEntity<?> viewCompleteMissions(
            @PathVariable(name = "teamId") Long teamId) {
        List<ViewMissionComplete> viewAllMission = missionService.viewAllMission(teamId);
        return ResponseEntity.ok(ApiResponse.ok(viewAllMission));
    }

    @GetMapping("/mission/{memberId}")
    public ApiResponse<MissionResponseDto.OwnMission> ownMission(
        Principal principal,
        @PathVariable("groupId") Long groupId) {
        ViewMyInfo viewMyInfo = memberService.viewMyInfo((principal.getName()));
        Long memberId = viewMyInfo.getId();
        Mission mission = missionService.getOwnMission(memberId, groupId);
        String myNickname = memberService.getNickname(memberId);
        String receiverNickname = matchingService.getReceiverNickname(memberId, groupId);
        return ApiResponse.ok(MissionConverter.toOwnMission(mission, myNickname, receiverNickname));
    }

    // fomt-data (미션 저장하기)
    @PutMapping("/mission")
    @Transactional
    public ApiResponse<?> executeMission(
            Principal principal,
            MissionComplete missionComplete) throws IOException {
        missionService.putMission(principal.getName(), missionComplete);
        return ApiResponse.ok();
    }


}
