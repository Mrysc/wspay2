package com.rltx.wspay.account.entity;

import java.util.Date;

public class MemberPersonInfoEntity {
  private Long id;
  private String code;
  private String uniqueSecret;
  private String requestOrgCode;
  private String requestOrgFullname;
  private String requestUserCode;
  private String requestUserFullname;
  private Date requestTime;
  private String auditUserCode;
  private String auditUserFullname;
  private Date auditTime;
  private String certStatus;
  private String userStatus;
  private Integer blacklistFlag;
  private Boolean rootFlag;
  private Integer userType;
  private String fullName;
  private String positionName;
  private String gender;
  private String phone;
  private String email;
  private String identityNumber;
  private String identityResourceCode;
  private Date identityExpirationDate;
  private String driverLicenseNo;
  private String driverLicenseResourceCode;
  private Date driverLicenseExpirationDate;
  private String driverLicenseAcceptType;
  private String qualificationCertificateNumber;
  private String qualificationCertificateResourceCode;
  private Date qualificationCertificateExpirationDate;
  private String creditCertificateResourceCode;
  private String safetyAgreementResourceCode;
  private String preJobTrainingResourceCode;
  private String bankAccountCode;
  private String bankAccountName;
  private String bankAccountDescription;
  private String fuelCardNo;
  private String bankName;
  private String bankAccountNo;
  private String headPictureResourceCode;
  private String politicalStatus;
  private String maritalStatus;
  private String nativePlace;
  private String registedResidence;
  private String identityCardAddress;
  private String currentResidence;
  private String emergencyContactPerson;
  private String emergencyContactPhone;
  private String schoolOfGraduation;
  private String education;
  private String major;
  private Date startDateOfFirstJob;
  private Date employmentDate;
  private String customData;
  private String userTag;
  private String description;
  private String auditDescription;
  private String remark;
  private Boolean disabled;
  private Boolean deleted;
  private String moduleCode;
  private String creatorUserCode;
  private String creatorUsername;
  private Date createTime;
  private String updateUserCode;
  private String updateUsername;
  private Date updateTime;
  private String ip;
  private Double operatorLongitude;
  private Double operatorLatitude;
  private String ownerUserCode;
  private String ownerOrgCode;
  private String ownerOrgName;
  private String synchronousId;
  private String uploadStatus;

  public void setId(Long id) {
    this.id = id;
  }
  
  public Long getId() {
    return this.id;
  }
  
  public void setCode(String code) {
    this.code = code;
  }
  
  public String getCode() {
    return this.code;
  }
  
  public void setUniqueSecret(String uniqueSecret) {
    this.uniqueSecret = uniqueSecret;
  }
  
  public String getUniqueSecret() {
    return this.uniqueSecret;
  }
  
  public void setRequestOrgCode(String requestOrgCode) {
    this.requestOrgCode = requestOrgCode;
  }
  
  public String getRequestOrgCode() {
    return this.requestOrgCode;
  }
  
  public void setRequestOrgFullname(String requestOrgFullname) {
    this.requestOrgFullname = requestOrgFullname;
  }
  
  public String getRequestOrgFullname() {
    return this.requestOrgFullname;
  }
  
  public void setRequestUserCode(String requestUserCode) {
    this.requestUserCode = requestUserCode;
  }
  
  public String getRequestUserCode() {
    return this.requestUserCode;
  }
  
  public void setRequestUserFullname(String requestUserFullname) {
    this.requestUserFullname = requestUserFullname;
  }
  
  public String getRequestUserFullname() {
    return this.requestUserFullname;
  }
  
  public void setRequestTime(Date requestTime) {
    this.requestTime = requestTime;
  }
  
  public Date getRequestTime() {
    return this.requestTime;
  }
  
  public void setAuditUserCode(String auditUserCode) {
    this.auditUserCode = auditUserCode;
  }
  
  public String getAuditUserCode() {
    return this.auditUserCode;
  }
  
  public void setAuditUserFullname(String auditUserFullname) {
    this.auditUserFullname = auditUserFullname;
  }
  
  public String getAuditUserFullname() {
    return this.auditUserFullname;
  }
  
  public void setAuditTime(Date auditTime) {
    this.auditTime = auditTime;
  }
  
