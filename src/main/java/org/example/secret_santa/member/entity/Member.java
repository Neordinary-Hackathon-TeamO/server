package org.example.secret_santa.member.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import org.example.secret_santa.team.mapping.MemberTeam;

import org.example.secret_santa.common.BaseEntity;
import org.example.secret_santa.member.dto.UpdateInfo;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@DynamicInsert // 자동으로 insert문에 null값을 배제하고 쿼리문을 날려줌.
@Table(name = "member")
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    @Column(nullable = false, unique = true)
    private String memId;
    @Column(nullable = false, unique = true)
    private String nickName;
    @Column(nullable = false)
    private String password;

    private String profileImage;

    @ColumnDefault("'ROLE_USER'")
    private String roleSet;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    List<MemberTeam> memberTeamList = new ArrayList<>();

    public void setMemId(String memId) {
        this.memId = memId;
    }
    public void setRole(String roleSet) {
        this.roleSet = roleSet;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void hashPassword(PasswordEncoder passwordEncoder) {
        setPassword(passwordEncoder.encode(getPassword()));
    }
    @Builder
    public Member(Long id, String memId, String nickName, String password, String profileImage) {
        this.id = id;
        this.memId = memId;
        this.password = password;
        this.nickName = nickName;
        this.profileImage = profileImage;
    }

    public void updateInfo(UpdateInfo updateInfo) {
        this.memId = updateInfo.getMemId();
        this.nickName = updateInfo.getNickName();
        this.profileImage = updateInfo.getProfileImage();
    }
    // Add memberTeam to the list of memberTeams
    public void addMemberTeam(MemberTeam memberTeam) {
        this.memberTeamList.add(memberTeam);
        if (memberTeam.getMember() != this) {
            memberTeam.setMember(this);
        }
    }
}