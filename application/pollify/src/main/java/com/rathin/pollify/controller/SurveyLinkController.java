package com.rathin.pollify.controller;

import com.google.zxing.WriterException;
import com.rathin.pollify.entity.SurveyLink;
import com.rathin.pollify.service.SurveyLinkService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/survey-links")
public class SurveyLinkController {

    @Autowired
    private SurveyLinkService surveyLinkService;

    @Value("${app.base-url}")
    private String appBaseUrl;

    @PostMapping("/generate/{surveyId}")
    public ResponseEntity<?> generateSurveyLink(@PathVariable Long surveyId) {
        try {
            SurveyLink surveyLink = surveyLinkService.generateSurveyLink(surveyId);
            String votingUrl = appBaseUrl + "/vote/" + surveyLink.getLink();

            Map<String, Object> response = new HashMap<>();
            response.put("linkToken", surveyLink.getLink());
            response.put("votingUrl", votingUrl);
            response.put("qrCodePath", surveyLink.getQrCodePath());
            response.put("createdAt", surveyLink.getCreatedAt());

            return ResponseEntity.ok(response);

        } catch (IOException | WriterException e) {
            return ResponseEntity.status(500).body("Failed to generate QR code: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{surveyId}")
public ResponseEntity<?> getSurveyLink(@PathVariable Long surveyId, HttpServletRequest request) {
    SurveyLink link = surveyLinkService.findBySurveyId(surveyId);
    if (link == null) {
        return ResponseEntity.status(404).body("No survey link found");
    }

    String votingUrl = appBaseUrl + "/vote/" + link.getLink();

    Map<String, Object> response = new HashMap<>();
    response.put("linkToken", link.getLink());
    response.put("votingUrl", votingUrl);
    response.put("qrCodePath", "/" + link.getQrCodePath().replace("\\", "/"));
    response.put("createdAt", link.getCreatedAt());

    return ResponseEntity.ok(response);
}
}