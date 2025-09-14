/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.repositories;

import com.thenthu.pojo.Role;
import java.util.List;
import java.util.Map;

/**
 *
 * @author this pc
 */
public interface RoleRepository {
    List<Role> getRoles(Map<String, String> params);
    Role findByName(String name);
    Role findById(int id);
}
