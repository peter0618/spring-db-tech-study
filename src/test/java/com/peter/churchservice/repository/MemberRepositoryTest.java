package com.peter.churchservice.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.peter.churchservice.domain.member.Gender;
import com.peter.churchservice.domain.member.Member;
import com.peter.churchservice.repository.dto.MemberSearchParam;
import com.peter.churchservice.repository.dto.MemberUpdateParam;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootTest
class MemberRepositoryTest {

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
    void save() {
        //given
        Member member = Member.builder()
            .name("Peter")
            .gender(Gender.MALE)
            .position("집사")
            .birthDate(LocalDate.parse("1995-01-01"))
            .address("서울시 강남구 학동로")
            .phoneNumber("010-1111-2222")
            .build();

        //when
        Member memberSave = memberRepository.save(member);
        Member memberFound = memberRepository.findById(memberSave.getId()).get();

        //then
        assertThat(memberFound).isEqualTo(memberSave);
    }

    @Test
    void findByIdForExistingRow() {
        //given
        Member member = Member.builder()
            .name("Peter")
            .gender(Gender.MALE)
            .position("집사")
            .birthDate(LocalDate.parse("1995-01-01"))
            .address("서울시 강남구 학동로")
            .phoneNumber("010-1111-2222")
            .build();
        Member memberSave = memberRepository.save(member);
        Long memberSaveId = memberSave.getId();

        //when
        Member memberFind = memberRepository.findById(memberSaveId).get();

        //then
        assertThat(memberFind).isEqualTo(memberSave);
    }

    @Test
    void findByIdForNonExistingRow() {
        //given
        Long memberIdForNonExisting = 100000L; // NOTE: @BeforeEach 에서 members table truncate 하는 로직이 있어야 완전한 non-existence 보장됨.

        //when
        Optional<Member> memberOptional = memberRepository.findById(memberIdForNonExisting);

        //then
        assertThat(memberOptional).isEmpty();
    }

    @Test
    void findWithoutCond() {
        //given
        Member member1 = Member.builder()
            .name("Peter1")
            .gender(Gender.MALE)
            .position("집사")
            .birthDate(LocalDate.parse("1995-01-01"))
            .address("서울시 강남구 학동로")
            .phoneNumber("010-1111-2222")
            .build();

        Member member2 = Member.builder()
            .name("Peter2")
            .gender(Gender.MALE)
            .position("집사")
            .birthDate(LocalDate.parse("1995-01-01"))
            .address("서울시 강남구 학동로")
            .phoneNumber("010-1111-2222")
            .build();

        Member member3 = Member.builder()
            .name("Peter3")
            .gender(Gender.MALE)
            .position("집사")
            .birthDate(LocalDate.parse("1995-01-01"))
            .address("서울시 강남구 학동로")
            .phoneNumber("010-1111-2222")
            .build();

        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);

        MemberSearchParam param = new MemberSearchParam(null);

        //when
        List<Member> list = memberRepository.findAll(param);
        Set<String> setOfNames = list.stream().map(Member::getName).collect(Collectors.toSet());

        //then
        assertThat(list).hasSize(3);
        assertThat(setOfNames).isEqualTo(Set.of(member1.getName(), member2.getName(), member3.getName()));
    }

    @Test
    void findWithCond() {
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

        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
        memberRepository.save(member4);

        MemberSearchParam param = new MemberSearchParam("a");

        //when
        List<Member> list = memberRepository.findAll(param);
        Set<String> setOfNames = list.stream().map(Member::getName).collect(Collectors.toSet());

        //then
        assertThat(list).hasSize(2);
        assertThat(setOfNames).isEqualTo(Set.of("Grace","Paul"));
    }

    @Test
    void update() {
        //given
        Member member = Member.builder()
            .name("Peter")
            .gender(Gender.MALE)
            .position("집사")
            .birthDate(LocalDate.parse("1995-01-01"))
            .address("서울시 강남구 학동로")
            .phoneNumber("010-1111-2222")
            .build();
        Member memberSave = memberRepository.save(member);

        MemberUpdateParam updateParam = MemberUpdateParam.builder()
            .name("Grace")
            .gender(Gender.FEMALE)
            .position("간사")
            .birthDate(LocalDate.parse("1997-02-02"))
            .address("서울시 강남구")
            .phoneNumber("010-3333-4444")
            .build();

        //when
        memberRepository.update(memberSave.getId(), updateParam);
        Member memberUpdate = memberRepository.findById(memberSave.getId()).get();

        //then
        assertThat(memberUpdate.getName()).isEqualTo(updateParam.getName());
        assertThat(memberUpdate.getGender()).isEqualTo(updateParam.getGender());
        assertThat(memberUpdate.getPosition()).isEqualTo(updateParam.getPosition());
        assertThat(memberUpdate.getBirthDate()).isEqualTo(updateParam.getBirthDate());
        assertThat(memberUpdate.getAddress()).isEqualTo(updateParam.getAddress());
        assertThat(memberUpdate.getPhoneNumber()).isEqualTo(updateParam.getPhoneNumber());
    }

    @Test
    void delete() {
        //given
        Member member = Member.builder()
            .name("Peter")
            .gender(Gender.MALE)
            .position("집사")
            .birthDate(LocalDate.parse("1995-01-01"))
            .address("서울시 강남구 학동로")
            .phoneNumber("010-1111-2222")
            .build();
        Member memberSaved = memberRepository.save(member);
        Long memberId = memberSaved.getId();
        assertThat(memberId).isNotNull();

        //when
        memberRepository.delete(memberId);
        Optional<Member> memberOptional = memberRepository.findById(memberId);

        //then
        assertThat(memberOptional).isEmpty();
    }
}