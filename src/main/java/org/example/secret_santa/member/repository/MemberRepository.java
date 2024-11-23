package org.example.secret_santa.member.repository;


import org.example.secret_santa.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByNickName(String nickName);

    Optional<Member> findByMemId(String memId);

    boolean existsByNickName(String nickName);
}
