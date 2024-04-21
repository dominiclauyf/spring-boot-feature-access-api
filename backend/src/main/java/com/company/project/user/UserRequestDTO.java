package com.company.project.user;

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
public class UserRequestDTO {
    @Email(regexp = ValidationConstants.EMAIL_REGEX)
    String email;
}
