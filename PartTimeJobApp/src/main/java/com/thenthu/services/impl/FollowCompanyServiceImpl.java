/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.services.impl;

import com.thenthu.dto.FollowCompanyDTO;
import com.thenthu.mapper.FollowCompanyMapper;
import com.thenthu.pojo.Followcompany;
import com.thenthu.repositories.FollowCompanyRepository;
import com.thenthu.services.FollowCompanyService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author this pc
 */
@Service
public class FollowCompanyServiceImpl implements FollowCompanyService {
    @Autowired
    private FollowCompanyRepository followCompanyRepository;

    @Autowired
    private FollowCompanyMapper followCompanyMapper;

    @Override
    public List<FollowCompanyDTO> getFollowCompaniesByCandidateId(int candidateId) {
        List<Followcompany> list = followCompanyRepository.getFollowCompaniesByCandidateId(candidateId);
        return list.stream()
                .map(followCompanyMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<FollowCompanyDTO> getFollowCompaniesByEmployerId(int employerId) {
        List<Followcompany> list = followCompanyRepository.getFollowCompaniesByEmployerId(employerId);
        return list.stream()
                .map(followCompanyMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    @Override
    public FollowCompanyDTO getFollowCompanyById(int candidateId, int employerId) {
        Followcompany follow = this.followCompanyRepository.getFollowCompanyById(candidateId, employerId);
        FollowCompanyDTO followDTO = followCompanyMapper.toDTO(follow);
        
        if(followDTO != null) {
            return followDTO;
        }
        else {
            return null;
        }
    }

    @Override
    public void addFollow(FollowCompanyDTO followDTO) {
        Followcompany follow = followCompanyMapper.toEntity(followDTO);
        this.followCompanyRepository.addFollow(follow);
    }

    @Override
    public void unfollow(int candidateId, int employerId) {
        this.followCompanyRepository.unfollow(candidateId, employerId);
    }
}
