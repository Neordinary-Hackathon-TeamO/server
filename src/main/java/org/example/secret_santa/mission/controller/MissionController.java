package org.example.secret_santa.mission.controller;

import jakarta.validation.constraints.Null;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.example.secret_santa.common.ApiResponse;
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
@RequestMapping("/api")
public class MissionController {


    private final MissionService missionService;
    private final MemberService memberService;
    private final MatchingService matchingService;


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






    @PutMapping("/mission/{memberId}")
    public ApiResponse<?> executeMission(
        Principal principal,
        @RequestPart(value = "image") MultipartFile image,
        @RequestParam("message") String message) {
        ViewMyInfo viewMyInfo = memberService.viewMyInfo((principal.getName()));
        Long memberId = viewMyInfo.getId();
        missionService.putMission(memberId, image, message);
        return ApiResponse.ok();
    }







}
