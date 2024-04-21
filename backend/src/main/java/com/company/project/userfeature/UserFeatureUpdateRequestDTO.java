package com.company.project.userfeature;

import com.company.project.constants.ValidationConstants;
import javax.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserFeatureUpdateRequestDTO {
    @Email(regexp = ValidationConstants.EMAIL_REGEX)
    String email;

    String featureName;
    boolean enable;
}
