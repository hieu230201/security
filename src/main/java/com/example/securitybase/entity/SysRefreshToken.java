package com.example.securitybase.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "SYS_REFRESH_TOKEN")
public class SysRefreshToken {
    private Long id;
    private String token;
    private Long userid;
    private Long expireat;

    @Id
    @GeneratedValue(generator = "SYS_REFRESH_TOKEN_SEQ", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "SYS_REFRESH_TOKEN_SEQ", sequenceName = "SYS_REFRESH_TOKEN_SEQ",allocationSize=1)
    @Column(name = "ID", unique = true, nullable = false)
    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "USERID")
    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }


    @Basic
    @Column(name = "TOKEN", length = 500)
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Basic
    @Column(name = "EXPIREAT")
    public Long getExpireat() {
        return expireat;
    }

    public void setExpireat(Long expireat) {
        this.expireat = expireat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SysRefreshToken that = (SysRefreshToken) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(token, that.token) &&
                Objects.equals(userid, that.userid) && Objects.equals(expireat, that.expireat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userid, expireat);
    }
}