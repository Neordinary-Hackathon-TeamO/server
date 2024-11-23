package org.example.secret_santa.team.dto;

import java.util.List;

public class GoRoomResponseDTO {
    private String status;
    private Integer headCount;
    private Integer currentHeadCount;
    private List<String> memberNicknames;
    private MatchingMessageDTO matchingMessage;  // String이 아닌 MatchingMessageDTO로 수정
    private MissionDTO missionDTO;

    // 생성자
    public GoRoomResponseDTO(String status, Integer headCount, Integer currentHeadCount, List<String> memberNicknames, MatchingMessageDTO matchingMessage, MissionDTO missionDTO) {
        this.status = status;
        this.headCount = headCount;
        this.currentHeadCount = currentHeadCount;
        this.memberNicknames = memberNicknames;
        this.matchingMessage = matchingMessage;
        this.missionDTO = missionDTO;
    }

    // getter, setter
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getMemberNicknames() {
        return memberNicknames;
    }

    public void setMemberNicknames(List<String> memberNicknames) {
        this.memberNicknames = memberNicknames;
    }

    public MatchingMessageDTO getMatchingMessage() {
        return matchingMessage;
    }

    public void setMatchingMessage(MatchingMessageDTO matchingMessage) {
        this.matchingMessage = matchingMessage;
    }

    public MissionDTO getMissionDTO() {
        return missionDTO;
    }

    public void setMissionDTO(MissionDTO missionDTO) {
        this.missionDTO = missionDTO;
    }
}

