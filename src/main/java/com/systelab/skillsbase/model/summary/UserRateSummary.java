package com.systelab.skillsbase.model.summary;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRateSummary {

    private List<UserRate> topTenByProficiency;
    private List<UserRate> topTenByInterest;

}
