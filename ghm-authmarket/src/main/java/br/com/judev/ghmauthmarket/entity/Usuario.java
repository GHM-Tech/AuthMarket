package br.com.judev.ghmauthmarket.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Table(name = "tb_usuario")
@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long id;
    @Column(name = "nome", nullable = false,length = 120)
    private String nomeCompleto;
    @NotBlank
    @Column(nullable = false)
    @Size(min = 6, max = 20)
    private String senha;
    @NotBlank
    @Email
    @Column(unique = true)
    private String email;

    public Usuario(Long id, String nomeCompleto, String senha, String email) {
        this.id = id;
        this.nomeCompleto = nomeCompleto;
        this.senha = senha;
        this.email = email;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
