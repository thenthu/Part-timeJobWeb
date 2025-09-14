/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
/**
 *
 * @author this pc
 */
public class NotificationDTO {

    private Integer notifId;
    private String content;
    private Boolean read;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate createdAt;
    private AccountDTO account;

    public Integer getNotifId() {
        return notifId;
    }

    public void setNotifId(Integer notifId) {
        this.notifId = notifId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public AccountDTO getAccount() {
        return account;
    }

    public void setAccount(AccountDTO account) {
        this.account = account;
    }
    
}
