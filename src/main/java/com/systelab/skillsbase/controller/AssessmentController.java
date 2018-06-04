package com.systelab.skillsbase.controller;

import com.systelab.skillsbase.model.skill.Skill;
import com.systelab.skillsbase.model.skill.SkillAssessment;
import com.systelab.skillsbase.model.user.User;
import com.systelab.skillsbase.repository.SkillRepository;
import com.systelab.skillsbase.repository.UserRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Api(value = "Assessment", description = "API for user assessment", tags = {"Assessment"})
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "Authorization", allowCredentials = "true")
@RequestMapping(value = "/skillsbase/v1/assessments", produces = MediaType.APPLICATION_JSON_VALUE)
public class AssessmentController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SkillRepository skillRepository;

    @ApiOperation(value = "Get User Assessment", notes = "", authorizations = {@Authorization(value = "Bearer")})
    @GetMapping("")
    public ResponseEntity<List<SkillAssessment>> getUserAssessment(Principal principal) {
        return ResponseEntity.ok(this.userRepository.findByLogin(principal.getName()).getSkillsAssessment());
    }

    @ApiOperation(value = "Save User Assessment", notes = "", authorizations = {@Authorization(value = "Bearer")})
    @PutMapping("")
    public ResponseEntity<List<SkillAssessment>> saveUserAssessment(@RequestBody @ApiParam(value = "Assessment", required = true) @Valid List<SkillAssessment> assessment, Principal principal) {
        User user = this.userRepository.findByLogin(principal.getName());

        List<SkillAssessment> list = new ArrayList<>();
        for (int i = 0; i < assessment.size(); i++) {
            Skill skill = skillRepository.getOne(assessment.get(i).getId().getSkillId());
            SkillAssessment skillAssessment = new SkillAssessment(user, skill);
            skillAssessment.setInterest(assessment.get(i).getInterest());
            skillAssessment.setProficiency(assessment.get(i).getProficiency());
            list.add(skillAssessment);
        }
        user.setSkillsAssessment(list);

        User savedUser = this.userRepository.save(user);
        return ResponseEntity.ok(savedUser.getSkillsAssessment());
    }
}