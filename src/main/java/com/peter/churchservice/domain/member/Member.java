package com.peter.churchservice.domain.member;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class Member {
    private Long id;
    private String name;
    private Gender gender;
    private String position; // 직분
    private LocalDate birthDate;
    private String address;
    private String phoneNumber;
}
