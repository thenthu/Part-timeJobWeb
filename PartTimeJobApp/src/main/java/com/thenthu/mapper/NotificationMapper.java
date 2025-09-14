/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.mapper;

import com.thenthu.dto.NotificationDTO;
import com.thenthu.pojo.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 *
 * @author this pc
 */
@Mapper(
    componentModel = "spring",
    uses = { AccountMapper.class }
)
public interface NotificationMapper {
    // Entity → DTO
    @Mapping(source = "INotifId", target = "notifId")
    @Mapping(source = "SContent", target = "content")
    @Mapping(source = "BRead", target = "read")
    @Mapping(source = "DCreatedAt", target = "createdAt")
    @Mapping(source = "IAccountId", target = "account")
    NotificationDTO toDTO(Notification notification);

    // DTO → Entity
    @Mapping(source = "notifId", target = "INotifId")
    @Mapping(source = "content", target = "SContent")
    @Mapping(source = "read", target = "BRead")
    @Mapping(source = "createdAt", target = "DCreatedAt")
    @Mapping(source = "account", target = "IAccountId")
    Notification toEntity(NotificationDTO notificationDTO);
}
