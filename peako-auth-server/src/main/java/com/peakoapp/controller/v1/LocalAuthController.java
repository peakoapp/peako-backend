package com.peakoapp.controller.v1;

import com.peakoapp.core.model.vo.LoginDetails;
import com.peakoapp.core.model.vo.SignupDetails;
import com.peakoapp.core.utils.R;
import com.peakoapp.service.AuthenticationService;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


/**
 * The {@code LocalAuthController} class provides the endpoints for the authentication services.
 *
 * @version 0.1.0
 */
@RestController
@RequestMapping(value = "/v1/auth")
public class LocalAuthController {
    private final AuthenticationService authenticationService;

    public LocalAuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @GetMapping(value = "test")
    @ResponseStatus(value = HttpStatus.OK)
    public R<?> test() {
        System.out.println("You can only enter here if you are an authenticated user.");
        Map<String, String> map = new HashMap<>();
        map.put("congrats", "You can only enter here if you are an authenticated user.");
        return R.ok(map);
    }

    @PostMapping(value = "/signin")
    @ResponseStatus(value = HttpStatus.OK)
    public R<?> signin(@Validated @RequestBody LoginDetails loginDetails) {
        return R.ok(authenticationService.signIn(loginDetails));
    }

    @PostMapping(value = "/signup")
    @ResponseStatus(value = HttpStatus.OK)
    public R<?> signup(@Validated @RequestBody SignupDetails signupDetails) {
        return R.ok(authenticationService.signUp(signupDetails));
    }
}
