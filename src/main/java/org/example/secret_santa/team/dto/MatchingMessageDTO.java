package org.example.secret_santa.team.dto;

public class MatchingMessageDTO {
    private String giverNickname;
    private String receiverNickname;

    // 생성자
    public MatchingMessageDTO(String giverNickname, String receiverNickname) {
        this.giverNickname = giverNickname;
        this.receiverNickname = receiverNickname;
    }

    // getter, setter
    public String getGiverNickname() {
        return giverNickname;
    }

    public void setGiverNickname(String giverNickname) {
        this.giverNickname = giverNickname;
    }

    public String getReceiverNickname() {
        return receiverNickname;
    }

    public void setReceiverNickname(String receiverNickname) {
        this.receiverNickname = receiverNickname;
    }
}

