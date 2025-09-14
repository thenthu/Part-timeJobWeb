/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.services;

import com.thenthu.dto.FollowCompanyDTO;
import java.util.List;

/**
 *
 * @author this pc
 */
public interface FollowCompanyService {
    List<FollowCompanyDTO> getFollowCompaniesByCandidateId(int candidateId);
    List<FollowCompanyDTO> getFollowCompaniesByEmployerId(int employerId);
    
    FollowCompanyDTO getFollowCompanyById(int candidateId, int employerId);
    void addFollow(FollowCompanyDTO followDTO);
    void unfollow(int candidateId, int employerId);
}
