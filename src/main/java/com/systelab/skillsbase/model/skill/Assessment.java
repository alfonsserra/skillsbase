package com.systelab.skillsbase.model.skill;

import com.systelab.skillsbase.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "assessment")
public class Assessment {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private User user;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SkillAssessment> skillsAssessment = new HashSet<SkillAssessment>();


}
