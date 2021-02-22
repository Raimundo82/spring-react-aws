package com.raisw.springreactaws.profile;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserProfileDto {
    private int id;
    private String username;

    public UserProfileDto(UserProfile userProfile) {
        this.id = userProfile.getId();
        this.username = userProfile.getUsername();
    }
}
