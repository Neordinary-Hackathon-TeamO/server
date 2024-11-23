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
import java.util.UUID;

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
    @Column(nullable = false)
    @ColumnDefault("0")
    Integer currentHeadCount = 0;
    @Column(nullable = true)
    private LocalDateTime startDate = LocalDateTime.now(); // 기본값 설정
    @Column(nullable = true)
    private LocalDateTime endDate = LocalDateTime.now(); // 기본값 설정

    private String inviteCode;

    @OneToMany(mappedBy = "team",  cascade = CascadeType.ALL)
    List<MemberTeam> memberTeamList = new ArrayList<>();
    @PrePersist
    public void prePersist() {
        if (this.currentHeadCount == null) this.currentHeadCount = 0;
    }

    public void generateInviteCode() {
        this.inviteCode = UUID.randomUUID().toString(); // 초대 코드를 UUID로 생성
    }
    public void addMemberTeam(MemberTeam memberTeam) {
        this.memberTeamList.add(memberTeam);
        if (memberTeam.getTeam() != this) {
            memberTeam.setTeam(this);
        }
    }
    public void plusCurrentHeadCount() {
        // Todo 인원수 넘어가지 않게 예외처리
        currentHeadCount += 1;
    }
}









