package com.systelab.skillsbase.model.skill;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.systelab.skillsbase.model.user.User;
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
@ToString(exclude = {"user","skill"})

public class SkillAssessment {

    @EmbeddedId
    private SkillAssessmentId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("skillId")
    @JsonIgnore
    private Skill skill;

    private int proficiency=-1;
    private int interest=-1;


    public SkillAssessment(User user, Skill skill) {
        this.user = user;
        this.skill = skill;
        this.id = new SkillAssessmentId(user.getId(), skill.getId());
    }

}
