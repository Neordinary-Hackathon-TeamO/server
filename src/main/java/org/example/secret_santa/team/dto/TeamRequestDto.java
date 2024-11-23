package org.example.secret_santa.team.dto;

import lombok.Data;
import org.example.secret_santa.common.enums.TeamType;
import org.example.secret_santa.team.entity.Team;

import java.time.LocalDateTime;

@Data
public class TeamRequestDto {

    public static class AddTeamDto {
        String name;

        String type;
        Integer headCount;
        LocalDateTime startDate;
        LocalDateTime endDate;

        public static Team convertDtoToTeam(AddTeamDto dto ) {
            return Team.builder()
                    .teamType(TeamType.toType(dto.type))
                    .headCount(dto.headCount)
                    .startDate(dto.startDate)
                    .endDate(dto.endDate)
                    .name(dto.name)
                    .build();
        }


    }


}
