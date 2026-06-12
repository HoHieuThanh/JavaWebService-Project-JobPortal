package com.ra.jobportal.user.controller;

import com.ra.jobportal.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/candidate/profile")
@RequiredArgsConstructor
public class CandidateProfileController {

    private final UserService userService;

    @PostMapping(
            value = "/upload-cv",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public String uploadCv(

            @RequestPart("file")
            MultipartFile file,

            Authentication authentication
    ) {

        return userService.uploadCv(
                file,
                authentication.getName()
        );
    }
}