  public Date getAuditTime() {
    return this.auditTime;
  }
  
  public void setCertStatus(String certStatus) {
    this.certStatus = certStatus;
  }
  
  public String getCertStatus() {
    return this.certStatus;
  }
  
  public void setUserStatus(String userStatus) {
    this.userStatus = userStatus;
  }
  
  public String getUserStatus() {
    return this.userStatus;
  }
  
  public void setBlacklistFlag(Integer blacklistFlag) {
    this.blacklistFlag = blacklistFlag;
  }
  
  public Integer getBlacklistFlag() {
    return this.blacklistFlag;
  }
  
  public void setRootFlag(Boolean rootFlag) {
    this.rootFlag = rootFlag;
  }
  
  public Boolean getRootFlag() {
    return this.rootFlag;
  }
  
  public void setUserType(Integer userType) {
    this.userType = userType;
  }
  
  public Integer getUserType() {
    return this.userType;
  }
  
  public void setFullName(String fullName) {
    this.fullName = fullName;
  }
  
  public String getFullName() {
    return this.fullName;
  }
  
  public void setPositionName(String positionName) {
    this.positionName = positionName;
  }
  
  public String getPositionName() {
    return this.positionName;
  }
  
  public void setGender(String gender) {
    this.gender = gender;
  }
  
  public String getGender() {
    return this.gender;
  }
  
  public void setPhone(String phone) {
    this.phone = phone;
  }
  
  public String getPhone() {
    return this.phone;
  }
  
  public void setEmail(String email) {
    this.email = email;
  }
  
  public String getEmail() {
    return this.email;
  }
  
  public void setIdentityNumber(String identityNumber) {
    this.identityNumber = identityNumber;
  }
  
  public String getIdentityNumber() {
    return this.identityNumber;
  }
  
  public void setIdentityResourceCode(String identityResourceCode) {
    this.identityResourceCode = identityResourceCode;
  }
  
  public String getIdentityResourceCode() {
    return this.identityResourceCode;
  }
  
  public void setIdentityExpirationDate(Date identityExpirationDate) {
    this.identityExpirationDate = identityExpirationDate;
  }
  
  public Date getIdentityExpirationDate() {
    return this.identityExpirationDate;
  }
  
  public void setDriverLicenseNo(String driverLicenseNo) {
    this.driverLicenseNo = driverLicenseNo;
  }
  
  public String getDriverLicenseNo() {
    return this.driverLicenseNo;
  }
  
  public void setDriverLicenseResourceCode(String driverLicenseResourceCode) {
    this.driverLicenseResourceCode = driverLicenseResourceCode;
  }
  
  public String getDriverLicenseResourceCode() {
    return this.driverLicenseResourceCode;
  }
  
  public void setDriverLicenseExpirationDate(Date driverLicenseExpirationDate) {
    this.driverLicenseExpirationDate = driverLicenseExpirationDate;
  }
  
  public Date getDriverLicenseExpirationDate() {
    return this.driverLicenseExpirationDate;
  }
  
  public void setDriverLicenseAcceptType(String driverLicenseAcceptType) {
    this.driverLicenseAcceptType = driverLicenseAcceptType;
  }
  
  public String getDriverLicenseAcceptType() {
    return this.driverLicenseAcceptType;
  }
  
  public void setQualificationCertificateNumber(String qualificationCertificateNumber) {
    this.qualificationCertificateNumber = qualificationCertificateNumber;
  }
  
  public String getQualificationCertificateNumber() {
    return this.qualificationCertificateNumber;
  }
  
  public void setQualificationCertificateResourceCode(String qualificationCertificateResourceCode) {
    this.qualificationCertificateResourceCode = qualificationCertificateResourceCode;
  }
  
  public String getQualificationCertificateResourceCode() {
    return this.qualificationCertificateResourceCode;
  }
  
  public void setQualificationCertificateExpirationDate(Date qualificationCertificateExpirationDate) {
    this.qualificationCertificateExpirationDate = qualificationCertificateExpirationDate;
  }
  
  public Date getQualificationCertificateExpirationDate() {
    return this.qualificationCertificateExpirationDate;
  }
  
  public void setCreditCertificateResourceCode(String creditCertificateResourceCode) {
    this.creditCertificateResourceCode = creditCertificateResourceCode;
  }
  
