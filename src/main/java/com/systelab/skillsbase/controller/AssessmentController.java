package com.systelab.skillsbase.controller;

import com.systelab.skillsbase.model.skill.Assessment;
import com.systelab.skillsbase.repository.AssessmentRepository;
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


    @ApiOperation(value = "Get all Assessments", notes = "", authorizations = {@Authorization(value = "Bearer")})
    @GetMapping("")
    @PermitAll
    public ResponseEntity<List<Assessment>> getAllAssessments() {
        return ResponseEntity.ok(assessmentRepository.findAll());
    }

    @ApiOperation(value = "Create an Assessment", notes = "", authorizations = {@Authorization(value = "Bearer")})
    @PostMapping("/assessment")
    public ResponseEntity<Assessment> createAssessment(@RequestBody @ApiParam(value = "Assessment", required = true) @Valid Assessment s) {
        Assessment assessment = this.assessmentRepository.save(s);

        URI uri = MvcUriComponentsBuilder.fromController(getClass()).path("/{id}").buildAndExpand(assessment.getId()).toUri();
        return ResponseEntity.created(uri).body(assessment);
    }
}