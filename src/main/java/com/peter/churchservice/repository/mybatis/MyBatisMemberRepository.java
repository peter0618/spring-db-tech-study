package com.peter.churchservice.repository.mybatis;


import com.peter.churchservice.domain.member.Member;
import com.peter.churchservice.repository.MemberRepository;
import com.peter.churchservice.repository.dto.MemberSearchParam;
import com.peter.churchservice.repository.dto.MemberUpdateParam;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Primary
@Repository
@RequiredArgsConstructor
public class MyBatisMemberRepository implements MemberRepository {

    private final MemberMapper memberMapper;

    @Override
    public Member save(Member member) {
        memberMapper.save(member);
        return member;
    }

    @Override
    public void update(Long memberId, MemberUpdateParam updateParam) {
        memberMapper.update(memberId, updateParam);
    }

    @Override
    public Optional<Member> findById(Long id) {
        return memberMapper.findById(id);
    }

    @Override
    public List<Member> findAll(MemberSearchParam cond) {
        return memberMapper.findAll(cond);
    }

    @Override
    public void delete(Long memberId) {
        memberMapper.delete(memberId);
    }
}
