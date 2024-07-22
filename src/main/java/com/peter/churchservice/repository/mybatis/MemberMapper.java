package com.peter.churchservice.repository.mybatis;

import com.peter.churchservice.domain.member.Member;
import com.peter.churchservice.repository.dto.MemberSearchParam;
import com.peter.churchservice.repository.dto.MemberUpdateParam;
import java.util.List;
import java.util.Optional;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberMapper {
    void save(Member member);

    void update(@Param("id") Long memberId, @Param("updateParam") MemberUpdateParam updateParam);

    Optional<Member> findById(Long id);

    List<Member> findAll(MemberSearchParam cond);

    void delete(Long memberId);
}
