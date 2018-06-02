package com.systelab.skillsbase.model.skill;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import java.io.Serializable;

@XmlRootElement
@XmlType(propOrder = {"id", "name"})

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "skill")
public class Skill implements Serializable {

    @Id
    @GeneratedValue
    @ApiModelProperty(notes = "The database generated Skill ID")
    private Long id;

    @Size(min = 1, max = 255)
    private String name;


}