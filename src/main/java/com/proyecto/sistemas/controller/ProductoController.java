package com.proyecto.sistemas.controller;

import com.proyecto.sistemas.model.PE;
import com.proyecto.sistemas.model.Producto;
import com.proyecto.sistemas.repository.PERepository;
import com.proyecto.sistemas.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("Duplicates")
@Controller
public class ProductoController {

    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private PERepository peRepository;

    @GetMapping("/allProducts")
    public String allProducts(HttpServletRequest request){
        request.setAttribute("productos" , productoRepository.findAll());
        return "administracionAllProducts";
    }

    @GetMapping("/altaProductos")
    public String altaProductos(){
        return "adminAltaProductos";
    }

    @GetMapping("/bajaProductos")
    public String bajaProductos(Integer id, HttpServletRequest request){
        productoRepository.delete(id);
        request.setAttribute("statusProducto", "productoEliminado");
        request.setAttribute("productos" , productoRepository.findAll());
        return "administracionAllProducts";
    }

    @GetMapping("/editaProducto")
    public String editaProducto(Integer id, HttpServletRequest request){
        Producto producto = productoRepository.findOne(id);
        PE pe = getPE(id);
        request.setAttribute("producto", producto);
        request.setAttribute("exist", pe.getExistencia());
        return "adminEditaProductos";
    }

    @PostMapping("/edit-product")
    public String updateProducto(Integer id, String name, String categoria, Integer existencia, Double precio,
                                 MultipartFile imagen, String descripcion, HttpServletRequest request){
        Producto producto = productoRepository.findOne(id);
        PE pe = getPE(id);
        try{
            producto.setNombre(name);
            producto.setCategoria(categoria);
            pe.setExistencia(existencia);
            producto.setPrecio(precio);
            producto.setDescripcion(descripcion);
            if (!imagen.isEmpty()){
                producto.setImagen(imagen.getBytes());
            }
            productoRepository.save(producto);
            peRepository.save(pe);
            request.setAttribute("statusProducto", "productoEditado");
            request.setAttribute("productos" , productoRepository.findAll());
            return "administracionAllProducts";
        } catch (IOException e) {
            e.printStackTrace();
            request.setAttribute("statusProducto", "error");
            request.setAttribute("productos" , productoRepository.findAll());
            return "administracionAllProducts";
        }
    }

    @PostMapping("/new-product")
    public String createProduct(String name, String categoria, Integer existencia, Double precio,
                                MultipartFile imagen,String descripcion, HttpServletRequest request){
        Producto producto = new Producto();

        try {
            producto.setNombre(name);
            producto.setCategoria(categoria);
            producto.setPrecio(precio);
            producto.setDescripcion(descripcion);
            producto.setImagen(imagen.getBytes());
            productoRepository.save(producto);
            PE pe = new PE(producto.getIdproducto(),existencia);
            peRepository.save(pe);
            request.setAttribute("statusProducto", "productoNuevo");
            request.setAttribute("productos" , productoRepository.findAll());
            return "administracionAllProducts";
        } catch (IOException e) {
            e.printStackTrace();
            request.setAttribute("statusProducto", "error");
            request.setAttribute("productos" , productoRepository.findAll());
            return "administracionAllProducts";
        }
    }

    @GetMapping("/categoria")
    public String byCategoria(String categoria, HttpServletRequest request){
        List<Producto> allProducts = productoRepository.findAll();
        List<Producto> productoCategoria = new ArrayList<>();
        for (Producto producto : allProducts){
            if (producto.getCategoria().equals(categoria)){
                productoCategoria.add(producto);
            }
        }
        request.setAttribute("user", request.getSession().getAttribute("user"));
        request.setAttribute("categoria", categoria);
        request.setAttribute("productosCategoria", productoCategoria);
        request.setAttribute("itemscarrito", request.getSession().getAttribute("itemscarrito"));
        return "index";
    }

    @GetMapping("/producto")
    public String producto(Integer id, HttpServletRequest request){
        Producto producto = productoRepository.findOne(id);
        request.setAttribute("user", request.getSession().getAttribute("user"));
        request.setAttribute("producto" , producto);
        request.setAttribute("itemscarrito", request.getSession().getAttribute("itemscarrito"));
        if (request.getSession().getAttribute("itemadd") != null){
            request.setAttribute("itemadd", "itemadd");
            request.getSession().setAttribute("itemadd", null);
        }
        if (request.getSession().getAttribute("noexistencia") != null){
            request.setAttribute("noexistencia", "noexistencia");
            request.getSession().setAttribute("noexistencia", null);
        }
        return "individual";
    }

    @GetMapping("/productoDescuento")
    public String productoDescuento(Integer id, HttpServletRequest request){
        Producto producto = productoRepository.findOne(id);
        request.setAttribute("user", request.getSession().getAttribute("user"));
        request.setAttribute("producto" , producto);
        request.setAttribute("itemscarrito", request.getSession().getAttribute("itemscarrito"));
        if (request.getSession().getAttribute("itemadd") != null){
            request.setAttribute("itemadd", "itemadd");
            request.getSession().setAttribute("itemadd", null);
        }
        if (request.getSession().getAttribute("noexistencia") != null){
            request.setAttribute("noexistencia", "noexistencia");
            request.getSession().setAttribute("noexistencia", null);
        }
        request.setAttribute("descuento", "descuento");
        return "individual";
    }

    private PE getPE(Integer idProducto){
        List<PE> list = peRepository.findAll();
        for (PE pe : list){
            if (pe.getIdproducto() == idProducto){
                return pe;
            }
        }
        return null;
    }
}
