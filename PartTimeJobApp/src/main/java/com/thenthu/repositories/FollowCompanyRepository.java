/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.repositories;

import com.thenthu.pojo.Followcompany;
import java.util.List;

/**
 *
 * @author this pc
 */
public interface FollowCompanyRepository {
    List<Followcompany> getFollowCompaniesByCandidateId(int candidateId);
    List<Followcompany> getFollowCompaniesByEmployerId(int employerId);
    
    Followcompany getFollowCompanyById(int candidateId, int employerId);
    void addFollow(Followcompany follow);
    void unfollow(int candidateId, int employerId);
}
