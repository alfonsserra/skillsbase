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
import java.util.stream.Collectors;

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
    public ResponseEntity<List<SkillAssessment>> saveUserAssessment(@RequestBody @ApiParam(value = "Assessment", required = true) @Valid List<SkillAssessment> assessments, Principal principal) {
        User user = this.userRepository.findByLogin(principal.getName());

        List<SkillAssessment> list = assessments.stream().map(assessment -> {
            SkillAssessment skillAssessment = new SkillAssessment(user, skillRepository.getOne(assessment.getId().getSkillId()));
            skillAssessment.setInterest(assessment.getInterest());
            skillAssessment.setProficiency(assessment.getProficiency());
            return skillAssessment;
        }).collect(Collectors.toList());

        user.setSkillsAssessment(list);

        User savedUser = this.userRepository.save(user);
        return ResponseEntity.ok(savedUser.getSkillsAssessment());
    }

    @ApiOperation(value = "Save User Skill Assessment", notes = "", authorizations = {@Authorization(value = "Bearer")})
    @PutMapping("/skill")
    public ResponseEntity<SkillAssessment> saveUserSkillAssessment(@RequestBody @ApiParam(value = "SkillAssessment", required = true) @Valid SkillAssessment assessment, Principal principal) {
        User user = this.userRepository.findByLogin(principal.getName());
        assessment.setUser(user);
        assessment.getId().setUserId(user.getId());

        List<SkillAssessment> currentSkills = user.getSkillsAssessment();

        boolean found = false;
        for (int i = 0; i < currentSkills.size() && !found; i++) {
            if (currentSkills.get(i).getId().getSkillId().longValue() == assessment.getId().getSkillId().longValue()) {
                currentSkills.get(i).setProficiency(assessment.getProficiency());
                currentSkills.get(i).setInterest(assessment.getInterest());
                found = true;
            }
        }
        if (!found) {
            Skill skill = skillRepository.getOne(assessment.getId().getSkillId());
            SkillAssessment skillAssessment = new SkillAssessment(user, skill);
            skillAssessment.setProficiency(assessment.getProficiency());
            skillAssessment.setInterest(assessment.getInterest());
            currentSkills.add(skillAssessment);
        }

        User savedUser = this.userRepository.save(user);
        return ResponseEntity.ok(assessment);
    }
}