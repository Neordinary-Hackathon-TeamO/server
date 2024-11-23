package org.example.secret_santa.member.controller;

import lombok.RequiredArgsConstructor;
import org.example.secret_santa.member.dto.post.RegisterInfo;
import org.example.secret_santa.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<?> register(@RequestBody RegisterInfo registerInfo) {
        Long memberId = memberService.registerMember(registerInfo);
        return ResponseEntity.ok(memberId);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.ok().build();
    }

}
