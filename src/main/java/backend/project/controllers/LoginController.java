package backend.project.controllers;

import backend.project.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import backend.project.dtos.DTOToken;
import backend.project.security.JwtUtilService;
import backend.project.security.SecurityUser;

@CrossOrigin(origins = "https://localhost:4200")
@RestController
@RequestMapping("/api")
public class LoginController {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    UserDetailsService userDetailsService;

    @Autowired
    private JwtUtilService jwtUtilService;


    @PostMapping("/login")
    public ResponseEntity<DTOToken> authenticate(@RequestBody User user) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getUserName(),
                        user.getPassword()));
        SecurityUser securityUser = (SecurityUser) this.userDetailsService.loadUserByUsername(
                user.getUserName());
        String jwt = jwtUtilService.generateToken(securityUser);
        return new ResponseEntity<DTOToken>(new DTOToken(jwt), HttpStatus.OK);

    }

}

