/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.repositories;

import com.thenthu.pojo.Account;
import java.util.List;
import java.util.Map;

/**
 *
 * @author this pc
 */
public interface AccountRepository {
    List<Account> getAccount(Map<String, String> params);
    Account getAccountById(int id);
    void addOrUpdateAccount(Account a);
    List<Account> getAccountByRole(String role);
    
    Account getAccountByUsername(String username);
    Account addAccount(Account a);
    boolean authenticate(String username, String password);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
}
