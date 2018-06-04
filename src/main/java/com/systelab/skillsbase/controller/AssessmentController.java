package com.systelab.skillsbase.controller;

import com.systelab.skillsbase.model.skill.Assessment;
import com.systelab.skillsbase.model.skill.SkillAssessment;
import com.systelab.skillsbase.repository.AssessmentNotFoundException;
import com.systelab.skillsbase.repository.AssessmentRepository;
import com.systelab.skillsbase.repository.SkillRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;


@Api(value = "Assessment", description = "API for Assessment management", tags = {"Assessment"})
@RestController()
@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "Authorization", allowCredentials = "true")
@RequestMapping(value = "/skillsbase/v1/assessments", produces = MediaType.APPLICATION_JSON_VALUE)
public class AssessmentController {

    @Autowired
    private AssessmentRepository assessmentRepository;

    @Autowired
    private SkillRepository skillRepository;

    @ApiOperation(value = "Get all Assessments", notes = "", authorizations = {@Authorization(value = "Bearer")})
    @GetMapping("")
    @PermitAll
    public ResponseEntity<List<Assessment>> getAllAssessments() {
        return ResponseEntity.ok(assessmentRepository.findAll());
    }

    @ApiOperation(value = "Get Assessment", notes = "", authorizations = {@Authorization(value = "Bearer")})
    @GetMapping("/{uid}")
    public ResponseEntity<Assessment> getAssessment(@PathVariable("uid") Long assessmentId) {
        return this.assessmentRepository.findById(assessmentId).map(ResponseEntity::ok).orElseThrow(() -> new AssessmentNotFoundException(assessmentId));
    }

    @ApiOperation(value = "Get Last Assessment", notes = "", authorizations = {@Authorization(value = "Bearer")})
    @GetMapping("/last/user/{uid}")
    public ResponseEntity<Assessment> getLastAssessment(@PathVariable("uid") Long userId) {
        return this.assessmentRepository.findFirstByUserIdOrderByRealizationDate(userId).map(ResponseEntity::ok).orElseThrow(() -> new AssessmentNotFoundException(new Long(-1)));

    }

    @ApiOperation(value = "Create an Assessment", notes = "", authorizations = {@Authorization(value = "Bearer")})
    @PostMapping("/assessment")
    public ResponseEntity<Assessment> createAssessment(@RequestBody @ApiParam(value = "Assessment", required = true) @Valid Assessment s) {

        List<SkillAssessment> assessments = s.getSkillsAssessment();
        for (int i = 0; i < assessments.size(); i++) {
            assessments.get(i).setAssessment(s);
            assessments.get(i).setSkill(skillRepository.getOne(assessments.get(i).getId().getSkillId()));
        }

        Assessment createdAssessment = this.assessmentRepository.save(s);

        URI uri = MvcUriComponentsBuilder.fromController(getClass()).path("/{id}").buildAndExpand(createdAssessment.getId()).toUri();
        return ResponseEntity.created(uri).body(createdAssessment);
    }

    @ApiOperation(value = "Delete a Assessment", notes = "", authorizations = {@Authorization(value = "Bearer")})
    @DeleteMapping("/{uid}")
    public ResponseEntity<?> removeAssessment(@PathVariable("uid") Long assessmentId) {
        return this.assessmentRepository.findById(assessmentId)
                .map(c -> {
                    assessmentRepository.delete(c);
                    return ResponseEntity.noContent().build();
                }).orElseThrow(() -> new AssessmentNotFoundException(assessmentId));
    }
}