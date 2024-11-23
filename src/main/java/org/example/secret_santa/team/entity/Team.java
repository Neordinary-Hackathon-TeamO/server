package org.example.secret_santa.team.entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.secret_santa.common.BaseEntity;
import org.example.secret_santa.common.enums.TeamType;
import org.example.secret_santa.team.mapping.MemberTeam;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
//@DynamicInsert // 자동으로 insert문에 null값을 배제하고 쿼리문을 날려줌.
@Table(name = "teams")
public class Team extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamId;

    String name;
    @Enumerated(EnumType.STRING)
    TeamType teamType;
    @Column(nullable = false)
    Integer headCount;
    @Column(nullable = true)
    private LocalDateTime startDate = LocalDateTime.now(); // 기본값 설정

    @Column(nullable = true)
    private LocalDateTime endDate = LocalDateTime.now(); // 기본값 설정


    @OneToMany(mappedBy = "team")
    List<MemberTeam> memberTeamList = new ArrayList<>();

}









