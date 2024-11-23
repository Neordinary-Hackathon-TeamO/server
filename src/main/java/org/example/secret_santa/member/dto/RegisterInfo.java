package org.example.secret_santa.member.dto;

import lombok.Data;
import org.example.secret_santa.member.entity.Member;
import org.springframework.web.multipart.MultipartFile;

@Data
public class RegisterInfo {
    private String memId;
    private String nickName;
    private String password;
    private MultipartFile profileImage;

    public static Member toEntity(RegisterInfo registerInfo, String uuid) {
        return Member.builder()
                .memId(registerInfo.memId)
                .nickName(registerInfo.nickName)
                .password(registerInfo.password)
                .profileImage(uuid)
                .build();
    }
}
