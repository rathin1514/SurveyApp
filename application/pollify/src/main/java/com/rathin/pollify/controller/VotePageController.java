package com.rathin.pollify.controller;

import com.rathin.pollify.entity.SurveyLink;
import com.rathin.pollify.service.SurveyLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class VotePageController {

    @Autowired
    private SurveyLinkService surveyLinkService;

    @GetMapping("/vote/{token}")
    public String redirectToVotePage(@PathVariable String token) {
        SurveyLink link = surveyLinkService.findByToken(token);
        if (link == null) {
            return "redirect:/404.html"; // Optional: static 404 page
        }

        Long surveyId = link.getSurvey().getId();
        return "redirect:/vote.html?id=" + surveyId;
    }
}
