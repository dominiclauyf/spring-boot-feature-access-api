package com.company.project.userfeature;

import javax.validation.constraints.Email;

import com.company.project.constants.ValidationConstants;

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
