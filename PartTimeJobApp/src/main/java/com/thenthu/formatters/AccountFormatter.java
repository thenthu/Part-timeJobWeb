/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.formatters;

import com.thenthu.dto.AccountAdminDTO;
import java.text.ParseException;
import java.util.Locale;
import org.springframework.format.Formatter;

/**
 *
 * @author this pc
 */
public class AccountFormatter implements Formatter<AccountAdminDTO>{
    @Override
    public String print(AccountAdminDTO acc, Locale locale) {
        return String.valueOf(acc.getAccountId());
    }

    @Override
    public AccountAdminDTO parse(String accountId, Locale locale) throws ParseException {
        AccountAdminDTO dto = new AccountAdminDTO();
        dto.setAccountId(Integer.valueOf(accountId));
        return dto;
    }
}
