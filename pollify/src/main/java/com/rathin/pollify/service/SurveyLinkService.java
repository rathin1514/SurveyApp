package com.rathin.pollify.service;

import com.google.zxing.WriterException;
import com.rathin.pollify.entity.Survey;
import com.rathin.pollify.entity.SurveyLink;
import com.rathin.pollify.repository.SurveyLinkRepository;
import com.rathin.pollify.repository.SurveyRepository;
import com.rathin.pollify.util.QRCodeGenerator;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class SurveyLinkService {

    private static final Logger logger = LoggerFactory.getLogger(SurveyLinkService.class);

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private SurveyLinkRepository surveyLinkRepository;

    private static final String QR_CODE_DIR = "uploads/qrcodes/";
    private static final String QR_CODE_URL_PREFIX = "/qrcodes/";

    public SurveyLink generateSurveyLink(Long surveyId, HttpServletRequest request) throws IOException, WriterException {
        Optional<Survey> surveyOpt = surveyRepository.findById(surveyId);
        if (surveyOpt.isEmpty()) {
            throw new IllegalArgumentException("Survey not found");
        }

        Survey survey = surveyOpt.get();

        Optional<SurveyLink> existingLinkOpt = surveyLinkRepository.findBySurvey_Id(surveyId);
        if (existingLinkOpt.isPresent()) {
            logger.info("SurveyLink already exists for surveyId={}", surveyId);
            return existingLinkOpt.get();
        }

        Path qrDirPath = Paths.get(QR_CODE_DIR);
        if (Files.notExists(qrDirPath)) {
            logger.info("QR code directory not found. Creating directory at {}", qrDirPath.toAbsolutePath());
            Files.createDirectories(qrDirPath);
        }

        String token = UUID.randomUUID().toString();
        String baseUrl = request.getRequestURL().toString().replace(request.getRequestURI(), "");
        String fullLink = baseUrl + "/vote/" + token;

        String fileName = "qr_" + token + ".png";
        try {
            QRCodeGenerator.generateQRCodeImage(fullLink, QR_CODE_DIR, fileName, 300, 300);
        } catch (Exception e) {
            logger.error("Failed to generate QR code for surveyId {}: {}", surveyId, e.getMessage());
            throw e;
        }
        
        String webQrCodePath = QR_CODE_URL_PREFIX.substring(1) + fileName;

        SurveyLink surveyLink = new SurveyLink();
        surveyLink.setSurvey(survey);
        surveyLink.setLink(token);
        surveyLink.setQrCodePath(webQrCodePath);
        surveyLink.setCreatedAt(LocalDateTime.now());

        SurveyLink saved = surveyLinkRepository.save(surveyLink);
        logger.info("SurveyLink saved successfully with id {} for surveyId {}", saved.getId(), surveyId);
        return saved;
    }

    public SurveyLink findBySurveyId(Long surveyId) {
        return surveyLinkRepository.findBySurvey_Id(surveyId).orElse(null);
    }
}
