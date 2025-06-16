package com.rathin.pollify.controller;

import com.google.zxing.WriterException;
import com.rathin.pollify.entity.SurveyLink;
import com.rathin.pollify.service.SurveyLinkService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/generate/{surveyId}")
    public ResponseEntity<?> generateSurveyLink(@PathVariable Long surveyId, HttpServletRequest request) {
        try {
            SurveyLink surveyLink = surveyLinkService.generateSurveyLink(surveyId, request);
            String votingUrl = request.getRequestURL().toString()
                    .replace(request.getRequestURI(), "")
                    + "/vote/" + surveyLink.getLink();

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
}