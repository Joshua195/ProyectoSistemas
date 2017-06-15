package com.proyecto.sistemas.repository;

import com.proyecto.sistemas.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VentasRepository extends JpaRepository<Venta,Integer>{}
