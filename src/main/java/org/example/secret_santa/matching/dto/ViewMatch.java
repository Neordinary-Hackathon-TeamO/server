package org.example.secret_santa.matching.dto;

import lombok.Builder;
import lombok.Data;
import org.example.secret_santa.matching.entity.Matching;

@Data
@Builder
public class ViewMatch {
    private String from;
    private String to;
    boolean isSuccess;

    public static ViewMatch of(Matching matching) {
        return ViewMatch.builder()
                .from(matching.getGiverMember().getNickName())
                .to(matching.getReceiverMember().getNickName())
                .isSuccess(matching.isSuccess())
                .build();
    }
}
