/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.controllers;

import com.thenthu.dto.AccountAdminDTO;
import com.thenthu.services.AccountService;
import com.thenthu.services.RoleService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author this pc
 */
@Controller
@RequestMapping("/admin/accounts")
public class AccountController {

    @Autowired
    private AccountService accService;
    @Autowired
    private RoleService roleService;

    @ModelAttribute
    public void response(Model model) {
        model.addAttribute("roles", this.roleService.getRoles(Map.of()));
    }

    @GetMapping
    public String listAccounts(Model model, @RequestParam Map<String, String> params) {
        int page = 1;
        if (params.containsKey("page")) {
            try {
                page = Integer.parseInt(params.get("page"));
            } catch (NumberFormatException ex) {
                page = 1;
            }
        }
        model.addAttribute("page", page);
        
        model.addAttribute("accounts", this.accService.getAccount(params));
        return "accounts";
    }

    @GetMapping("/add")
    public String showAddForm(Model model) {
        model.addAttribute("account", new AccountAdminDTO());
        return "accountForm";
    }

    @PostMapping("/add")
    public String addJobs(@ModelAttribute("account") AccountAdminDTO c) {
        this.accService.addOrUpdateAccount(c);
        return "redirect:/admin/accounts";
    }

    @GetMapping("/update/{iAccountId}")
    public String updateJob(Model model, @PathVariable("iAccountId") int id) {
        model.addAttribute("account", this.accService.getAccountById(id));
        return "accountForm";
    }
}
