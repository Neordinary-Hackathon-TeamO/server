package org.example.secret_santa.member.dto;

import lombok.Data;
import org.example.secret_santa.member.entity.Member;

@Data
public class RegisterInfo {
    private String memId;
    private String nickName;
    private String password;
    private String profileImage;

    public static Member toEntity(RegisterInfo registerInfo) {
        return Member.builder()
                .memId(registerInfo.memId)
                .nickName(registerInfo.nickName)
                .password(registerInfo.password)
                .profileImage(registerInfo.profileImage)
                .build();
    }
}
