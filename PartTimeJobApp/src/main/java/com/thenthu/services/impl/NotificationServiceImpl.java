/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.services.impl;

import com.thenthu.dto.NotificationDTO;
import com.thenthu.mapper.NotificationMapper;
import com.thenthu.pojo.Account;
import com.thenthu.pojo.Notification;
import com.thenthu.repositories.AccountRepository;
import com.thenthu.repositories.NotificationRepository;
import com.thenthu.services.NotificationService;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author this pc
 */
@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepo;
    @Autowired
    private AccountRepository accountRepo;
    @Autowired
    private NotificationMapper notiMapper;

    @Override
    public List<NotificationDTO> getNotifications(Map<String, String> params) {
        List<Notification> notifications = notificationRepo.getNotifications(params);
        return notifications.stream()
                .map(notiMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public NotificationDTO getNotiById(int id) {
        Notification notification = notificationRepo.getNotiById(id);
        return notiMapper.toDTO(notification);
    }

    @Override
    public void sendNotification(NotificationDTO noti) {
        Notification entity = notiMapper.toEntity(noti);
        Account accEntity = accountRepo.getAccountByUsername(noti.getAccount().getUsername());
        entity.setIAccountId(accEntity);
        notificationRepo.sendNotification(entity);
    }

    @Override
    public void sendNotifications(List<NotificationDTO> notifications) {
        if (notifications != null && !notifications.isEmpty()) {
            List<Notification> entities = notifications.stream()
                    .map(dto -> {
                        Notification entity = notiMapper.toEntity(dto);

                        Account accEntity = accountRepo.getAccountByUsername(dto.getAccount().getUsername());
                        entity.setIAccountId(accEntity);
                        return entity;
                    })
                    .collect(Collectors.toList());
            notificationRepo.sendNotifications(entities);
        }
    }

    @Override
    public List<NotificationDTO> getNotiToUser(String username) {
        Account receiver = accountRepo.getAccountByUsername(username);

        List<Notification> notis = notificationRepo.getNotiToUser(receiver);
        return notis.stream()
                .map(notiMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void markAsRead(int id, String username) {
        Notification n = notificationRepo.getNotiById(id);
        if (n != null && n.getIAccountId().getSUsername().equals(username)) {
            n.setBRead(Boolean.TRUE);
            notificationRepo.saveNoti(n);
        }
    }
}
