package com.peter.churchservice.repository.jdbctemplate;

import com.peter.churchservice.domain.member.Gender;
import com.peter.churchservice.domain.member.Member;
import com.peter.churchservice.repository.MemberRepository;
import com.peter.churchservice.repository.dto.MemberSearchParam;
import com.peter.churchservice.repository.dto.MemberUpdateParam;
import com.peter.churchservice.repository.jdbctemplate.common.BeanPropertySqlParameterSourceForEnum;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

/**
 * NamedParameterJdbcTemplate 적용
 */
@Primary
@Repository
public class JdbcTemplateMemberRepositoryV2 implements MemberRepository {

    private final NamedParameterJdbcTemplate template;

    public JdbcTemplateMemberRepositoryV2(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Member save(Member member) {
        String sql = "INSERT INTO members (name, gender, position, birth_date, address, phone_number)"
            + " VALUES (:name,:gender,:position,:birthDate,:address,:phoneNumber)";

        // enum 처리 기능을 추가한 BeanPropertySqlParameterSourceForEnum 적용
        SqlParameterSource param = new BeanPropertySqlParameterSourceForEnum(member);

        KeyHolder keyHolder = new GeneratedKeyHolder();
        template.update(sql, param, keyHolder);

        long key = keyHolder.getKey().longValue();
        member.setId(key);
        return member;
    }

    @Override
    public void update(Long memberId, MemberUpdateParam updateParam) {
        String sql = "UPDATE members SET name = :name"
                                    + ", gender = :gender"
                                    + ", position = :position"
                                    + ", birth_date = :birthDate"
                                    + ", address = :address"
                                    + ", phone_number = :phoneNumber"
                                    + " WHERE id = :id";

        SqlParameterSource param = new MapSqlParameterSource()
            .addValue("name", updateParam.getName())
            .addValue("gender", updateParam.getGender().name())
            .addValue("position", updateParam.getPosition())
            .addValue("birthDate",updateParam.getBirthDate() )
            .addValue("address", updateParam.getAddress())
            .addValue("phoneNumber", updateParam.getPhoneNumber())
            .addValue("id", memberId);

        template.update(sql, param);
    }

    @Override
    public Optional<Member> findById(Long id) {
        String sql = "SELECT id, name, gender, position, birth_date, address, phone_number FROM members WHERE id=:id";
        Map<String, Long> param = Map.of("id", id);
        try {
            Member member = template.queryForObject(sql, param, memberRowMapper());
            return Optional.ofNullable(member);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private RowMapper<Member> memberRowMapper() {
        return BeanPropertyRowMapper.newInstance(Member.class);
    }

    @Override
    public List<Member> findAll(MemberSearchParam cond) {
        String name = cond.getName();
        String sql = "SELECT id, name, gender, position, birth_date, address, phone_number FROM members";

        if (StringUtils.hasText(name)) {
            sql += " WHERE name Like CONCAT('%',:name,'%')";
        }

        return template.query(sql, new BeanPropertySqlParameterSourceForEnum(cond), memberRowMapper());
    }

    @Override
    public void delete(Long memberId) {
        String sql = "DELETE FROM members WHERE id=:id";
        Map<String, Object> param = Map.of("id", memberId);
        template.update(sql, param);
    }
}
