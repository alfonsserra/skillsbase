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
@Table(name = "category")
@ToString(exclude = {"parent"})
@EqualsAndHashCode(of = {"id"})
public class Category implements Serializable {

    @Id
    @GeneratedValue
    @ApiModelProperty(notes = "The database generated Category ID")
    private Long id;

    @Size(min = 1, max = 255)
    private String name;

    private int level = 0;


    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    List<Category> categoryList = new ArrayList<Category>();

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "PARENTCATEGORY", referencedColumnName = "id")
    private Category parent;

}