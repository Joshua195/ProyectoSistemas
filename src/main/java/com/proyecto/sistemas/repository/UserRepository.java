package com.proyecto.sistemas.repository;

import com.proyecto.sistemas.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Integer>{}
