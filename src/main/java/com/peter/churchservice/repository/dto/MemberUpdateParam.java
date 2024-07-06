package com.peter.churchservice.repository.dto;

import com.peter.churchservice.domain.member.Gender;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberUpdateParam {
    private String name;
    private Gender gender;
    private String position; // 직분
    private LocalDate birthDate;
    private String address;
    private String phoneNumber;
}
