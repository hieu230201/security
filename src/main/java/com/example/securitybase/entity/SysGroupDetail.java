package com.example.securitybase.entity;


import com.example.securitybase.entity.base.BaseEntity;

import javax.persistence.*;
import java.sql.Time;
import java.util.Objects;

@Entity
@Table(name = "SYS_GROUP_DETAIL")
public class SysGroupDetail extends BaseEntity {
    private Long id;
    private String branchCode;
    private String groupType;
    private String groupRule;
    private String memberRule;
    private String region;
    private Long groupId;
    private String refTinhCode;
    private String tenTinh;
    private String refHuyenCode;
    private String tenHuyen;
    private String refXaCode;
    private String tenXa;

    private String committee;

    private String maDvtt;


    private String maDvttLv1;
    private String maDvttLv2;
    private String maDvttLv3;




    @Basic
    @Id
    @GeneratedValue(generator = "SYS_GROUP_DETAIL_SEQ", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SYS_GROUP_DETAIL_SEQ", sequenceName = "SYS_GROUP_DETAIL_SEQ",allocationSize=1)
    @Column(name = "ID")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "BRANCH_CODE")
    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    @Basic
    @Column(name = "GROUP_TYPE")
    public String getGroupType() {
        return groupType;
    }

    public void setGroupType(String groupType) {
        this.groupType = groupType;
    }

    @Basic
    @Column(name = "GROUP_RULE")
    public String getGroupRule() {
        return groupRule;
    }

    public void setGroupRule(String groupRule) {
        this.groupRule = groupRule;
    }

    @Basic
    @Column(name = "MEMBER_RULE")
    public String getMemberRule() {
        return memberRule;
    }

    public void setMemberRule(String memberRule) {
        this.memberRule = memberRule;
    }

    @Basic
    @Column(name = "REGION")
    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    @Basic
    @Column(name = "GROUP_ID")
    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    @Basic
    @Column(name = "REF_TINH_CODE")
    public String getRefTinhCode() {
        return refTinhCode;
    }

    public void setRefTinhCode(String refTinhCode) {
        this.refTinhCode = refTinhCode;
    }

    @Basic
    @Column(name = "TEN_TINH")
    public String getTenTinh() {
        return tenTinh;
    }

    public void setTenTinh(String tenTinh) {
        this.tenTinh = tenTinh;
    }

    @Basic
    @Column(name = "REF_HUYEN_CODE")
    public String getRefHuyenCode() {
        return refHuyenCode;
    }

    public void setRefHuyenCode(String refHuyenCode) {
        this.refHuyenCode = refHuyenCode;
    }

    @Basic
    @Column(name = "TEN_HUYEN")
    public String getTenHuyen() {
        return tenHuyen;
    }

    public void setTenHuyen(String tenHuyen) {
        this.tenHuyen = tenHuyen;
    }

    @Basic
    @Column(name = "REF_XA_CODE")
    public String getRefXaCode() {
        return refXaCode;
    }

    public void setRefXaCode(String refXaCode) {
        this.refXaCode = refXaCode;
    }

    @Basic
    @Column(name = "TEN_XA")
    public String getTenXa() {
        return tenXa;
    }

    public void setTenXa(String tenXa) {
        this.tenXa = tenXa;
    }

    @Basic
    @Column(name = "COMMITTEE")
    public String getCommittee() {
        return committee;
    }

    public void setCommittee(String committee) {
        this.committee = committee;
    }

    @Basic
    @Column(name = "MA_DVTT")
    public String getMaDvtt() {
        return maDvtt;
    }

    public void setMaDvtt(String maDvtt) {
        this.maDvtt = maDvtt;
    }

    @Basic
    @Column(name = "MA_DVTT1")
    public String getMaDvttLv1() {
        return maDvttLv1;
    }

    public void setMaDvttLv1(String maDvttLv1) {
        this.maDvttLv1 = maDvttLv1;
    }

    @Basic
    @Column(name = "MA_DVTT2")
    public String getMaDvttLv2() {
        return maDvttLv2;
    }

    public void setMaDvttLv2(String maDvttLv2) {
        this.maDvttLv2 = maDvttLv2;
    }

    @Basic
    @Column(name = "MA_DVTT3")
    public String getMaDvttLv3() {
        return maDvttLv3;
    }

    public void setMaDvttLv3(String maDvttLv3) {
        this.maDvttLv3 = maDvttLv3;
    }
}

