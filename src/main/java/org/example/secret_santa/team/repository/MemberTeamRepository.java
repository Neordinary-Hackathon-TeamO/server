package org.example.secret_santa.team.repository;

import org.example.secret_santa.member.entity.Member;
import org.example.secret_santa.team.mapping.MemberTeam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface MemberTeamRepository extends JpaRepository<MemberTeam, Long> {
    List<MemberTeam> findMemberTeamsByMember(Member member);
    @Query("SELECT m.nickName FROM Member m JOIN MemberTeam mt ON m.id = mt.member.id WHERE mt.team.id = :teamId")
    List<String> findMembersById(@Param("teamId") Long teamId);


    @Query("SELECT mt.isOpened FROM MemberTeam mt WHERE mt.team.id = :teamId AND mt.member.id = :memberId")
    boolean findIsOpenedByTeamIdAndMemberId(@Param("teamId") Long teamId, @Param("memberId") Long memberId);

    @Modifying
    @Query("UPDATE MemberTeam mt SET mt.isOpened = true WHERE mt.team.id = :teamId AND mt.member.id = :memberId")
    void updateIsOpenedStatus(@Param("teamId") Long teamId, @Param("memberId") Long memberId);

}
