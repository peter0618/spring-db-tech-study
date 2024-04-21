package com.peter.churchservice.repository.jdbctemplate;

import com.peter.churchservice.domain.member.Member;
import com.peter.churchservice.repository.MemberBulkRepository;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcTemplateMemberBulkRepository implements MemberBulkRepository {

    private final JdbcTemplate template;

    public JdbcTemplateMemberBulkRepository(DataSource dataSource) {
        this.template = new JdbcTemplate(dataSource);
    }

    @Override
    public void bulkInsert(List<Member> members) {
        String sql =
            "INSERT INTO members (name, gender, position, birth_date, address, phone_number)"
                + " VALUES (?,?,?,?,?,?)";

        template.batchUpdate(sql, new BatchPreparedStatementSetter() {

            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Member member = members.get(i);
                ps.setString(1, member.getName());
                ps.setString(2, member.getGender().name());
                ps.setString(3, member.getPosition());
                ps.setDate(4, Date.valueOf(member.getBirthDate()));
                ps.setString(5, member.getAddress());
                ps.setString(6, member.getPhoneNumber());
            }

            @Override
            public int getBatchSize() {
                return members.size();
            }
        });
    }
}
