package org.example.secret_santa.team.dto;

import lombok.*;
import org.example.secret_santa.team.entity.Team;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class TeamResponseDto {
    // 팀 목록을 보여주는 DTO
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetTeamList {
        private Long teamId;

        private String name;
        private LocalDateTime startDate;
        private LocalDateTime endDate;

        // Team 객체를 DTO로 변환
        public static GetTeamList fromTeam(Team team) {
            return GetTeamList.builder()
                    .teamId(team.getTeamId())
                    .name(team.getName())
                    .startDate(team.getStartDate())
                    .endDate(team.getEndDate())
                    .build();
        }
    }
}
