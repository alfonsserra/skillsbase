package com.systelab.skillsbase.controller;

import com.systelab.skillsbase.model.skill.Skill;
import com.systelab.skillsbase.model.summary.OrganizationSummary;
import com.systelab.skillsbase.model.summary.SkillSummary;
import com.systelab.skillsbase.model.summary.UserSummary;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.PermitAll;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Api(value = "Summary", description = "API for Summary management", tags = {"Summary"})
@RestController()
@CrossOrigin(origins = "*", allowedHeaders = "*", exposedHeaders = "Authorization", allowCredentials = "true")
@RequestMapping(value = "/skillsbase/v1/summary", produces = MediaType.APPLICATION_JSON_VALUE)
public class SummaryController {

    @PersistenceContext
    private EntityManager entityManager;

    @ApiOperation(value = "Get Organization Summary", notes = "", authorizations = {@Authorization(value = "Bearer")})
    @GetMapping("/organization")
    @PermitAll
    public ResponseEntity<OrganizationSummary> getGeneralSummary() {
        OrganizationSummary organizationSummary=new OrganizationSummary();

        List<SkillSummary> listskills=new ArrayList<>();
        List<Object[]> skills = entityManager.createQuery("SELECT e.skill,AVG(e.proficiency),count(e) AS average FROM SkillAssessment e GROUP by e.skill ORDER BY average DESC").getResultList();
        for (Object[] p : skills) {
            Skill skill=(Skill)p[0];
            listskills.add(new SkillSummary(skill.getId(),skill.getText(),(Long)p[2],(Double)p[1]));
        }
        organizationSummary.setTopTenSkills(listskills);

        Object skillsAvg = entityManager.createQuery("SELECT AVG(e.proficiency) AS average FROM SkillAssessment e").getSingleResult();
        organizationSummary.setProficiency((Double)skillsAvg);

        List<SkillSummary> listsInterests=new ArrayList<>();
        List<Object[]> interests = entityManager.createQuery("SELECT e.skill,AVG(e.interest) AS interest,count(e) FROM SkillAssessment e GROUP by e.skill ORDER BY interest DESC").getResultList();
        for (Object[] p : interests) {
            Skill skill=(Skill)p[0];
            listsInterests.add(new SkillSummary(skill.getId(),skill.getText(),(Long)p[2],(Double)p[1]));
        }
        organizationSummary.setTopTenInterests(listsInterests);

        Object interestAvg = entityManager.createQuery("SELECT AVG(e.interest) AS average FROM SkillAssessment e").getSingleResult();
        organizationSummary.setInterest((Double)interestAvg);

        return ResponseEntity.ok(organizationSummary);
    }

    @ApiOperation(value = "Get User Summary", notes = "", authorizations = {@Authorization(value = "Bearer")})
    @GetMapping("/user/{uid}")
    @PermitAll
    public ResponseEntity<UserSummary> getUserSummary(@PathVariable("uid") Long userId) {
        UserSummary userSummary=new UserSummary();

        List<SkillSummary> listskills=new ArrayList<>();
        List<Object[]> skills = entityManager.createQuery("SELECT e.skill,AVG(e.proficiency) AS average FROM SkillAssessment e GROUP by e.skill ORDER BY average DESC").getResultList();
        for (Object[] p : skills) {
            Skill skill=(Skill)p[0];
            listskills.add(new SkillSummary(skill.getId(),skill.getText(),1l,(Double)p[1]));
        }
        userSummary.setTopTenSkills(listskills);

        Object skillsAvg = entityManager.createQuery("SELECT AVG(e.proficiency) AS average FROM SkillAssessment e").getSingleResult();
        userSummary.setProficiency((Double)skillsAvg);

        List<SkillSummary> listsInterests=new ArrayList<>();
        List<Object[]> interests = entityManager.createQuery("SELECT e.skill,AVG(e.interest) AS interest FROM SkillAssessment e GROUP by e.skill ORDER BY interest DESC").getResultList();
        for (Object[] p : interests) {
            Skill skill=(Skill)p[0];
            listsInterests.add(new SkillSummary(skill.getId(),skill.getText(),1l,(Double)p[1]));
        }
        userSummary.setTopTenInterests(listsInterests);

        Object interestAvg = entityManager.createQuery("SELECT AVG(e.interest) AS average FROM SkillAssessment e").getSingleResult();
        userSummary.setInterest((Double)interestAvg);

        return ResponseEntity.ok(userSummary);
    }

}
