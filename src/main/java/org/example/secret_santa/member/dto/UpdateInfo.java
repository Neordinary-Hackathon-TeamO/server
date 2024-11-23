package org.example.secret_santa.member.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateInfo {
    private String memId;
    private String nickName;
    private String profileImage;


}
