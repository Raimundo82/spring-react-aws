package com.raisw.springreactaws.profile.datasources;


import com.raisw.springreactaws.profile.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {

    @Query("SELECT user FROM UserProfile user WHERE user.username = ?1")
    public Optional<UserProfile> findByUsername(String username);


}
