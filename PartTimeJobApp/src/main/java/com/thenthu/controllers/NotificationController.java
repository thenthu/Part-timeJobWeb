/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.controllers;

import com.thenthu.dto.AccountDTO;
import com.thenthu.dto.NotificationDTO;
import com.thenthu.services.AccountService;
import com.thenthu.services.NotificationService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author this pc
 */
@Controller
@RequestMapping("/admin/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notiService;
    @Autowired
    private AccountService accService;

    @ModelAttribute
    public void response(Model model) {
        model.addAttribute("accounts", this.accService.getAccount(Map.of()));
    }

    @GetMapping
    public String getNotifications(Model model, @RequestParam Map<String, String> params) {
        model.addAttribute("notifications", this.notiService.getNotifications(params));
        return "notifications";
    }

    @GetMapping("/send")
    public String showAddForm(Model model) {
        model.addAttribute("notification", new NotificationDTO());
        return "notiForm";
    }

    @PostMapping("/send")
    public String sendNotification(
            @RequestParam String sendType,
            @RequestParam String content,
            @RequestParam(required = false) String account,
            @RequestParam(required = false) String groupType
    ) {
        if ("one".equals(sendType)) {
            NotificationDTO dto = new NotificationDTO();
            dto.setContent(content);

            AccountDTO acc = accService.getAccountByUsername(account);
            dto.setAccount(acc);
            notiService.sendNotification(dto);
        } else {
            List<AccountDTO> accounts = new ArrayList<>();
            if ("employer".equals(groupType) || "both".equals(groupType)) {
                accounts.addAll(accService.getAccountByRole("ROLE_EMPLOYER"));
            }
            if ("candidate".equals(groupType) || "both".equals(groupType)) {
                accounts.addAll(accService.getAccountByRole("ROLE_CANDIDATE"));
            }
            System.out.println(accounts);
            List<NotificationDTO> notifications = accounts.stream().map(acc -> {
                NotificationDTO dto = new NotificationDTO();
                dto.setContent(content);
                dto.setAccount(acc);
                return dto;
            }).collect(Collectors.toList());
            notiService.sendNotifications(notifications);
        }
        return "redirect:/admin/notifications";
    }
}