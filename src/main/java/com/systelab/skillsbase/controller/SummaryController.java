package com.systelab.skillsbase.controller;

import com.systelab.skillsbase.model.skill.Skill;
import com.systelab.skillsbase.model.summary.*;
import com.systelab.skillsbase.model.user.User;
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
        List<Object[]> skills = entityManager.createQuery("SELECT e.skill,AVG(e.proficiency) AS proficiency,count(e) FROM SkillAssessment e GROUP by e.skill ORDER BY proficiency DESC").getResultList();
        for (Object[] p : skills) {
            Skill skill=(Skill)p[0];
            listskills.add(new SkillSummary(skill.getId(),skill.getText(),(Long)p[2],(Double)p[1]));
        }
        organizationSummary.setTopTenByProficiency(listskills);

        Object skillsAvg = entityManager.createQuery("SELECT AVG(e.proficiency) AS average FROM SkillAssessment e").getSingleResult();
        organizationSummary.setProficiency((Double)skillsAvg);

        List<SkillSummary> listsInterests=new ArrayList<>();
        List<Object[]> interests = entityManager.createQuery("SELECT e.skill,AVG(e.interest) AS interest,count(e) FROM SkillAssessment e GROUP by e.skill ORDER BY interest DESC").getResultList();
        for (Object[] p : interests) {
            Skill skill=(Skill)p[0];
            listsInterests.add(new SkillSummary(skill.getId(),skill.getText(),(Long)p[2],(Double)p[1]));
        }
        organizationSummary.setTopTenByInterest(listsInterests);

        Object interestAvg = entityManager.createQuery("SELECT AVG(e.interest) AS average FROM SkillAssessment e").getSingleResult();
        organizationSummary.setInterest((Double)interestAvg);

        return ResponseEntity.ok(organizationSummary);
    }


    @ApiOperation(value = "Get Skill Top ten users", notes = "", authorizations = {@Authorization(value = "Bearer")})
    @GetMapping("/users/skill/{uid}")
    @PermitAll
    public ResponseEntity<UserRateSummary> getUserRateSummary(@PathVariable("uid") Long skillId) {
        UserRateSummary userRateSummary=new UserRateSummary();

        List<UserRate> listUsersByProficiency=new ArrayList<>();
        List<Object[]> usersByProficiency = entityManager.createQuery("SELECT a.user,a.proficiency FROM SkillAssessment a WHERE a.skill.id="+skillId+" ORDER BY a.proficiency DESC").getResultList();
        for (Object[] p : usersByProficiency) {
            User user=(User)p[0];
            listUsersByProficiency.add(new UserRate(user,(Integer)p[1]));
        }
        userRateSummary.setTopTenByProficiency(listUsersByProficiency);


        List<UserRate> listUsersByInterest=new ArrayList<>();
        List<Object[]> usersByInterest = entityManager.createQuery("SELECT a.user,a.interest FROM SkillAssessment a WHERE a.skill.id="+skillId+" ORDER BY a.interest DESC").getResultList();
        for (Object[] p : usersByInterest) {
            User user=(User)p[0];
            listUsersByInterest.add(new UserRate(user,(Integer)p[1]));
        }
        userRateSummary.setTopTenByInterest(listUsersByInterest);

        return ResponseEntity.ok(userRateSummary);
    }


}
