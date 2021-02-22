package com.raisw.springreactaws.profile;

import com.google.common.base.Objects;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="user_profiles")
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String username;

    private String userProfileImageLink; // S3 Key

    public UserProfile(String username, String userProfileImageLink) {
        this.username = username;
        this.userProfileImageLink = userProfileImageLink;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserProfile that = (UserProfile) o;
        return Objects.equal(id, that.id) &&
                Objects.equal(username, that.username) &&
                Objects.equal(userProfileImageLink, that.userProfileImageLink);
    }

    public Optional<String> getUserProfileImageLink() {
        return Optional.ofNullable(userProfileImageLink);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, username, userProfileImageLink);
    }
}
