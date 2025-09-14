/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.services;

import com.thenthu.dto.AccountAdminDTO;
import com.thenthu.dto.AccountDTO;
import com.thenthu.dto.RegisterDTO;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 *
 * @author this pc
 */
public interface AccountService extends UserDetailsService {
    List<AccountAdminDTO> getAccount(Map<String, String> params);
    AccountAdminDTO getAccountById(int id);
    void addOrUpdateAccount(AccountAdminDTO a);
//    void deleteAccount(int id);
    
    AccountDTO getAccountByUsername(String username);
    RegisterDTO addAccount(RegisterDTO r);
    boolean authenticate(String username, String password);
    List<AccountDTO> getAccountByRole(String role);
    void changePassword(String username, String oldPassword, String newPassword);
}
