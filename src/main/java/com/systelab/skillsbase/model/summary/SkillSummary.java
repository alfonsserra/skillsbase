package com.systelab.skillsbase.model.summary;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkillSummary {

    private Long id;
    private String text;
    private Double counter;
}
