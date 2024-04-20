package com.example.securitybase.entity;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author caunv
 * @created_date 04/05/2021 - 9:40 AM
 */
@Entity
@Table(name = "SYS_LS_BRANCH")
public class SysLsBranch {
    private Long id;
    private String branchid;
    private String branchname;
    private Integer branchlevel;
    private String address;
    private String telephone;
    private String parentid;
    private Integer provinceid;
    private String department;
    private String fax;
    private String branchfullname;
    private String branchcode;
    private Integer areaid;
    private String istt;
    private String businessregistration;
    private String representivesigned;
    private String position;
    private String authority;

    @Id
    @GeneratedValue(generator = "SYS_LS_BRANCH_SEQ", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SYS_LS_BRANCH_SEQ", sequenceName = "SYS_LS_BRANCH_SEQ",allocationSize=1)
    @Column(name = "ID", nullable = false, precision = 0)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "BRANCHID", nullable = true, length = 50)
    public String getBranchid() {
        return branchid;
    }

    public void setBranchid(String branchid) {
        this.branchid = branchid;
    }

    @Basic
    @Column(name = "BRANCHNAME", nullable = true, length = 200)
    public String getBranchname() {
        return branchname;
    }

    public void setBranchname(String branchname) {
        this.branchname = branchname;
    }

    @Basic
    @Column(name = "BRANCHLEVEL", nullable = true, precision = 0)
    public Integer getBranchlevel() {
        return branchlevel;
    }

    public void setBranchlevel(Integer branchlevel) {
        this.branchlevel = branchlevel;
    }

    @Basic
    @Column(name = "ADDRESS", nullable = true, length = 1000)
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Basic
    @Column(name = "TELEPHONE", nullable = true, length = 100)
    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Basic
    @Column(name = "PARENTID", nullable = true, length = 50)
    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    @Basic
    @Column(name = "PROVINCEID", nullable = true, precision = 0)
    public Integer getProvinceid() {
        return provinceid;
    }

    public void setProvinceid(Integer provinceid) {
        this.provinceid = provinceid;
    }

    @Basic
    @Column(name = "DEPARTMENT", nullable = true, length = 200)
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    @Basic
    @Column(name = "FAX", nullable = true, length = 200)
    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    @Basic
    @Column(name = "BRANCHFULLNAME", nullable = true, length = 200)
    public String getBranchfullname() {
        return branchfullname;
    }

    public void setBranchfullname(String branchfullname) {
        this.branchfullname = branchfullname;
    }

    @Basic
    @Column(name = "BRANCHCODE", nullable = true, length = 20)
    public String getBranchcode() {
        return branchcode;
    }

    public void setBranchcode(String branchcode) {
        this.branchcode = branchcode;
    }

    @Basic
    @Column(name = "AREAID", nullable = true, precision = 0)
    public Integer getAreaid() {
        return areaid;
    }

    public void setAreaid(Integer areaid) {
        this.areaid = areaid;
    }

    @Basic
    @Column(name = "ISTT", nullable = true, length = 20)
    public String getIstt() {
        return istt;
    }

    public void setIstt(String istt) {
        this.istt = istt;
    }

    @Basic
    @Column(name = "BUSINESSREGISTRATION", nullable = true, length = 2000)
    public String getBusinessregistration() {
        return businessregistration;
    }

    public void setBusinessregistration(String businessregistration) {
        this.businessregistration = businessregistration;
    }

    @Basic
    @Column(name = "REPRESENTIVESIGNED", nullable = true, length = 500)
    public String getRepresentivesigned() {
        return representivesigned;
    }

    public void setRepresentivesigned(String representivesigned) {
        this.representivesigned = representivesigned;
    }

    @Basic
    @Column(name = "POSITION", nullable = true, length = 500)
    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    @Basic
    @Column(name = "AUTHORITY", nullable = true, length = 500)
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysLsBranch that = (SysLsBranch) o;
        return Objects.equals(id, that.id) && Objects.equals(branchid, that.branchid) && Objects.equals(branchname, that.branchname) && Objects.equals(branchlevel, that.branchlevel) && Objects.equals(address, that.address) && Objects.equals(telephone, that.telephone) && Objects.equals(parentid, that.parentid) && Objects.equals(provinceid, that.provinceid) && Objects.equals(department, that.department) && Objects.equals(fax, that.fax) && Objects.equals(branchfullname, that.branchfullname) && Objects.equals(branchcode, that.branchcode) && Objects.equals(areaid, that.areaid) && Objects.equals(istt, that.istt) && Objects.equals(businessregistration, that.businessregistration) && Objects.equals(representivesigned, that.representivesigned) && Objects.equals(position, that.position) && Objects.equals(authority, that.authority);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, branchid, branchname, branchlevel, address, telephone, parentid, provinceid, department, fax, branchfullname, branchcode, areaid, istt, businessregistration, representivesigned, position, authority);
    }
}
