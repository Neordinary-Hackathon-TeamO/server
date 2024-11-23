package org.example.secret_santa.team.dto;

import lombok.Data;
import org.example.secret_santa.common.enums.TeamType;

import java.time.LocalDateTime;

@Data
public class TeamRequestDto {

    public static class AddTeamDto {
        String name;

        TeamType type;
        LocalDateTime startDate;
        LocalDateTime endDate;


    }



}
