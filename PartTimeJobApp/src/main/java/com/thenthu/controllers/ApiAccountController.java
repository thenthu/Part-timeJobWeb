/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.controllers;

import com.thenthu.dto.AccountDTO;
import com.thenthu.dto.CandidateDTO;
import com.thenthu.dto.EmployerDTO;
import com.thenthu.dto.LoginDTO;
import com.thenthu.dto.NotificationDTO;
import com.thenthu.dto.RegisterDTO;
import com.thenthu.dto.ReviewDTO;
import com.thenthu.dto.ReviewListDTO;
import com.thenthu.services.AccountService;
import com.thenthu.services.NotificationService;
import com.thenthu.services.ProfileService;
import com.thenthu.services.ReviewService;
import com.thenthu.utils.JwtUtils;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author this pc
 */
@RestController
@RequestMapping("/api")
@CrossOrigin
public class ApiAccountController {

    @Autowired
    private AccountService accService;
    @Autowired
    private ProfileService profileService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private NotificationService notiService;

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @PostMapping(path = "/register")
    public ResponseEntity<?> create(@RequestBody RegisterDTO dto) {
        if (dto.getUsername() == null || dto.getPassword() == null || dto.getEmail() == null) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", "Thiếu thông tin đăng ký"));
        }
        RegisterDTO created = accService.addAccount(dto);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDTO a) {
        if (accService.authenticate(a.getUsername(), a.getPassword())) {
            try {
                AccountDTO acc = accService.getAccountByUsername(a.getUsername());
                String role = acc.getRole().getRoleName();

                String token = JwtUtils.generateToken(a.getUsername(), role);
                return ResponseEntity.ok(Collections.singletonMap("token", token));
            } catch (Exception e) {
                return ResponseEntity.status(500).body(Collections.singletonMap("error", "Lỗi khi tạo JWT"));
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Collections.singletonMap("error", "Sai thông tin đăng nhập hoặc tài khoản bị khóa"));
    }

    @GetMapping("/secure/profile")
    public ResponseEntity<AccountDTO> getProfile(Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
        AccountDTO dto = profileService.getProfile(principal.getName());

        return ResponseEntity.ok(dto);
    }
    
    @PatchMapping("/secure/profile/candidate")
    public ResponseEntity<?> updateCandidateProfile(
            @ModelAttribute CandidateDTO candidateDTO,
            @RequestParam(value = "avatar", required = false) MultipartFile avatar,
            Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(401).body("Chưa đăng nhập");
        }
        try {
            CandidateDTO updated = profileService.editCandidateProfile(candidateDTO, principal.getName(), avatar);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
    
    @PatchMapping("/secure/profile/employer")
    public ResponseEntity<?> updateEmployerProfile(
            @ModelAttribute("employer") EmployerDTO employer,
            @RequestParam(value = "avatar", required = false) MultipartFile avatar,
            @RequestParam(value = "workEnvImg1", required = false) MultipartFile workEnvImg1,
            @RequestParam(value = "workEnvImg2", required = false) MultipartFile workEnvImg2,
            @RequestParam(value = "workEnvImg3", required = false) MultipartFile workEnvImg3,
            Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(401).body("Chưa đăng nhập");
        }
        try {
            EmployerDTO updated = profileService.editEmployerProfile(employer, principal.getName(), avatar, workEnvImg1, workEnvImg2, workEnvImg3);
            return ResponseEntity.ok(updated);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
    
    @PatchMapping("/secure/profile/change-password")
    public ResponseEntity<?> changePassword(
            @RequestParam String oldPassword,
            @RequestParam String newPassword,
            Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(401).body("Chưa đăng nhập");
        }
        try {
            accService.changePassword(principal.getName(), oldPassword, newPassword);
            return ResponseEntity.ok("Đổi mật khẩu thành công");
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping("/profile/{username}/reviews")
    public ResponseEntity<ReviewListDTO> getReview(@RequestParam Map<String, String> params, @PathVariable("username") String username, Principal principal) {
        if (principal != null) {
            return new ResponseEntity<>(this.reviewService.getReviews(params, username, principal.getName()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(this.reviewService.getReviews(params, username, null), HttpStatus.OK);
        }
    }

    @PreAuthorize("hasAnyRole('CANDIDATE','EMPLOYER')")
    @PostMapping("/secure/profile/{username}/review")
    public ResponseEntity<?> addReview(@RequestBody ReviewDTO review, @PathVariable("username") String username, Principal principal) {
        try {
            ReviewDTO created = reviewService.addOrUpdateReview(review, principal.getName(), username);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Bạn đã đánh giá người dùng này rồi!");
        }
    }

    @PreAuthorize("@reviewSecurity.isOwner(#id, authentication.principal)")
    @PatchMapping("/secure/profile/{username}/review/{id}")
    public ResponseEntity<ReviewDTO> editReview(@RequestBody ReviewDTO review, @PathVariable("username") String username, @PathVariable("id") int id, Principal principal) {
        if (reviewService.getReviewsByUser(principal.getName()) != null) {
            AccountDTO to = profileService.getProfile(username);
            AccountDTO from = profileService.getProfile(principal.getName());

            review.setFromAccount(from);
            review.setToAccount(to);
            review.setReviewId(id);
            return new ResponseEntity<>(this.reviewService.addOrUpdateReview(review, principal.getName(), username), HttpStatus.OK);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @PreAuthorize("@reviewSecurity.isOwner(#id, authentication.principal)")
    @DeleteMapping("/secure/profile/review/{id}")
    public void deleteReview(@PathVariable("id") int id) {
        this.reviewService.deleteReview(id);
    }

    @GetMapping("/secure/notifications")
    public ResponseEntity<List<NotificationDTO>> getNotifications(Principal principal) {
        return new ResponseEntity<>(this.notiService.getNotiToUser(principal.getName()), HttpStatus.OK);
    }

    @PatchMapping("/secure/notification/{id}")
    public void markAsRead(@PathVariable("id") int id, Principal principal) {
        this.notiService.markAsRead(id, principal.getName());
    }
}
