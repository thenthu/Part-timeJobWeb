/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.repositories;

import com.thenthu.pojo.Account;
import com.thenthu.pojo.Notification;
import java.util.List;
import java.util.Map;

/**
 *
 * @author this pc
 */
public interface NotificationRepository {
    List<Notification> getNotifications(Map<String, String> params);
    Notification getNotiById(int id);
    void sendNotification(Notification noti);
    void sendNotifications(List<Notification> notifications);
    
    List<Notification> getNotiToUser(Account acc);
    void saveNoti(Notification n);
}
