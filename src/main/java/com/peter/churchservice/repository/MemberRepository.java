package com.peter.churchservice.repository;

import com.peter.churchservice.domain.member.Member;
import com.peter.churchservice.repository.dto.MemberSearchParam;
import com.peter.churchservice.repository.dto.MemberUpdateParam;
import java.util.List;
import java.util.Optional;

public interface MemberRepository {
    Member save(Member member);

    void update(Long memberId, MemberUpdateParam updateParam);

    Optional<Member> findById(Long id);

    List<Member> findAll(MemberSearchParam cond);

    void delete(Long memberId);
}
