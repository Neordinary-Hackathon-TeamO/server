package org.example.secret_santa.member.dto;

import lombok.Builder;
import lombok.Data;
import org.example.secret_santa.member.entity.Member;

@Data
@Builder
public class ViewMyInfo {
    private Long id;
    private String memId;
    private String nickName;
    private String profileImage;

    public static ViewMyInfo of(Member member, String bucketName) {
        return ViewMyInfo.builder()
                .id(member.getId())
                .memId(member.getMemId())
                .nickName(member.getNickName())
                .profileImage("https://storage.googleapis.com/" + bucketName + "/" + member.getProfileImage())
                .build();
    }
}
