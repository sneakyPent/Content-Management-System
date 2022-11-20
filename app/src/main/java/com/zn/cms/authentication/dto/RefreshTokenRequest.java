package com.zn.cms.authentication.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenRequest implements Serializable {

	@NotBlank
	private String refreshToken;
}
