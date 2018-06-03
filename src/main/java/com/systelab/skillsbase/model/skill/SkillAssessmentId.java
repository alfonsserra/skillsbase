package com.systelab.skillsbase.model.skill;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkillAssessmentId implements Serializable {

    private Long assessmentId;
    private Long skillId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        SkillAssessmentId that = (SkillAssessmentId) o;
        return Objects.equals(assessmentId, that.assessmentId) &&
                Objects.equals(skillId, that.skillId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(assessmentId, skillId);
    }
}
