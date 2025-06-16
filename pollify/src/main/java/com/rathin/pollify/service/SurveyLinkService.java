package com.rathin.pollify.service;

import com.google.zxing.WriterException;
import com.rathin.pollify.entity.Survey;
import com.rathin.pollify.entity.SurveyLink;
import com.rathin.pollify.repository.SurveyLinkRepository;
import com.rathin.pollify.repository.SurveyRepository;
import com.rathin.pollify.util.QRCodeGenerator;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class SurveyLinkService {

    @Autowired
    private SurveyRepository surveyRepository;

    @Autowired
    private SurveyLinkRepository surveyLinkRepository;

    private static final String QR_CODE_DIR = "qrcodes/";

    public SurveyLink generateSurveyLink(Long surveyId, HttpServletRequest request) throws IOException, WriterException {
        Optional<Survey> surveyOpt = surveyRepository.findById(surveyId);
        if (surveyOpt.isEmpty()) {
            throw new IllegalArgumentException("Survey not found");
        }

        Survey survey = surveyOpt.get();

        String token = UUID.randomUUID().toString();
        String baseUrl = request.getRequestURL().toString().replace(request.getRequestURI(), "");
        String fullLink = baseUrl + "/vote/" + token;

        String fileName = "qr_" + token + ".png";
        String qrCodePath = QRCodeGenerator.generateQRCodeImage(fullLink, QR_CODE_DIR, fileName, 300, 300);
        
        SurveyLink surveyLink = new SurveyLink();
        surveyLink.setSurvey(survey);
        surveyLink.setLink(token);
        surveyLink.setQrCodePath(qrCodePath);
        surveyLink.setCreatedAt(LocalDateTime.now());

        return surveyLinkRepository.save(surveyLink);
    }
}
