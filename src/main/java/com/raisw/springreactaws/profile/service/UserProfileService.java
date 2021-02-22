package com.raisw.springreactaws.profile.service;

import com.raisw.springreactaws.bucket.BucketName;
import com.raisw.springreactaws.exceptions.AppException;
import com.raisw.springreactaws.filestore.FileStore;
import com.raisw.springreactaws.profile.UserProfile;
import com.raisw.springreactaws.profile.UserProfileDto;
import com.raisw.springreactaws.profile.datasources.UserProfileRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import static com.raisw.springreactaws.exceptions.ErrorMessage.*;


@Service
@AllArgsConstructor
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final FileStore fileStore;

    public List<UserProfile> getUserProfiles() {
        return userProfileRepository.findAll();
    }

    @Transactional
    public void uploadUserProfileImage(Integer id, MultipartFile file) {
        isFileEmpty(file);
        isImage(file);
        Map<String, String> metadata = collectMetadata(file);

        UserProfile user = getUserProfile(id);

        String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), user.getId());
        String filename = String.format("%s-%s", file.getOriginalFilename(), UUID.randomUUID());

        try {

            fileStore.save(path, filename, Optional.of(metadata), file.getInputStream());
            user.setUserProfileImageLink(filename);
        } catch (IOException e) {
            throw new AppException(FAILED_TO_STORE_FILE_S3, e.getMessage());
        }
    }

    public byte[] downloadUserProfileImage(Integer id) {
        UserProfile user = getUserProfile(id);
        String path = String.format("%s/%s",
                BucketName.PROFILE_IMAGE.getBucketName(),
                user.getId());

        return user.getUserProfileImageLink()
                .map(key -> fileStore.download(path, key))
                .orElse(new byte[0]);
    }

    public UserProfileDto registerUserProfile(UserProfileDto profileDto) {
        checkUsernameConsistency(profileDto);
        checkIfUsernameExistsAndIfNotSaveIt(profileDto);
        return new UserProfileDto(userProfileRepository
                .findByUsername(profileDto.getUsername())
                .orElseThrow(() -> new AppException(USER_NOT_REGISTERED, profileDto.getUsername())));
    }


    public List<UserProfileDto> registerUserProfiles(List<UserProfileDto> profilesList) {

        profilesList.forEach(this::checkIfUsernameExistsAndIfNotSaveIt);

        return userProfileRepository.findAll()
                .stream()
                .map(UserProfileDto::new)
                .collect(Collectors.toList());
    }

    private void checkUsernameConsistency(UserProfileDto profileDto) {
        if (profileDto.getUsername().isEmpty()) {
            throw new AppException(USERNAME_INVALID, profileDto.getUsername());
        }
    }

    private void checkIfUsernameExistsAndIfNotSaveIt(UserProfileDto profileDto) {
        Optional<UserProfile> user = userProfileRepository.findByUsername(profileDto.getUsername());

        if (user.isPresent())
            throw new AppException(USERNAME_ALREADY_EXISTS, profileDto.getUsername());

        userProfileRepository.save(new UserProfile(profileDto.getUsername(), null));
    }

    private Map<String, String> collectMetadata(MultipartFile file) {
        Map<String, String> metadata = new HashMap<>();
        metadata.put("Content-Type", file.getContentType());
        metadata.put("Content-Length", String.valueOf(file.getSize()));
        return metadata;
    }

    private UserProfile getUserProfile(Integer id) {
        return userProfileRepository.findById(id)
                .stream().filter(user -> user.getId().equals(id))
                .findFirst().orElseThrow(() -> new AppException(USER_PROFILE_NOT_FOUND, id));
    }

    private void isImage(MultipartFile file) {
        Optional<String> fileContentType = Optional.ofNullable(file.getContentType());

        if (fileContentType.isEmpty() || fileContentType.toString().contains("image/*"))
            throw new AppException(FILE_IS_NOT_IMAGE, file.getName());
    }

    private void isFileEmpty(MultipartFile file) {
        if (file.isEmpty())
            throw new AppException(IMAGE_FILE_IS_EMPTY, file.getName());
    }


}

