package com.system.unipar.controller;

import com.system.unipar.model.Usuario;
import com.system.unipar.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private static final Logger logger = Logger.getLogger(AuthController.class.getName());

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        try {
            if (loginRequest == null || loginRequest.getCpf() == null || loginRequest.getSenha() == null) {
                return ResponseEntity.badRequest().build();
            }

            Optional<Usuario> usuarioOpt = usuarioService.findAll().stream()
                    .filter(u -> u.getCpf() != null && u.getCpf().equals(loginRequest.getCpf()))
                    .findFirst();

            if (usuarioOpt.isPresent() && usuarioOpt.get().getSenha().equals(loginRequest.getSenha())) {
                Usuario usuario = usuarioOpt.get();
                LoginResponse response = new LoginResponse(
                    usuario.getId(),
                    usuario.getNome(),
                    usuario.getCpf(),
                    usuario.isAdmin()
                );
                return ResponseEntity.ok(response);
            }

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Erro ao realizar login", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public static class LoginRequest {
        private String cpf;
        private String senha;

        public LoginRequest() {}

        public LoginRequest(String cpf, String senha) {
            this.cpf = cpf;
            this.senha = senha;
        }

        public String getCpf() { return cpf; }
        public void setCpf(String cpf) { this.cpf = cpf; }
        public String getSenha() { return senha; }
        public void setSenha(String senha) { this.senha = senha; }
    }

    public static class LoginResponse {
        private Long id;
        private String nome;
        private String cpf;
        private boolean admin;

        public LoginResponse() {}

        public LoginResponse(Long id, String nome, String cpf, boolean admin) {
            this.id = id;
            this.nome = nome;
            this.cpf = cpf;
            this.admin = admin;
        }

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getNome() { return nome; }
        public void setNome(String nome) { this.nome = nome; }
        public String getCpf() { return cpf; }
        public void setCpf(String cpf) { this.cpf = cpf; }
        public boolean isAdmin() { return admin; }
        public void setAdmin(boolean admin) { this.admin = admin; }
    }
}
