package com.systelab.skillsbase.controller;

import com.systelab.skillsbase.model.skill.Skill;
import com.systelab.skillsbase.model.summary.OrganizationSummary;
import com.systelab.skillsbase.model.summary.SkillSummary;
import com.systelab.skillsbase.model.summary.UserRate;
import com.systelab.skillsbase.model.summary.UserRateSummary;
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
import java.util.List;
import java.util.stream.Collectors;

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
        OrganizationSummary organizationSummary = new OrganizationSummary();

        List<Object[]> skills = entityManager.createQuery("SELECT e.skill,AVG(e.proficiency) AS proficiency,count(e) FROM SkillAssessment e GROUP by e.skill ORDER BY proficiency DESC").getResultList();
        List<SkillSummary> listskills = skills.stream().map(p -> new SkillSummary(((Skill) p[0]).getId(), ((Skill) p[0]).getText(), (Long) p[2], (Double) p[1])).collect(Collectors.toList());
        organizationSummary.setTopTenByProficiency(listskills);

        Object skillsAvg = entityManager.createQuery("SELECT AVG(e.proficiency) AS average FROM SkillAssessment e").getSingleResult();
        organizationSummary.setProficiency((Double) skillsAvg);

        List<Object[]> interests = entityManager.createQuery("SELECT e.skill,AVG(e.interest) AS interest,count(e) FROM SkillAssessment e GROUP by e.skill ORDER BY interest DESC").getResultList();
        List<SkillSummary> listsInterests = interests.stream().map(p -> new SkillSummary(((Skill) p[0]).getId(), ((Skill) p[0]).getText(), (Long) p[2], (Double) p[1])).collect(Collectors.toList());
        organizationSummary.setTopTenByInterest(listsInterests);

        Object interestAvg = entityManager.createQuery("SELECT AVG(e.interest) AS average FROM SkillAssessment e").getSingleResult();
        organizationSummary.setInterest((Double) interestAvg);

        return ResponseEntity.ok(organizationSummary);
    }


    @ApiOperation(value = "Get Skill Top ten users", notes = "", authorizations = {@Authorization(value = "Bearer")})
    @GetMapping("/users/skill/{uid}")
    @PermitAll
    public ResponseEntity<UserRateSummary> getUserRateSummary(@PathVariable("uid") Long skillId) {
        UserRateSummary userRateSummary = new UserRateSummary();

        List<Object[]> usersByProficiency = entityManager.createQuery("SELECT a.user,a.proficiency FROM SkillAssessment a WHERE a.skill.id=" + skillId + " ORDER BY a.proficiency DESC").getResultList();
        List<UserRate> listUsersByProficiency = usersByProficiency.stream().map(p -> new UserRate((User) p[0], (Integer) p[1])).filter(ur -> ur.getRate() > 3).collect(Collectors.toList());
        userRateSummary.setTopTenByProficiency(listUsersByProficiency);

        List<Object[]> usersByInterest = entityManager.createQuery("SELECT a.user,a.interest FROM SkillAssessment a WHERE a.skill.id=" + skillId + " ORDER BY a.interest DESC").getResultList();
        List<UserRate> listUsersByInterest = usersByInterest.stream().map(p -> new UserRate((User) p[0], (Integer) p[1])).filter(ur -> ur.getRate() > 3).collect(Collectors.toList());
        userRateSummary.setTopTenByInterest(listUsersByInterest);

        return ResponseEntity.ok(userRateSummary);
    }


}
