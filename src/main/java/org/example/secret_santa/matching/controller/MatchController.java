package org.example.secret_santa.matching.controller;

import lombok.RequiredArgsConstructor;
import org.example.secret_santa.common.ApiResponse;
import org.example.secret_santa.matching.dto.ViewMatch;
import org.example.secret_santa.matching.service.MatchingService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/matching")
public class MatchController {

    private final MatchingService matchService;

    @GetMapping("/{teamId}")
    public ResponseEntity<?> viewAllMatch(
            @PathVariable(name = "teamId") Long teamId,
            Pageable pageable) {
        Page<ViewMatch> viewAllMatches = matchService.viewAllMatches(pageable, teamId);
        return ResponseEntity.ok(ApiResponse.ok(viewAllMatches));
    }

    @PostMapping("/{teamId}")
    public ResponseEntity<?> guessMyManitto(
            @PathVariable(name = "teamId") Long teamId,
            Principal principal,
            @RequestParam Long memberId)
    {
        Long ok = matchService.guessManitto(teamId, memberId, principal.getName());
        return ResponseEntity.ok(ApiResponse.ok(ok));
    }
}
