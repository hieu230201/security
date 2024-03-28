package com.example.securitybase.repository.systems;

import com.example.securitybase.entity.SysRefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SysRefreshTokenRepository extends JpaRepository<SysRefreshToken, Long> {
    SysRefreshToken findByToken(String token);
    SysRefreshToken deleteByToken(String token);
}