package org.example.secret_santa.mission.converter;

import org.example.secret_santa.mission.dto.MissionResponseDto;
import org.example.secret_santa.mission.entity.Mission;

public class MissionConverter {

    public static MissionResponseDto.OwnMission toOwnMission(
        Mission mission, String myNickname, String receiverNickname) {
        return MissionResponseDto.OwnMission.builder()
            .contents(mission.getContents())
            .myNickname(myNickname)
            .receiverNickname(receiverNickname)
            .build();
    }

}
