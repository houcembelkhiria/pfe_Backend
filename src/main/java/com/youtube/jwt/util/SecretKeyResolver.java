package com.youtube.jwt.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwsHeader;
import io.jsonwebtoken.SigningKeyResolver;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import java.security.Key;

public class SecretKeyResolver implements SigningKeyResolver {
    private final String secretKey;

    public SecretKeyResolver(String secretKey) {
        this.secretKey = secretKey;
    }

    @Override
    public Key resolveSigningKey(JwsHeader header, Claims claims) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public Key resolveSigningKey(JwsHeader header, String plaintext) {
        throw new UnsupportedOperationException("Method not supported");
    }
}
