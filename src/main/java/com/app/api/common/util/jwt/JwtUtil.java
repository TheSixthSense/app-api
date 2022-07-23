package com.app.api.common.util.jwt;

import com.app.api.core.exception.BizException;
import com.google.gson.Gson;

import java.util.Base64;
import java.util.Map;

public class JwtUtil {

    /**
     * Apple Secret Token 에서 email 정보 추출
     */
    @SuppressWarnings("unchecked")
    public static String getEmailFromAppleSecretToken(String secretToken) {
        String[] chunks = secretToken.split("\\.");

        if (chunks.length <= 1)
            throw BizException.withUserMessageKey("exception.user.client.secret.is.not.jwtToken").build();

        // payload base64 decode
        Base64.Decoder decoder = Base64.getUrlDecoder();
        String payload = new String(decoder.decode(chunks[1]));

        // payload to json
        Gson gson = new Gson();
        Map<String, Object> payloadMap = gson.fromJson(payload, Map.class);
        if (payloadMap.get("email") == null)
            throw BizException.withUserMessageKey("exception.user.client.secret.is.not.contain.email").build();

        return payloadMap.get("email").toString();
    }
}
