package com.systelab.skillsbase.model.summary;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationSummary {

    private Double proficiency;
    private Double interest;

    private List<SkillSummary> topTenSkills;
    private List<SkillSummary> topTenInterests;

}
