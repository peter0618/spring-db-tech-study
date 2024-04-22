package com.peter.churchservice.repository.jdbctemplate;

import com.peter.churchservice.domain.member.Member;
import com.peter.churchservice.repository.MemberBulkRepository;
import com.peter.churchservice.repository.jdbctemplate.common.BeanPropertySqlParameterSourceForEnum;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class SimpleJdbcInsertMemberBulkRepository implements MemberBulkRepository {

    private final SimpleJdbcInsert jdbcInsert;

    public SimpleJdbcInsertMemberBulkRepository(DataSource dataSource) {
        this.jdbcInsert = new SimpleJdbcInsert(dataSource)
            .withTableName("members")
            .usingGeneratedKeyColumns("id");
    }

    @Override
    public void bulkInsert(List<Member> members) {
        SqlParameterSource[] sqlParameterSources = members
            .stream()
            .map(BeanPropertySqlParameterSourceForEnum::new)
            .toArray(SqlParameterSource[]::new);

        jdbcInsert.executeBatch(sqlParameterSources);
    }
}
