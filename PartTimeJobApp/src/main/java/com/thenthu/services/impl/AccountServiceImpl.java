/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.services.impl;

import com.thenthu.dto.AccountAdminDTO;
import com.thenthu.dto.AccountDTO;
import com.thenthu.dto.RegisterDTO;
import com.thenthu.mapper.AccountMapper;
import com.thenthu.mapper.RegisterMapper;
import com.thenthu.pojo.Account;
import com.thenthu.pojo.Role;
import com.thenthu.repositories.AccountRepository;
import com.thenthu.repositories.RoleRepository;
import com.thenthu.services.AccountService;
import org.springframework.security.access.AccessDeniedException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *
 * @author this pc
 */
@Service("userDetailsService")
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private RegisterMapper registerMapper;
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<AccountAdminDTO> getAccount(Map<String, String> params) {
        List<Account> accounts = accountRepository.getAccount(params);
        return accounts.stream()
                .map(accountMapper::toAdminDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AccountAdminDTO getAccountById(int id) {
        Account account = accountRepository.getAccountById(id);
        if (account == null) {
            return null;
        }
        return accountMapper.toAdminDTO(account);
    }

    @Override
    public void addOrUpdateAccount(AccountAdminDTO a) {
        boolean isUpdate = a.getAccountId() != null;

        if (!isUpdate || (isUpdate && !accountRepository.getAccountById(a.getAccountId()).getSUsername().equals(a.getUsername()))) {
            if (accountRepository.existsByUsername(a.getUsername())) {
                throw new IllegalArgumentException("Username đã tồn tại!");
            }
        }
        if (!isUpdate || (isUpdate && !accountRepository.getAccountById(a.getAccountId()).getSEmail().equals(a.getEmail()))) {
            if (accountRepository.existsByEmail(a.getEmail())) {
                throw new IllegalArgumentException("Email đã tồn tại!");
            }
        }

        Account account;
        if (isUpdate) {
            account = accountRepository.getAccountById(a.getAccountId());
            if (account == null) {
                throw new IllegalArgumentException("Không tìm thấy tài khoản!");
            }

            account.setSUsername(a.getUsername());
            account.setSEmail(a.getEmail());
            account.setBStatus(a.getStatus());

            if (a.getPassword() != null && !a.getPassword().isBlank()) {
                account.setSPassword(passwordEncoder.encode(a.getPassword()));
            }
            if (a.getRole() != null) {
                Integer roleId = a.getRole().getRoleId();
                Role role = roleRepository.findById(roleId);
                account.setIRoleId(role);
            }
        } else {
            account = accountMapper.toEntity(a);

            account.setSPassword(passwordEncoder.encode(a.getPassword()));

            if (a.getRole() != null) {
                Integer roleId = a.getRole().getRoleId();
                Role role = roleRepository.findById(roleId);
                account.setIRoleId(role);
            }
        }

        accountRepository.addOrUpdateAccount(account);
    }

//    @Override
//    public void deleteAccount(int id) {
//        accountRepository.deleteAccount(id);
//    }
    @Override
    public AccountDTO getAccountByUsername(String username) {
        Account account = accountRepository.getAccountByUsername(username);
        if (account == null) {
            return null;
        }
        return accountMapper.toDTO(account);
    }

    @Override
    public RegisterDTO addAccount(RegisterDTO r) {

        if (accountRepository.existsByUsername(r.getUsername())) {
            throw new IllegalArgumentException("Username đã tồn tại!");
        }

        if (accountRepository.existsByEmail(r.getEmail())) {
            throw new IllegalArgumentException("Email đã tồn tại!");
        }

        Account account;
        account = registerMapper.toEntity(r);

        account.setSPassword(passwordEncoder.encode(r.getPassword()));

        if (r.getRole() != null) {
            if (r.getRole().getRoleId() != 1) {
                Integer roleId = r.getRole().getRoleId();
                Role role = roleRepository.findById(roleId);
                account.setIRoleId(role);
            } else {
                throw new AccessDeniedException("Bạn chỉ được đăng ký vai trò nhà tuyển dụng hoặc ứng viên!");
            }
        }

        Account savedAccount = accountRepository.addAccount(account);

        RegisterDTO resultDTO = registerMapper.toDTO(savedAccount);
        return resultDTO;
    }

    @Override
    public boolean authenticate(String username, String password) {
        return this.accountRepository.authenticate(username, password);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.getAccountByUsername(username);
        if (account == null) {
            throw new UsernameNotFoundException("Invalid username!");
        }

        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(account.getIRoleId().getSRoleName()));

        return new org.springframework.security.core.userdetails.User(
                account.getSUsername(), account.getSPassword(), authorities);
    }

    @Override
    public List<AccountDTO> getAccountByRole(String role) {
        List<Account> accounts = accountRepository.getAccountByRole(role);
        return accounts.stream()
                .map(accountMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void changePassword(String username, String oldPassword, String newPassword) {
        Account acc = accountRepository.getAccountByUsername(username);

        if (!passwordEncoder.matches(oldPassword, acc.getSPassword())) {
            throw new IllegalArgumentException("Mật khẩu cũ không đúng!");
        }

        acc.setSPassword(passwordEncoder.encode(newPassword));
        accountRepository.addOrUpdateAccount(acc);
    }
}
