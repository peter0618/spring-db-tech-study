package com.peter.churchservice.repository.dto;

import lombok.Getter;

@Getter
public class MemberSearchParam {
    private String name;

    public MemberSearchParam(String name) {
        this.name = name;
    }
}
