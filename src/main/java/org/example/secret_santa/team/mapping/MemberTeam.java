package org.example.secret_santa.team.mapping;

import jakarta.persistence.*;
import lombok.*;
import org.example.secret_santa.common.BaseEntity;
import org.example.secret_santa.member.entity.Member;
import org.example.secret_santa.team.entity.Team;
import org.hibernate.annotations.DynamicInsert;

@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert // 자동으로 insert문에 null값을 배제하고 쿼리문을 날려줌.
@Table(name = "member-team")
public class MemberTeam extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long memberTeamId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;
}
