package com.peter.churchservice.repository.jdbctemplate;

import com.peter.churchservice.domain.member.Gender;
import com.peter.churchservice.domain.member.Member;
import com.peter.churchservice.repository.MemberRepository;
import com.peter.churchservice.repository.dto.MemberSearchParam;
import com.peter.churchservice.repository.dto.MemberUpdateParam;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
public class JdbcTemplateMemberRepository implements MemberRepository {

    private final JdbcTemplate template;

    public JdbcTemplateMemberRepository(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public Member save(Member member) {
        String sql = "INSERT INTO members (name, gender, position, birth_date, address, phone_number)"
                               + " VALUES (?,?,?,?,?,?)";
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(connection -> {
            // 자동 증가 키
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, member.getName());
            ps.setString(2, member.getGender().name());
            ps.setString(3, member.getPosition());
            ps.setDate(4, Date.valueOf(member.getBirthDate()));
            ps.setString(5, member.getAddress());
            ps.setString(6, member.getPhoneNumber());
            return ps;
        }, keyHolder);

        long key = keyHolder.getKey().longValue();
        member.setId(key);
        return member;
    }

    @Override
    public void update(Long memberId, MemberUpdateParam updateParam) {
        String sql = "UPDATE members SET name = ?"
                                    + ", gender = ?"
                                    + ", position = ?"
                                    + ", birth_date = ?"
                                    + ", address = ?"
                                    + ", phone_number = ?"
                    + " WHERE id = ?";
        template.update(sql, updateParam.getName(), updateParam.getGender().name(), updateParam.getPosition(),
            updateParam.getBirthDate(), updateParam.getAddress(), updateParam.getPhoneNumber(), memberId);
    }

    @Override
    public Optional<Member> findById(Long id) {
        String sql = "SELECT id, name, gender, position, birth_date, address, phone_number FROM members WHERE id=?";
        try {
            Member member = template.queryForObject(sql, memberRowMapper(), id);
            return Optional.ofNullable(member);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private RowMapper<Member> memberRowMapper() {
        return ((rs, rowNum) -> Member.builder()
            .id(rs.getLong("id"))
            .name(rs.getString("name"))
            .gender(Gender.valueOf(rs.getString("gender")))
            .position(rs.getString("position"))
            .birthDate(rs.getDate("birth_date").toLocalDate())
            .address(rs.getString("address"))
            .phoneNumber(rs.getString("phone_number"))
            .build());
    }

    @Override
    public List<Member> findAll(MemberSearchParam cond) {
        String name = cond.getName();

        String sql = "SELECT id, name, gender, position, birth_date, address, phone_number FROM members";
        List<Object> param = new ArrayList<>();

        if (StringUtils.hasText(name)) {
            sql += " WHERE name Like CONCAT('%',?,'%')";
            param.add(name);
        }

        return template.query(sql, memberRowMapper(), param.toArray());
    }

    @Override
    public void delete(Long memberId) {
        String sql = "DELETE FROM members WHERE id=?";
        template.update(sql, memberId);
    }
}
