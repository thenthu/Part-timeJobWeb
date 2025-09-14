/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.thenthu.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.web.multipart.MultipartFile;


/**
 *
 * @author this pc
 */
public class EmployerDTO extends AccountDTO {

    private Integer employerId;
    private String companyName;
    private String representativeName;
    private String representativeTitle;
    private String avatar;
    private String taxCode;
    private String workEnvImg1;
    private String workEnvImg2;
    private String workEnvImg3;
    @JsonIgnore
    private String verifyDoc;
    
    @JsonIgnore
    private MultipartFile avatarFile;
    @JsonIgnore
    private MultipartFile workEnvImg1File;
    @JsonIgnore
    private MultipartFile workEnvImg2File;
    @JsonIgnore
    private MultipartFile workEnvImg3File;
    @JsonIgnore
    private MultipartFile verifyDocFile;

    public Integer getEmployerId() {
        return employerId;
    }

    public void setEmployerId(Integer employerId) {
        this.employerId = employerId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getRepresentativeName() {
        return representativeName;
    }

    public void setRepresentativeName(String representativeName) {
        this.representativeName = representativeName;
    }

    public String getRepresentativeTitle() {
        return representativeTitle;
    }

    public void setRepresentativeTitle(String representativeTitle) {
        this.representativeTitle = representativeTitle;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getTaxCode() {
        return taxCode;
    }

    public void setTaxCode(String taxCode) {
        this.taxCode = taxCode;
    }

    public String getWorkEnvImg1() {
        return workEnvImg1;
    }

    public void setWorkEnvImg1(String workEnvImg1) {
        this.workEnvImg1 = workEnvImg1;
    }

    public String getWorkEnvImg2() {
        return workEnvImg2;
    }

    public void setWorkEnvImg2(String workEnvImg2) {
        this.workEnvImg2 = workEnvImg2;
    }

    public String getWorkEnvImg3() {
        return workEnvImg3;
    }

    public void setWorkEnvImg3(String workEnvImg3) {
        this.workEnvImg3 = workEnvImg3;
    }

    public String getVerifyDoc() {
        return verifyDoc;
    }

    public void setVerifyDoc(String verifyDoc) {
        this.verifyDoc = verifyDoc;
    }

    public MultipartFile getAvatarFile() {
        return avatarFile;
    }

    public void setAvatarFile(MultipartFile avatarFile) {
        this.avatarFile = avatarFile;
    }

    public MultipartFile getWorkEnvImg1File() {
        return workEnvImg1File;
    }

    public void setWorkEnvImg1File(MultipartFile workEnvImg1File) {
        this.workEnvImg1File = workEnvImg1File;
    }

    public MultipartFile getWorkEnvImg2File() {
        return workEnvImg2File;
    }

    public void setWorkEnvImg2File(MultipartFile workEnvImg2File) {
        this.workEnvImg2File = workEnvImg2File;
    }

    public MultipartFile getWorkEnvImg3File() {
        return workEnvImg3File;
    }

    public void setWorkEnvImg3File(MultipartFile workEnvImg3File) {
        this.workEnvImg3File = workEnvImg3File;
    }

    public MultipartFile getVerifyDocFile() {
        return verifyDocFile;
    }

    public void setVerifyDocFile(MultipartFile verifyDocFile) {
        this.verifyDocFile = verifyDocFile;
    }

}
