package com.proyecto.sistemas.controller;

import com.proyecto.sistemas.model.User;
import com.proyecto.sistemas.repository.UserRepository;
import com.proyecto.sistemas.utils.CryptMD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(name = "/api/users")
public class UserRestController {

    @Autowired
    private UserRepository repository;

    @GetMapping("/api/users")
    public List<User> users(){
        return repository.findAll();
    }

    @PostMapping("/api/users")
    public String createUser(String username, String password, String nombre, String correo, String rrecuperacion, String role){
        String crypPassword = new CryptMD5().cryptMD5(password);
        User user = new User(username,crypPassword,nombre,correo,rrecuperacion,role);
        repository.save(user);
        return "success";
    }

    @PutMapping("/api/users")
    public String updateUser(Integer id,String username, String password, String nombre, String correo,
                             String rrecuperacion, String role, boolean enabled){
        User user = repository.findOne(id);
        user.setUsername(username);
        user.setPassword(new CryptMD5().cryptMD5(password));
        user.setNombre(nombre);
        user.setCorreo(correo);
        user.setRrecuperacion(rrecuperacion);
        user.setRole(role);
        user.setEnabled(enabled);
        repository.save(user);
        return "success";
    }

    @DeleteMapping("/api/users")
    public String deleteUser(Integer id){
        repository.delete(id);
        return "success";
    }
}
