package com.proyecto.sistemas.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
public class User implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer idusers;
    private String username;
    private String password;
    private boolean enabled;
    private String nombre;
    private String correo;
    private String rrecuperacion;
    private String role;

    public User() {}

    public User(String username, String password, String nombre, String correo, String rrecuperacion, String role) {
        this.username = username;
        this.password = password;
        this.nombre = nombre;
        this.correo = correo;
        this.rrecuperacion = rrecuperacion;
        this.role = role;
        this.enabled = true;
    }

    public Integer getIdusers() {
        return idusers;
    }

    public void setIdusers(Integer idusers) {
        this.idusers = idusers;
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

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getRrecuperacion() {
        return rrecuperacion;
    }

    public void setRrecuperacion(String rrecuperacion) {
        this.rrecuperacion = rrecuperacion;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "idpusers=" + idusers +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", enabled=" + enabled +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", rrecuperacion='" + rrecuperacion + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
