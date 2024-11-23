package org.example.secret_santa.member.controller;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.parser.Authorization;
import org.example.secret_santa.common.ApiResponse;
import org.example.secret_santa.member.dto.RegisterInfo;
import org.example.secret_santa.member.dto.UpdateInfo;
import org.example.secret_santa.member.dto.ViewMyInfo;
import org.example.secret_santa.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping
    ResponseEntity<?> viewMyInfo(Principal principal) {
        ViewMyInfo viewMyInfo = memberService.viewMyInfo(principal.getName());
        return ResponseEntity.ok(ApiResponse.ok(viewMyInfo));
    }
    @PostMapping
    public ResponseEntity<?> register(RegisterInfo registerInfo) throws IOException {
        Long memberId = memberService.registerMember(registerInfo);
        return ResponseEntity.ok(ApiResponse.created(memberId));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody UpdateInfo updateInfo) {
        Long memberId = memberService.updateMember(id, updateInfo);
        return ResponseEntity.ok(ApiResponse.updated(memberId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.ok(ApiResponse.delete(id));
    }

}
