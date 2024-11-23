package org.example.secret_santa.mission.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
public class MissionComplete {
    private MultipartFile missionImage;
    private String comment;
}
