package org.example.secret_santa.team.controller;

import lombok.RequiredArgsConstructor;
import org.example.secret_santa.team.service.TeamMatchingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teams")
@RequiredArgsConstructor
public class TeamMatchingController {

    private final TeamMatchingService teamMatchingService;

    // 팀 멤버 매칭 실행 API
    @PostMapping("/{teamId}/match")
    public ResponseEntity<String> matchMembers(@PathVariable Long teamId) {
        try {
            // 팀 멤버 매칭 실행
            teamMatchingService.matchMembers(teamId);
            return ResponseEntity.ok("Members matched successfully.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body("Error: " + e.getMessage());
        }
    }
}
