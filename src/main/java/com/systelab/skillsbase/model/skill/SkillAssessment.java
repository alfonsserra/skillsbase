package com.systelab.skillsbase.model.skill;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "skill_assessment")
public class SkillAssessment {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "skill_id")
    private Skill skill;

    private int rate;

}
