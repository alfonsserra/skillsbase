package com.systelab.skillsbase.controller;

import com.systelab.skillsbase.model.skill.Skill;
import com.systelab.skillsbase.repository.SkillNotFoundException;
import com.systelab.skillsbase.repository.SkillRepository;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@Api(value = "Skill", description = "API for Skill management", tags = {"Skill"})
@RestController()
@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "Authorization", allowCredentials = "true")
@RequestMapping(value = "/skillsbase/v1/skills", produces = MediaType.APPLICATION_JSON_VALUE)
public class SkillController {

    @Autowired
    private SkillRepository skillRepository;
    

    @ApiOperation(value = "Get all Skills", notes = "", authorizations = {@Authorization(value = "Bearer")})
    @GetMapping("")
    @PermitAll
    public ResponseEntity<List<Skill>> getAllCategories() {
        return ResponseEntity.ok(skillRepository.findAll());
    }

    @ApiOperation(value = "Get Skill", notes = "", authorizations = {@Authorization(value = "Bearer")})
    @GetMapping("/{uid}")
    public ResponseEntity<Skill> getCategory(@PathVariable("uid") Long skillId) {
        return this.skillRepository.findById(skillId).map(ResponseEntity::ok).orElseThrow(() -> new SkillNotFoundException(skillId));

    }

    @ApiOperation(value = "Create a Skill", notes = "", authorizations = {@Authorization(value = "Bearer")})
    @PostMapping("/category")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Skill> createCategory(@RequestBody @ApiParam(value = "Skill", required = true) @Valid Skill s) {
        Skill skill = this.skillRepository.save(s);

        URI uri = MvcUriComponentsBuilder.fromController(getClass()).path("/{id}").buildAndExpand(skill.getId()).toUri();
        return ResponseEntity.created(uri).body(skill);
    }

    @ApiOperation(value = "Create or Update (idempotent) an existing Skill", notes = "", authorizations = {@Authorization(value = "Bearer")})
    @PutMapping("/{uid}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Skill> updateCategory(@PathVariable("uid") Long skillId, @RequestBody @ApiParam(value = "Skill", required = true) @Valid Skill s) {
        return this.skillRepository
                .findById(skillId)
                .map(existing -> {
                    s.setId(skillId);
                    Skill Category = this.skillRepository.save(s);
                    URI selfLink = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().toUriString());
                    return ResponseEntity.created(selfLink).body(Category);
                }).orElseThrow(() -> new SkillNotFoundException(skillId));
    }


    @ApiOperation(value = "Delete a Skill", notes = "", authorizations = {@Authorization(value = "Bearer")})
    @DeleteMapping("/{uid}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<?> removeCategory(@PathVariable("uid") Long skillId) {
        return this.skillRepository.findById(skillId)
                .map(c -> {
                    skillRepository.delete(c);
                    return ResponseEntity.noContent().build();
                }).orElseThrow(() -> new SkillNotFoundException(skillId));
    }
}