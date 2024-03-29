package com.example.service;

import com.example.entity.ProfileEntity;
import com.example.enums.ProfileRole;
import com.example.enums.ProfileStatus;
import com.example.repository.ProfileRepository;
import com.example.util.MDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class InitService {
    @Autowired
    private ProfileRepository profileRepository;

    public void initAdmin() {
        String adminEmail = "admin@mail.ru";
        Optional<ProfileEntity> optional = profileRepository.findByEmail(adminEmail);
        if (optional.isPresent()) {
            return;
        }
        // create admin
        ProfileEntity admin = new ProfileEntity();
        admin.setName("Admin");
        admin.setSurname("Adminjon");
        admin.setEmail(adminEmail);
        admin.setStatus(ProfileStatus.ACTIVE);
        admin.setRole(ProfileRole.ADMIN);
        admin.setPassword(MDUtil.encode("12345"));
        profileRepository.save(admin);
    }

    public void initModerator() {
        String adminEmail = "moderator@mail.ru";
        Optional<ProfileEntity> optional = profileRepository.findByEmail(adminEmail);
        if (optional.isPresent()) {
            return;
        }
        // create admin
        ProfileEntity admin = new ProfileEntity();
        admin.setName("Moderator");
        admin.setSurname("Moderator");
        admin.setEmail(adminEmail);
        admin.setStatus(ProfileStatus.ACTIVE);
        admin.setRole(ProfileRole.MODERATOR);
        admin.setPassword(MDUtil.encode("12345"));
        profileRepository.save(admin);
    }

}
