package org.example.secret_santa.team.dto;

import lombok.*;
import org.example.secret_santa.common.enums.TeamType;
import org.example.secret_santa.team.entity.Team;

import java.time.LocalDateTime;

@Data
public class TeamRequestDto {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AddTeamDto {
        String name;
        String type;
        Integer headCount;
        LocalDateTime startDate;
        LocalDateTime endDate;


        public static Team convertDtoToTeam(AddTeamDto dto ) {

            return Team.builder()
                    .teamType(convertTeamType(dto.type))
                    .headCount(dto.headCount)
                    .startDate(dto.startDate)
                    .endDate(dto.endDate)
                    .name(dto.name)
                    .build();
        }


        public static TeamType convertTeamType(String str) {
            if (str == null || str.isBlank()) {
                throw new IllegalArgumentException("Input string cannot be null or blank");
            }

            switch (str) {
                case "RELATIONSHIP":
                    return TeamType.RELATIONSHIP;
                case "HEALING":
                    return TeamType.HEALING;
                case "GAME":
                    return TeamType.GAME;
                case "JOY":
                    return TeamType.JOY;
                case "SHARING":
                    return TeamType.SHARING;
                case "MEMORY":
                    return TeamType.MEMORY;
                case "HUMOR":
                    return TeamType.HUMOR;
                default:
                    throw new IllegalArgumentException("Invalid TeamType: " + str);
            }


        }

    }

}
