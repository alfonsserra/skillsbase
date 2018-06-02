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
import java.util.ArrayList;
import java.util.List;

@XmlRootElement
@XmlType(propOrder = {"id", "name"})

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "category")
public class Category implements Serializable {

    @Id
    @GeneratedValue
    @ApiModelProperty(notes = "The database generated Category ID")
    private Long id;

    @Size(min = 1, max = 255)
    private String name;

    private int level=0;


    @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
    List<Category > categoryList = new ArrayList<Category>();

    @ManyToOne
    @JoinColumn(name = "PARENTCATEGORY")
    private Category parent;

}