package com.example.securitybase.auth.models;

import com.example.securitybase.auth.enums.TokenType;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;
import java.util.stream.Collectors;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserPrincipal implements UserDetails {
    private String clientId;
    private String username;
    private String siteCode;
    private String rmCode;
    private Long userId;
    private Long siteId;
    private List<String> scopes;
    private Collection<? extends GrantedAuthority> authorities;
    private String uuId;
    private TokenType tokenType;
    private Date expireAt;

    public UserPrincipal() {

    }

    public UserPrincipal(String clientId, String userName, String siteCode, String rmCode, Long userId, Long siteId, List<String> scopes, List<String> authorities, String uuId, TokenType tokenType, Date expireAt) {
        this.clientId = clientId;
        this.username = userName;
        this.siteCode = siteCode;
        this.rmCode = rmCode;
        this.userId = userId;
        this.siteId = siteId;
        this.scopes = scopes;
        this.authorities = authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        this.uuId = uuId;
        this.tokenType = tokenType;
        this.expireAt = expireAt;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public List<?> getBetaAuthorities() {
        return (List<?>) authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public String getSiteCode() {
        return siteCode;
    }

    public void setSiteCode(String siteCode) {
        this.siteCode = siteCode;
    }

    public String getRmCode() {
        return rmCode;
    }

    public void setRmCode(String rmCode) {
        this.rmCode = rmCode;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public List<String> getScopes() {
        return scopes;
    }

    public void setScopes(List<String> scopes) {
        this.scopes = scopes;
    }

    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    public void setAuthorities(List<String> authorities) {
        this.authorities = authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    public String getUuId() {
        return uuId;
    }

    public void setUuId(String uuId) {
        this.uuId = uuId;
    }

    public TokenType getTokenType() {
        return tokenType;
    }

    public void setTokenType(TokenType tokenType) {
        this.tokenType = tokenType;
    }

    public Date getExpireAt() {
        return expireAt;
    }

    public void setExpireAt(Date expireAt) {
        this.expireAt = expireAt;
    }
}
 