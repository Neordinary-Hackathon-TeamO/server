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
                .from(matching.getGiver_member_id().getNickName())
                .to(matching.getReceiver_member_id().getNickName())
                .isSuccess(matching.isSuccess())
                .build();
    }
}
