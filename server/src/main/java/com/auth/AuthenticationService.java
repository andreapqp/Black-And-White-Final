package com.auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.config.JwtService;
import com.dipendente.DipendenteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@Service
@RequiredArgsConstructor
@Slf4j 
public class AuthenticationService {
  private final DipendenteRepository repository;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  
  public AuthenticationResponse authenticate(AuthenticationRequest request) {
    log.info("Attempting authentication");
    authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            request.getUsername(),
            request.getPassword()
        )
    );
    var user = repository.findByUsername(request.getUsername())
        .orElseThrow(() -> new RuntimeException("Utente non trovato"));

        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
            .id(user.getId())
            .token(jwtToken)
            .role(user.getRole())
            .nome(user.getNome())
            .cognome(user.getCognome())
            .build();
  }  

}