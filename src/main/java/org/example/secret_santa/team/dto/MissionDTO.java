package org.example.secret_santa.team.dto;

public class MissionDTO {
    private Long missionId;
    private String missionTitle;
    private String giverNickname;
    private String receiverNickname;


    // 생성자
    public MissionDTO(Long missionId, String missionTitle, String giverNickname, String receiverNickname ) {
        this.missionId = missionId;
        this.missionTitle = missionTitle;
        this.giverNickname = giverNickname;
        this.receiverNickname = receiverNickname;
    }

    // getter, setter
    public String getMissionTitle() {
        return missionTitle;
    }

    public void setMissionTitle(String missionTitle) {
        this.missionTitle = missionTitle;
    }

    public String getReceiverNickname() {
        return receiverNickname;
    }

    public void setGiverNickname(String giverNickname) {
        this.giverNickname = giverNickname;
    }
    public String getGiverNickname() {
        return giverNickname;
    }

    public void setReceiverNickname(String receiverNickname) {
        this.receiverNickname = receiverNickname;
    }

}
