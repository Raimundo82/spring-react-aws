package com.raisw.springreactaws.profile.datasources;

import com.raisw.springreactaws.profile.UserProfile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InMemRep {

    private static final List<UserProfile> USER_PROFILES = new ArrayList<>();
    private static Integer id = 0;
    private static final String PROFILE = "profile-";

    static {
        USER_PROFILES.add(new UserProfile(++id, PROFILE + id, null));
        USER_PROFILES.add(new UserProfile(++id, PROFILE + id, null));
        USER_PROFILES.add(new UserProfile(++id, PROFILE + id, null));
        USER_PROFILES.add(new UserProfile(++id, PROFILE + id, null));
    }

    public List<UserProfile> getUserProfileList() {
        return USER_PROFILES;
    }
}
