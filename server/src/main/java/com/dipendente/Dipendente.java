package com.dipendente;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.Set;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "dipendenti")
public class Dipendente implements UserDetails {

    @Id
    private String id;
    private String username;
    private String password;
    private String role;
    private String nome;
    private String cognome;
    private String[] lingue;
    private String[] qualifiche;

    public Dipendente(String username, String password, Role role, String nome, String cognome, Lingua[] lingue, Qualifica[] qualifiche) {
        this.username = username;
        this.password = password;
        this.role = role.name();
        this.nome = nome;
        this.cognome = cognome;
        for (int i = 0; i < lingue.length; i++) {
            this.lingue[i] = lingue[i].name();
        }

        this.qualifiche = new String[qualifiche.length];
        for (int i = 0; i < qualifiche.length; i++) {
            this.qualifiche[i] = qualifiche[i].name();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getRole() {
        return role;
    }

    public void seQualifiche(String[] qualifiche) {
        this.qualifiche = qualifiche;
    }
    public String[] getQualifiche() {
        return qualifiche;
    }

    public void setLingue(String[] lingue) {
        this.lingue = lingue;
    }
    public String[] getLingue() {
        return lingue;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
public Collection<? extends GrantedAuthority> getAuthorities() {
    SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);
    return Set.of(authority);
}


}
