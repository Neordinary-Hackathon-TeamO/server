package org.example.secret_santa.matching.controller;

import lombok.RequiredArgsConstructor;
import org.example.secret_santa.common.ApiResponse;
import org.example.secret_santa.matching.dto.ViewMatch;
import org.example.secret_santa.matching.service.MatchService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
@RequestMapping("/matching")
public class MatchController {

    private final MatchService matchService;

    @GetMapping("/{id}")
    public ResponseEntity<?> viewAllMatch(
            @PathVariable(name = "id") Long teamId,
            Pageable pageable) {
        Page<ViewMatch> viewAllMatches = matchService.viewAllMatches(pageable, teamId);
        return ResponseEntity.ok(ApiResponse.ok(viewAllMatches));
    }

    @PostMapping("/{id}")
    public ResponseEntity<?> guessMyManitto(@PathVariable(name = "id") Long teamId)
    {
        matchService.guessManitto(memberId)
    }
}
