package org.example.secret_santa.team.service;

import lombok.RequiredArgsConstructor;
import org.example.secret_santa.team.dto.TeamRequestDto;
import org.example.secret_santa.team.entity.Team;
import org.example.secret_santa.team.repository.TeamRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeamService {
    final TeamRepository teamRepository;

    public Team createTeam(TeamRequestDto.AddTeamDto dto) {
        Team savedTeam = teamRepository.save(TeamRequestDto.AddTeamDto.convertDtoToTeam(dto));
        return savedTeam;

    }


}
