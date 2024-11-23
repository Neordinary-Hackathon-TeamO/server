package org.example.secret_santa.team.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.secret_santa.common.BaseEntity;
import org.example.secret_santa.common.enums.TeamType;
import org.example.secret_santa.team.mapping.MemberTeam;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@DynamicInsert // 자동으로 insert문에 null값을 배제하고 쿼리문을 날려줌.
@Table(name = "team")
public class Team extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamId;

    @ColumnDefault("비밀 산타")
    String name;
    @Enumerated(EnumType.STRING)
    TeamType teamType;
    @Column(nullable = false)
    LocalDateTime startDate;
    @Column(nullable = false)
    LocalDateTime endDate;

    @OneToMany(mappedBy = "team")
    List<MemberTeam> memberTeamList = new ArrayList<>();

}









