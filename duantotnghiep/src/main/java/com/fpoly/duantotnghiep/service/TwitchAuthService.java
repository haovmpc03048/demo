package com.fpoly.duantotnghiep.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;

@Service
public class TwitchAuthService {
    private static final String CLIENT_ID = "q3obcu40tu3jcd95s5jp461j6h1181";
    private static final String CLIENT_SECRET = "zcad5j0q77w1j912zgrkvs4wki70pp";
    private static final String REDIRECT_URI = "http://localhost:8080/oauth2callback";
    private static final String SCOPE = "user:read:email channel:manage:videos";

    @Autowired
    private CookieService cookieService;

    private RestTemplate restTemplate;

    public TwitchAuthService() {
        this.restTemplate = new RestTemplate();
    }

    public String generateAuthURL() {
        return "https://id.twitch.tv/oauth2/authorize?" +
                "client_id=" + CLIENT_ID +
                "&redirect_uri=" + REDIRECT_URI +
                "&response_type=code" +
                "&scope=" + SCOPE;
    }

    public String getAccessToken(String authCode) {
        String tokenURL = "https://id.twitch.tv/oauth2/token";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> requestBody = new LinkedMultiValueMap<>();
        requestBody.add("client_id", CLIENT_ID);
        requestBody.add("client_secret", CLIENT_SECRET);
        requestBody.add("code", authCode);
        requestBody.add("grant_type", "authorization_code");
        requestBody.add("redirect_uri", REDIRECT_URI);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.postForEntity(tokenURL, request, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            JsonObject jsonObject = JsonParser.parseString(response.getBody()).getAsJsonObject();
            String accessToken = jsonObject.get("access_token").getAsString();
            return accessToken;
        } else {
            System.out.println("Error getting access token: " + response.getBody());
            return null;
        }
    }

    public void uploadVideo(MultipartFile file, String accessToken, String broadcasterId) throws IOException {
        byte[] videoData = file.getBytes(); // Chuyển MultipartFile thành byte array

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.set("Client-Id", CLIENT_ID);
        headers.set("Authorization", "Bearer " + accessToken);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        ByteArrayResource resource = new ByteArrayResource(videoData) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename(); // Lấy tên file gốc
            }
        };

        body.add("file", resource);

        String uploadUrl = "https://api.twitch.tv/helix/clips?broadcaster_id=" + broadcasterId;

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        System.out.println(requestEntity);

        try {
            ResponseEntity<String> response = restTemplate.exchange(uploadUrl, HttpMethod.POST, requestEntity,
                    String.class);
            if (response.getStatusCode().is2xxSuccessful()) {
                // Xử lý thành công
            } else {
                System.out.println("Error uploading video: " + response);
                // Xử lý lỗi
                throw new RuntimeException("Error uploading video: " + response.getBody());
            }
        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }

    }

    public String getBroadcasterId() {
        String accessToken = cookieService.getValue("accessToken");

        if (accessToken != null && !accessToken.isEmpty()) {
            HttpHeaders headers = new HttpHeaders();
            headers.set("Client-ID", CLIENT_ID);
            headers.set("Authorization", "Bearer " + accessToken);

            HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

            String apiUrl = "https://api.twitch.tv/helix/users?login=rysanji2206";

            ResponseEntity<String> response = restTemplate.exchange(apiUrl, HttpMethod.GET, entity, String.class);

            if (response.getStatusCode() == HttpStatus.OK) {
                JsonObject jsonObject = JsonParser.parseString(response.getBody()).getAsJsonObject();
                if (jsonObject.has("data") && jsonObject.get("data").isJsonArray()
                        && jsonObject.get("data").getAsJsonArray().size() > 0) {
                    String broadcasterId = jsonObject.get("data").getAsJsonArray().get(0).getAsJsonObject().get("id")
                            .getAsString();
                    return broadcasterId;
                } else {
                    System.out.println("No data found in the response.");
                }
            } else {
                System.out.println("Error getting broadcaster ID: " + response.getBody());
            }
        } else {
            System.out.println("Access token is missing");
        }

        return null;
    }

}
