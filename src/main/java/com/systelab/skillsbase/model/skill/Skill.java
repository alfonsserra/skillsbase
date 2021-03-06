package com.systelab.skillsbase.model.skill;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@XmlType(propOrder = {"id", "text"})

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "skill")
@ToString(exclude = {"parentId"})
@EqualsAndHashCode(of = {"id"})
public class Skill implements Serializable {

    @Id
    @GeneratedValue
    @ApiModelProperty(notes = "Skill ID")
    private Long id;

    @Size(min = 1, max = 255)
    @NotNull
    private String text;

    @Enumerated(EnumType.STRING)
    @NotNull
    private SkillType type;

    @Size(max = 4200)
    private String comments;

    @Column(columnDefinition = "boolean default true")
    private boolean useInStatistics=true;

    @Column(columnDefinition = "int default -1")
    private int companyInterest=-1;


    @Column(columnDefinition = "int default 0")
    private int position = 1;

    @Column(columnDefinition = "int default 0")
    private int level = 0;


    @OneToMany(mappedBy = "parentId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @OrderBy("position ASC")
    List<Skill> children = new ArrayList<Skill>();

    @JsonIgnore
    @ManyToOne(fetch=FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinColumn(name = "parent_skill", referencedColumnName = "id",insertable=false,updatable=false)
    private Skill parentId;

}