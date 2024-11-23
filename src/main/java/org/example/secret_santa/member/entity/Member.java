package org.example.secret_santa.member.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.secret_santa.team.mapping.MemberTeam;
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
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    @Column(nullable = false, unique = true)
    private String name;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false, unique = true)
    private String profileImage;
    @ColumnDefault("'ROLE_USER'")
    private String roleSet;
    @OneToMany(mappedBy = "member")
    List<MemberTeam> memberTeamList = new ArrayList<>();
    public void setName(String name) {
        this.name = name;
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
    public Member(Long id, String name, String password, String profileImage) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.profileImage = profileImage;
    }
}
