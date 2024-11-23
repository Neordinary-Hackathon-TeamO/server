package org.example.secret_santa.mission.dto;

import lombok.Builder;
import lombok.Data;
import org.example.secret_santa.mission.entity.Mission;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class ViewMissionComplete {
    private String nickName;
    private String missionImage;
    private String comment;

    public static ViewMissionComplete of(Mission mission, String bucketName) {
        return ViewMissionComplete.builder()
                .nickName(mission.getMatching().getReceiverMember().getNickName())
                .comment(mission.getMessage())
                .missionImage("https://storage.googleapis.com/" + bucketName + "/" + mission.getMissionImg())
                .build();
    }
}
