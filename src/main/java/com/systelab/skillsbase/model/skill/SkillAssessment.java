package com.systelab.skillsbase.model.skill;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "skill_assessment")
@ToString(exclude = {"assessment","skill"})

public class SkillAssessment {

    @EmbeddedId
    private SkillAssessmentId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("assessmentId")
    @JsonIgnore
    private Assessment assessment;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("skillId")
    @JsonIgnore
    private Skill skill;

    private int rate;

    public SkillAssessment(Assessment assessment, Skill skill) {
        this.assessment = assessment;
        this.skill = skill;
        this.id = new SkillAssessmentId(assessment.getId(), skill.getId());
    }

}
