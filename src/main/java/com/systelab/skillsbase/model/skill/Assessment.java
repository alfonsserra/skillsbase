package com.systelab.skillsbase.model.skill;

import com.systelab.skillsbase.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "assessment")
public class Assessment {

    @Id
    @GeneratedValue
    private Long id;

    private Date realizationDate;

    @ManyToOne(optional = false)
    private User user;

    @OneToMany(mappedBy = "assessment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SkillAssessment> skillsAssessment = new ArrayList<SkillAssessment>();

    public void addSkill(Skill skill) {
        SkillAssessment skillAssessment = new SkillAssessment(this, skill);
        skillsAssessment.add(skillAssessment);
    }

    public void removeSkill(Skill skill) {
        for (Iterator<SkillAssessment> iterator = skillsAssessment.iterator(); iterator.hasNext(); ) {
            SkillAssessment skillAssessment = iterator.next();

            if (skillAssessment.getAssessment().equals(this) && skillAssessment.getSkill().equals(skill)) {
                iterator.remove();
                skillAssessment.setAssessment(null);
                skillAssessment.setSkill(null);
            }
        }
    }
}
