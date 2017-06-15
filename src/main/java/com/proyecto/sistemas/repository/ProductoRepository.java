package com.proyecto.sistemas.repository;


import com.proyecto.sistemas.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto,Integer>{
}
