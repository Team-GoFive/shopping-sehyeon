package com.kt.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@ApiResponses(
	value = {
		@ApiResponse(responseCode = "200", description = "중복 여부 반환"),
		@ApiResponse(responseCode = "400", description = "잘못된 요청"),
		@ApiResponse(responseCode = "500", description = "서버 오류")
	}
)
public abstract class SwaggerAssistance {
}
