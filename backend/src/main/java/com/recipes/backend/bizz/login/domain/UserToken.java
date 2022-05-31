package com.recipes.backend.bizz.login.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserToken {

    private String userToken;
    private Boolean isAdmin;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserToken userToken1 = (UserToken) o;
        return Objects.equals(userToken, userToken1.userToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userToken);
    }
}
