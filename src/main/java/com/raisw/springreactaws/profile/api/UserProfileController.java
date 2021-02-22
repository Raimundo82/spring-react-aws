package com.raisw.springreactaws.profile.api;

import com.raisw.springreactaws.profile.UserProfile;
import com.raisw.springreactaws.profile.UserProfileDto;
import com.raisw.springreactaws.profile.service.UserProfileService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(path = "user-profile")
@CrossOrigin("*")
public class UserProfileController {

    private final UserProfileService userProfileService;

    @GetMapping
    public List<UserProfile> getUserProfiles() {
        return userProfileService.getUserProfiles();
    }

    @PostMapping(
            path = "/{id}/image/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public void uploadUserProfileImage(@PathVariable("id") Integer id, @RequestParam("file") MultipartFile file) {
        userProfileService.uploadUserProfileImage(id, file);
    }

    @GetMapping(path = "/{id}/image/download")
    public byte[] downloadUserProfileImage(@PathVariable("id") Integer id) {
        return userProfileService.downloadUserProfileImage(id);
    }

    @PostMapping(path = "/register-list")
    public List<UserProfileDto> registerList(@RequestBody List<UserProfileDto> profilesList) {
        return userProfileService.registerUserProfiles(profilesList);
    }

    @PostMapping(path = "/register")
    public UserProfileDto register(@RequestBody UserProfileDto profile) {
        return userProfileService.registerUserProfile(profile);
    }

}
