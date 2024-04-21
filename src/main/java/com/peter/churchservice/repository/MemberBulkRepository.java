package com.peter.churchservice.repository;

import com.peter.churchservice.domain.member.Member;
import java.util.List;

public interface MemberBulkRepository {

    void bulkInsert(List<Member> members);
}
