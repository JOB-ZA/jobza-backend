package jobza.member.entity;

import lombok.Getter;

@Getter
public enum Role {
    ROLE_USER("일반 회원"), ROLE_CORP("기업 회원");

    private String description;

    Role(String description) {
        this.description = description;
    }
}
