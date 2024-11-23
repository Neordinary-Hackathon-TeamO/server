package org.example.secret_santa.member.dto.post;

import lombok.Data;
import org.example.secret_santa.member.entity.Member;

@Data
public class RegisterInfo {
    private String name;
    private String password;
    private String profileImage;

    public static Member toEntity(RegisterInfo registerInfo) {
        return Member.builder()
                .name(registerInfo.name)
                .password(registerInfo.password)
                .profileImage(registerInfo.profileImage)
                .build();
    }
}
