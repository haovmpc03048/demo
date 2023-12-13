package com.fpoly.duantotnghiep.service;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.InputStreamContent;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoSnippet;
import com.google.api.services.youtube.model.VideoStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Service
public class YouTubeService {

    private static final String CLIENT_ID = "324471727626-jre1didbd4843anl6q1cv9i9fohclqf6.apps.googleusercontent.com"; // Replace
                                                                                                                        // with
                                                                                                                        // your
                                                                                                                        // Client
                                                                                                                        // ID
    private static final String CLIENT_SECRET = "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"; // Replace with your Client
                                                                                       // Secret
    private static final String REDIRECT_URI = "http://localhost:8080/oauth2callback"; // Replace with your Redirect URI

                                                                                         // URI

    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();

    private YouTube youTube;
    private String authorizationCode;

    @Autowired
    CookieService cookieService;

    public YouTubeService() throws GeneralSecurityException, IOException {
        youTube = getYouTubeService();
    }

    public String getAuthorizationUrl() {
        GoogleAuthorizationCodeFlow flow = null;
        try {
            flow = new GoogleAuthorizationCodeFlow.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    JSON_FACTORY,
                    CLIENT_ID,
                    CLIENT_SECRET,
                    Collections.singleton("https://www.googleapis.com/auth/youtube.upload"))
                    .setAccessType("offline")
                    .setApprovalPrompt("force")
                    .build();
        } catch (Exception e) {
            // Handle exception
        }

        return flow.newAuthorizationUrl().setRedirectUri(REDIRECT_URI).build();
    }

    

    public String getAccessToken(String authorizationCode) throws IOException {
        GoogleAuthorizationCodeFlow flow = null;
        try {
            flow = new GoogleAuthorizationCodeFlow.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    JSON_FACTORY,
                    CLIENT_ID,
                    CLIENT_SECRET,
                    Collections.singleton("https://www.googleapis.com/auth/youtube.upload"))
                    .setAccessType("offline")
                    .setApprovalPrompt("force")
                    .build();
        } catch (Exception e) {
            // Handle exception
        }

        GoogleTokenResponse tokenResponse = flow.newTokenRequest(authorizationCode)
                .setRedirectUri(REDIRECT_URI)
                .execute();

        return tokenResponse.getAccessToken();
    }

    public void uploadVideo(String title, String description, String privacyStatus, MultipartFile file,
            String accessToken)
            throws IOException {
        try {
            Credential credential = new GoogleCredential().setAccessToken(accessToken);

            YouTube youTube = new YouTube.Builder(
                    GoogleNetHttpTransport.newTrustedTransport(),
                    JSON_FACTORY,
                    credential)
                    .setApplicationName("My Spring Boot Application")
                    .build();

            Video video = new Video();
            VideoSnippet snippet = new VideoSnippet();
            snippet.setTitle(title);
            snippet.setDescription(description);
            video.setSnippet(snippet);
            VideoStatus status = new VideoStatus();
            status.setPrivacyStatus(privacyStatus);
            video.setStatus(status);

            InputStreamContent mediaContent = new InputStreamContent("video/*", file.getInputStream());

            YouTube.Videos.Insert request = youTube.videos().insert("snippet,status", video, mediaContent);
            Video response = request.execute();
            cookieService.add("videoId", response.getId(), 1);
            cookieService.add("videoTitle", response.getSnippet().getTitle(), 1);
            System.out.println(response);
        } catch (Exception e) {
            // Xử lý các ngoại lệ
            System.out.println(e.getMessage());
        }
    }

    private YouTube getYouTubeService() throws GeneralSecurityException, IOException {
        return new YouTube.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                null)
                .setApplicationName("My Spring Boot Application")
                .build();
    }
}
