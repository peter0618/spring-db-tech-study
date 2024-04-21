package com.peter.churchservice.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.peter.churchservice.domain.member.Gender;
import com.peter.churchservice.domain.member.Member;
import com.peter.churchservice.repository.dto.MemberSearchParam;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
class MemberBulkRepositoryTest {

    @Autowired
    MemberBulkRepository memberBulkRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    JdbcTemplate template;

    @AfterEach
    void afterEach() {
        String sql = "TRUNCATE TABLE members";
        template.update(sql);
    }

    @Test
    void bulkInsertTest() {
        //given
        Member member1 = Member.builder()
            .name("Peter")
            .gender(Gender.MALE)
            .position("집사")
            .birthDate(LocalDate.parse("1995-01-01"))
            .address("서울시 강남구 학동로")
            .phoneNumber("010-1111-2222")
            .build();

        Member member2 = Member.builder()
            .name("John")
            .gender(Gender.MALE)
            .position("집사")
            .birthDate(LocalDate.parse("1995-01-01"))
            .address("서울시 강남구 학동로")
            .phoneNumber("010-1111-2222")
            .build();

        Member member3 = Member.builder()
            .name("Paul")
            .gender(Gender.MALE)
            .position("집사")
            .birthDate(LocalDate.parse("1995-01-01"))
            .address("서울시 강남구 학동로")
            .phoneNumber("010-1111-2222")
            .build();

        Member member4 = Member.builder()
            .name("Grace")
            .gender(Gender.MALE)
            .position("집사")
            .birthDate(LocalDate.parse("1995-01-01"))
            .address("서울시 강남구 학동로")
            .phoneNumber("010-1111-2222")
            .build();

        List<Member> members = new ArrayList<>();
        members.add(member1);
        members.add(member2);
        members.add(member3);
        members.add(member4);

        //when
        memberBulkRepository.bulkInsert(members);

        //then
        List<Member> membersFound = memberRepository.findAll(new MemberSearchParam(null));
        assertThat(membersFound).hasSize(4);
    }
}