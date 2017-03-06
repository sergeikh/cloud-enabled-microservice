package com.example.task.service;

import io.jsonwebtoken.Jwts;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import sun.security.rsa.RSAPublicKeyImpl;

import javax.annotation.PostConstruct;
import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.PublicKey;

import static java.util.Objects.isNull;

/**
 * Extracts Tenant Id from @RequestHeader("Authorization")
 */
@Service
public class TenantExtractor {
    Resource resource = new ClassPathResource("public.cert");
    PublicKey pk = null;

    @PostConstruct
    public void initKey() {
        String publicKey = null;
        try {
            publicKey = new String(FileCopyUtils.copyToByteArray(resource.getInputStream()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        publicKey = publicKey.replace("-----BEGIN PUBLIC KEY-----", "");
        publicKey = publicKey.replace("-----END PUBLIC KEY-----", "");

        try {
            pk = new RSAPublicKeyImpl(DatatypeConverter.parseBase64Binary(publicKey));
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }
    }

    public String decodeTenantId(String token) {
        if (isNull(token))
            throw new IllegalArgumentException("Token can't be null");

        return Jwts.parser()
                .setSigningKey(pk)
                .parseClaimsJws(token.replace("Bearer ","")).getBody().get("tenantId").toString();
    }
}
