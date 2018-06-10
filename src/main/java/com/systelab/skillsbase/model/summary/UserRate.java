package com.systelab.skillsbase.model.summary;

import com.systelab.skillsbase.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRate {
    private User user;
    private Integer rate;
}
