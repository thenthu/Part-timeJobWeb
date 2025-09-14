/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.services;

import com.thenthu.dto.NotificationDTO;
import java.util.List;
import java.util.Map;

/**
 *
 * @author this pc
 */
public interface NotificationService {
    List<NotificationDTO> getNotifications(Map<String, String> params);
    NotificationDTO getNotiById(int id);
    void sendNotification(NotificationDTO noti);
    void sendNotifications(List<NotificationDTO> notifications);
    
    List<NotificationDTO> getNotiToUser(String username);
    void markAsRead(int id, String username);
}
