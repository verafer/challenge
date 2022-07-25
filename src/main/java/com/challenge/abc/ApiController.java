package com.challenge.abc;

import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

	@GetMapping(value="/consulta")
	public Object consulta(@RequestParam(value = "q", defaultValue = "") String query, HttpServletResponse response) throws Throwable{
        ApiService apiService = new ApiService();
        return apiService.validateQuery(query, response);
	}
}