  public String getCreditCertificateResourceCode() {
    return this.creditCertificateResourceCode;
  }
  
  public void setSafetyAgreementResourceCode(String safetyAgreementResourceCode) {
    this.safetyAgreementResourceCode = safetyAgreementResourceCode;
  }
  
  public String getSafetyAgreementResourceCode() {
    return this.safetyAgreementResourceCode;
  }
  
  public void setPreJobTrainingResourceCode(String preJobTrainingResourceCode) {
    this.preJobTrainingResourceCode = preJobTrainingResourceCode;
  }
  
  public String getPreJobTrainingResourceCode() {
    return this.preJobTrainingResourceCode;
  }
  
  public void setBankAccountCode(String bankAccountCode) {
    this.bankAccountCode = bankAccountCode;
  }
  
  public String getBankAccountCode() {
    return this.bankAccountCode;
  }
  
  public void setBankAccountName(String bankAccountName) {
    this.bankAccountName = bankAccountName;
  }
  
  public String getBankAccountName() {
    return this.bankAccountName;
  }
  
  public void setBankAccountDescription(String bankAccountDescription) {
    this.bankAccountDescription = bankAccountDescription;
  }
  
  public String getBankAccountDescription() {
    return this.bankAccountDescription;
  }
  
  public void setFuelCardNo(String fuelCardNo) {
    this.fuelCardNo = fuelCardNo;
  }
  
  public String getFuelCardNo() {
    return this.fuelCardNo;
  }
  
  public void setBankName(String bankName) {
    this.bankName = bankName;
  }
  
  public String getBankName() {
    return this.bankName;
  }
  
  public void setBankAccountNo(String bankAccountNo) {
    this.bankAccountNo = bankAccountNo;
  }
  
  public String getBankAccountNo() {
    return this.bankAccountNo;
  }
  
  public void setHeadPictureResourceCode(String headPictureResourceCode) {
    this.headPictureResourceCode = headPictureResourceCode;
  }
  
  public String getHeadPictureResourceCode() {
    return this.headPictureResourceCode;
  }
  
  public void setPoliticalStatus(String politicalStatus) {
    this.politicalStatus = politicalStatus;
  }
  
  public String getPoliticalStatus() {
    return this.politicalStatus;
  }
  
  public void setMaritalStatus(String maritalStatus) {
    this.maritalStatus = maritalStatus;
  }
  
  public String getMaritalStatus() {
    return this.maritalStatus;
  }
  
  public void setNativePlace(String nativePlace) {
    this.nativePlace = nativePlace;
  }
  
  public String getNativePlace() {
    return this.nativePlace;
  }
  
  public void setRegistedResidence(String registedResidence) {
    this.registedResidence = registedResidence;
  }
  
  public String getRegistedResidence() {
    return this.registedResidence;
  }
  
  public void setIdentityCardAddress(String identityCardAddress) {
    this.identityCardAddress = identityCardAddress;
  }
  
  public String getIdentityCardAddress() {
    return this.identityCardAddress;
  }
  
  public void setCurrentResidence(String currentResidence) {
    this.currentResidence = currentResidence;
  }
  
  public String getCurrentResidence() {
    return this.currentResidence;
  }
  
  public void setEmergencyContactPerson(String emergencyContactPerson) {
    this.emergencyContactPerson = emergencyContactPerson;
  }
  
  public String getEmergencyContactPerson() {
    return this.emergencyContactPerson;
  }
  
  public void setEmergencyContactPhone(String emergencyContactPhone) {
    this.emergencyContactPhone = emergencyContactPhone;
  }
  
  public String getEmergencyContactPhone() {
    return this.emergencyContactPhone;
  }
  
  public void setSchoolOfGraduation(String schoolOfGraduation) {
    this.schoolOfGraduation = schoolOfGraduation;
  }
  
  public String getSchoolOfGraduation() {
    return this.schoolOfGraduation;
  }
  
  public void setEducation(String education) {
    this.education = education;
  }
  
  public String getEducation() {
    return this.education;
  }
  
  public void setMajor(String major) {
    this.major = major;
  }
  
  public String getMajor() {
    return this.major;
  }
  
