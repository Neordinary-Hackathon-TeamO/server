package org.example.secret_santa.mission.controller;

import jakarta.validation.constraints.Null;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.example.secret_santa.common.ApiResponse;
import org.example.secret_santa.common.converter.UserAuthorizationConverter;
import org.example.secret_santa.matching.service.MatchingService;
import org.example.secret_santa.member.dto.ViewMyInfo;
import org.example.secret_santa.member.service.MemberService;
import org.example.secret_santa.mission.converter.MissionConverter;
import org.example.secret_santa.mission.dto.MissionRequestDto.ExecuteMission;
import org.example.secret_santa.mission.dto.MissionResponseDto;
import org.example.secret_santa.mission.entity.Mission;
import org.example.secret_santa.mission.service.MissionService;
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
@RequestMapping("/mission")
public class MissionController {


    private final MissionService missionService;
    private final MemberService memberService;
    private final MatchingService matchingService;


    @GetMapping("/{groupId}/{memberId}")
    public ApiResponse<MissionResponseDto.OwnMission> ownMission(
        @PathVariable("groupId") Long groupId) {
        Mission mission = missionService.getOwnMission(groupId);
        String myNickname = memberService.getNickname();
        String receiverNickname = matchingService.getReceiverNickname(groupId);
        return ApiResponse.ok(MissionConverter.toOwnMission(mission, myNickname, receiverNickname));
    }






    @PutMapping("/{memberId}")
    public ApiResponse<?> executeMission(
        @RequestPart(value = "image") MultipartFile image,
        @RequestParam("message") String message) {
        missionService.putMission( image, message);
        return ApiResponse.ok();
    }







}
