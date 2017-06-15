package com.proyecto.sistemas.controller;

import com.proyecto.sistemas.model.Producto;
import com.proyecto.sistemas.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@SuppressWarnings("Duplicates")
@RestController
public class ProductoRestController {

    @Autowired
    private ProductoRepository repository;

    @GetMapping("api/productos")
    public List<Producto> allProducts(){
        return repository.findAll();
    }

    @PostMapping("api/productos")
    public String createProduct(String name, String categoria, Integer existencia, Double precio,
                                MultipartFile imagen,String descripcion){
        Producto producto = new Producto();
        try {
            producto.setNombre(name);
            producto.setCategoria(categoria);
//            producto.setExistencia(existencia);
            producto.setPrecio(precio);
            producto.setDescripcion(descripcion);
            producto.setImagen(imagen.getBytes());
            repository.save(producto);
            return "redirect:/allProducts";
        } catch (IOException e) {
            e.printStackTrace();
            return "internal faliure";
        }
    }

    @PutMapping("/api/productos")
    @ResponseBody
    public String updateProducto(Integer id,String name, String categoria, Integer existencia, Double precio,
                                 MultipartFile imagen, String descripcion){
        Producto producto = repository.findOne(id);
        try{
            producto.setNombre(name);
            producto.setCategoria(categoria);
//            producto.setExistencia(existencia);
            producto.setPrecio(precio);
            producto.setDescripcion(descripcion);
            producto.setImagen(imagen.getBytes());
            repository.save(producto);
            return "success";
        } catch (IOException e) {
            e.printStackTrace();
            return "internal faliure";
        }
    }

    @DeleteMapping("/api/productos")
    @ResponseBody
    public String deleteProduct(Integer idproducto){
        repository.delete(idproducto);
        return "success";
    }

}