  public void setStartDateOfFirstJob(Date startDateOfFirstJob) {
    this.startDateOfFirstJob = startDateOfFirstJob;
  }
  
  public Date getStartDateOfFirstJob() {
    return this.startDateOfFirstJob;
  }
  
  public void setEmploymentDate(Date employmentDate) {
    this.employmentDate = employmentDate;
  }
  
  public Date getEmploymentDate() {
    return this.employmentDate;
  }
  
  public void setCustomData(String customData) {
    this.customData = customData;
  }
  
  public String getCustomData() {
    return this.customData;
  }
  
  public void setUserTag(String userTag) {
    this.userTag = userTag;
  }
  
  public String getUserTag() {
    return this.userTag;
  }
  
  public void setDescription(String description) {
    this.description = description;
  }
  
  public String getDescription() {
    return this.description;
  }
  
  public void setRemark(String remark) {
    this.remark = remark;
  }
  
  public String getRemark() {
    return this.remark;
  }
  
  public void setDisabled(Boolean disabled) {
    this.disabled = disabled;
  }
  
  public Boolean getDisabled() {
    return this.disabled;
  }
  
  public void setDeleted(Boolean deleted) {
    this.deleted = deleted;
  }
  
  public Boolean getDeleted() {
    return this.deleted;
  }
  
  public void setModuleCode(String moduleCode) {
    this.moduleCode = moduleCode;
  }
  
  public String getModuleCode() {
    return this.moduleCode;
  }
  
  public void setCreatorUserCode(String creatorUserCode) {
    this.creatorUserCode = creatorUserCode;
  }
  
  public String getCreatorUserCode() {
    return this.creatorUserCode;
  }
  
  public void setCreatorUsername(String creatorUsername) {
    this.creatorUsername = creatorUsername;
  }
  
  public String getCreatorUsername() {
    return this.creatorUsername;
  }
  
  public void setCreateTime(Date createTime) {
    this.createTime = createTime;
  }
  
  public Date getCreateTime() {
    return this.createTime;
  }
  
  public void setUpdateUserCode(String updateUserCode) {
    this.updateUserCode = updateUserCode;
  }
  
  public String getUpdateUserCode() {
    return this.updateUserCode;
  }
  
  public void setUpdateUsername(String updateUsername) {
    this.updateUsername = updateUsername;
  }
  
  public String getUpdateUsername() {
    return this.updateUsername;
  }
  
  public void setUpdateTime(Date updateTime) {
    this.updateTime = updateTime;
  }
  
  public Date getUpdateTime() {
    return this.updateTime;
  }
  
  public void setIp(String ip) {
    this.ip = ip;
  }
  
  public String getIp() {
    return this.ip;
  }
  
  public void setOperatorLongitude(Double operatorLongitude) {
    this.operatorLongitude = operatorLongitude;
  }
  
  public Double getOperatorLongitude() {
    return this.operatorLongitude;
  }
  
  public void setOperatorLatitude(Double operatorLatitude) {
    this.operatorLatitude = operatorLatitude;
  }
  
  public Double getOperatorLatitude() {
    return this.operatorLatitude;
  }
  
  public void setOwnerUserCode(String ownerUserCode) {
    this.ownerUserCode = ownerUserCode;
  }
  
  public String getOwnerUserCode() {
    return this.ownerUserCode;
  }
  
  public void setOwnerOrgCode(String ownerOrgCode) {
    this.ownerOrgCode = ownerOrgCode;
  }
  
  public String getOwnerOrgCode() {
    return this.ownerOrgCode;
  }
  
  public void setOwnerOrgName(String ownerOrgName) {
    this.ownerOrgName = ownerOrgName;
  }
  
  public String getOwnerOrgName() {
    return this.ownerOrgName;
  }
  
  public void setSynchronousId(String synchronousId) {
    this.synchronousId = synchronousId;
  }
  
  public String getSynchronousId() {
    return this.synchronousId;
  }
  
  public String getAuditDescription() {
    return this.auditDescription;
  }
  
  public void setAuditDescription(String auditDescription) {
    this.auditDescription = auditDescription;
  }

  public String getUploadStatus() {
    return uploadStatus;
  }

  public void setUploadStatus(String uploadStatus) {
    this.uploadStatus = uploadStatus;
  }
}
