package com.systelab.skillsbase.model.skill;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@XmlType(propOrder = {"id", "name"})

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "skill")
@ToString(exclude = {"parent"})
@EqualsAndHashCode(of = {"id"})
public class Skill implements Serializable {

    @Id
    @GeneratedValue
    @ApiModelProperty(notes = "Skill ID")
    private Long id;

    @Size(min = 1, max = 255)
    private String name;

    private String comments;


    private int level = 0;


    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Skill> skillsList = new ArrayList<Skill>();

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PARENTSKILL", referencedColumnName = "id")
    private Skill parent;

}