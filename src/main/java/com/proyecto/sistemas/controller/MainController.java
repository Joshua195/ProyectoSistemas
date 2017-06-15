package com.proyecto.sistemas.controller;

import com.proyecto.sistemas.model.Item;
import com.proyecto.sistemas.model.Producto;
import com.proyecto.sistemas.repository.ProductoRepository;
import com.proyecto.sistemas.utils.SendMail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Controller
public class MainController {

    @Autowired
    private ProductoRepository productoRepository;

    @GetMapping("/")
    public String index(HttpServletRequest request){
        request.setAttribute("user", request.getSession().getAttribute("user"));
        List<Producto> productos = productoRepository.findAll();
        List<Producto> productoOferta = new ArrayList<>();
        Integer index;
        do {
            index = new Random().nextInt(productos.size());
            if (!productoOferta.contains(productos.get(index))) {
                productoOferta.add(productos.get(index));
            }
        }while (productoOferta.size() != 4);
        request.setAttribute("productosOferta", productoOferta);
        if (request.getSession().getAttribute("itemscarrito") == null){
            request.getSession().setAttribute("itemscarrito", 0);
        }
        request.setAttribute("itemscarrito", request.getSession().getAttribute("itemscarrito"));
        String color = getCookieValue("color",request);
        String font = getCookieValue("font" , request);
        request.setAttribute("color", color);
        request.setAttribute("font", font);
        return "index";
    }

    @GetMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response){
        int capcha = new Random().nextInt(15);
        request.getSession().setAttribute("capcha", capcha);
        request.setAttribute("capcha", capcha);
        String usernameCookie = getCookieValue("username",request);
        request.setAttribute("username", usernameCookie);
        return "login";
    }

    @GetMapping("/nosotros")
    public String nosotros(HttpServletRequest request){
        request.setAttribute("user", request.getSession().getAttribute("user"));
        request.setAttribute("itemscarrito", request.getSession().getAttribute("itemscarrito"));
        return "nosotros";
    }

    @GetMapping("/contacto")
    public String contacto(HttpServletRequest request){
        request.setAttribute("user", request.getSession().getAttribute("user"));
        request.setAttribute("itemscarrito", request.getSession().getAttribute("itemscarrito"));
        return "contacto";
    }

    @GetMapping("/preguntas-frecuentes")
    public String preguntasFrecuentes(HttpServletRequest request){
        request.setAttribute("user", request.getSession().getAttribute("user"));
        request.setAttribute("itemscarrito", request.getSession().getAttribute("itemscarrito"));
        return "preguntas-frecuentes";
    }

    @GetMapping("/registro")
    public String registro(){
        return "registro";
    }

    @PostMapping("/contacto")
    public String processContac(String name, String email){
        String mensaje = "El usuario \"" + name + "\" quiere contactarnos su email \"" + email + "\"";
        SendMail sendMail = new SendMail(mensaje,"Contacto","compu.com.ags@hotmail.com");
        sendMail.send();
        return "redirect:/";
    }

    public static String getCookieValue(String cookieName, HttpServletRequest request) {
        String value = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            int i = 0;
            boolean cookieExists = false;
            while (!cookieExists && i < cookies.length) {
                if (cookies[i].getName().equals(cookieName)) {
                    cookieExists = true;
                    value = cookies[i].getValue();
                } else {
                    i++;
                }
            }
        }
        return value;
    }

}
