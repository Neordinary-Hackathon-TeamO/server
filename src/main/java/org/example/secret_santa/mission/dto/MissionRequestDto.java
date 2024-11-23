package org.example.secret_santa.mission.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MissionRequestDto {


    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExecuteMission {

        private String message;

        private String image;

    }
}
