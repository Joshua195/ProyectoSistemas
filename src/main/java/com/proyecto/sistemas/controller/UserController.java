package com.proyecto.sistemas.controller;

import com.proyecto.sistemas.model.*;
//import com.proyecto.sistemas.model.Venta;
import com.proyecto.sistemas.repository.*;
//import com.proyecto.sistemas.repository.VentasRepository;
import com.proyecto.sistemas.utils.CryptMD5;
import com.proyecto.sistemas.utils.SendMail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.*;

@SuppressWarnings({"Duplicates", "unchecked"})
@Controller
public class UserController {

    @Autowired
    private UserRepository repository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private PERepository peRepository;
    @Autowired
    private EnvioRepository envioRepository;
    @Autowired
    private CompraRepository compraRepository;
    @Autowired
    private CPRepository cpRepository;

    private ArrayList<String> sesionesActivas = new ArrayList<>();

    private Map<Integer,String> mapCapcha = new HashMap<>();

    public UserController(){
        mapCapcha.put(0,"8a400");
        mapCapcha.put(1,"82b3d");
        mapCapcha.put(2,"37692");
        mapCapcha.put(3,"2cb8b");
        mapCapcha.put(4,"d06ee");
        mapCapcha.put(5,"41aa7");
        mapCapcha.put(6,"2d4d0");
        mapCapcha.put(7,"1822e");
        mapCapcha.put(8,"d078c");
        mapCapcha.put(9,"35eca");
        mapCapcha.put(10,"5c0ac");
        mapCapcha.put(11,"1f3ff");
        mapCapcha.put(12,"3be54");
        mapCapcha.put(13,"232db");
        mapCapcha.put(14,"b718e");
    }

    @PostMapping("/check-user")
    public String checkUser(String username, String password, String capcha, HttpServletRequest request, boolean remember,
                            HttpServletResponse response, String color, String font){
        if (remember){
            saveCookie("username",username,(60*60*24),response);
            saveCookie("color", color,(60*60*24), response);
            saveCookie("font" ,font, (60*60*24), response);
        }
        Integer noCapcha = (Integer) request.getSession().getAttribute("capcha");
        String capchaLocal = mapCapcha.get(noCapcha);
        if (!capchaLocal.equals(capcha)){
            int capchaEnviar = new Random().nextInt(15);
            request.getSession().setAttribute("capcha", capchaEnviar);
            request.setAttribute("capcha", capchaEnviar);
            request.setAttribute("capchaError" , "capchaError");
            return "login";
        }
        String crytpPassword = new CryptMD5().cryptMD5(password);
        Integer intentos = 0;
        ArrayList<User> all = (ArrayList<User>) repository.findAll();
        boolean band = false;
        for (User user : all){
            HttpSession httpSession = request.getSession();
            intentos = (Integer) httpSession.getAttribute("intento");
            if (intentos != null && intentos >= 3){
                invalidaUsuario(username, crytpPassword);
            }
            if (user.getUsername().equals(username) && user.getPassword().equals(crytpPassword)) {
                if (user.isEnabled()) {
                    if (intentos != null) {
                        band = true;
                        httpSession.setAttribute("user", user);
                        request.setAttribute("user", user);
                        if (user.getRole().equals("ADMIN")) {
                            sesionesActivas.add(request.getSession().getId());
                            httpSession.setAttribute("intento", 0);
                            return "administracion";
                        } else if (user.getRole().equals("USER")) {
                            sesionesActivas.add(request.getSession().getId());
                            httpSession.setAttribute("intento", 0);
                            return "redirect:/";
                        }
                    }else {
                        band = true;
                        httpSession.setAttribute("user", user);
                        request.setAttribute("user", user);
                        if (user.getRole().equals("ADMIN")) {
                            sesionesActivas.add(request.getSession().getId());
                            httpSession.setAttribute("intento", 0);
                            return "administracion";
                        } else if (user.getRole().equals("USER")) {
                            sesionesActivas.add(request.getSession().getId());
                            httpSession.setAttribute("intento", 0);
                            return "redirect:/";
                        }
                    }
                }else {
                    return "recuperarCuenta";
                }
            }
        }
        if(!band){
            request.setAttribute("password", "fail");
            HttpSession httpSession = request.getSession();
            if (intentos != null){
                httpSession.setAttribute("intento", intentos + 1);
            }else {
                intentos = 1;
                httpSession.setAttribute("intento", intentos);
            }
            int capchaEnviar = new Random().nextInt(3);
            request.getSession().setAttribute("capcha", capchaEnviar);
            request.setAttribute("capcha", capchaEnviar);
            return "login";
        }
        return null;
    }

    @PostMapping("/new-user")
    public String createUser(String username, String password, String nombre, String correo,
                             String rrecuperacion, HttpServletRequest request){
        User user = new User();
        user.setUsername(username);
        user.setPassword(new CryptMD5().cryptMD5(password));
        user.setNombre(nombre);
        user.setCorreo(correo);
        user.setEnabled(true);
        user.setRrecuperacion(rrecuperacion);
        user.setRole("USER");
        repository.save(user);
        int capchaEnviar = new Random().nextInt(3);
        request.getSession().setAttribute("capcha", capchaEnviar);
        request.setAttribute("capcha", capchaEnviar);
        request.setAttribute("nologin", "nologin");
        return "login";
    }

    private void invalidaUsuario(String username, String pass){
        List<User> all = repository.findAll();
        for (User user : all){
            if (user.getUsername().equals(username) || user.getPassword().equals(pass)){
                user.setEnabled(false);
                repository.save(user);
                break;
            }
        }
    }

    public boolean checkSession(HttpServletRequest request){
        String sessionActive = request.getSession().getId();
        if(sesionesActivas.contains(sessionActive)){
            return true;
        }else{
            return false;
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        String idSessionActiva = request.getSession().getId();
        sesionesActivas.remove(idSessionActiva);
        request.getSession().invalidate();
        return "redirect:/";
    }

    @GetMapping("/admin")
    public String admin(HttpServletRequest httpServletRequest){
        if(!checkSession(httpServletRequest)){
            httpServletRequest.setAttribute("nologin" , "nologin");
            return "redirect:/login";
        }
        HttpSession httpSession = httpServletRequest.getSession();
        httpServletRequest.setAttribute("user", httpSession.getAttribute("user"));
        return "administracion";
    }


    @GetMapping("/carrito")
    public String carrito(HttpServletRequest request){
        if(!checkSession(request)){
            request.setAttribute("nologin" , "nologin");
            return "redirect:/login";
        }
        if (request.getSession().getAttribute("items") != null){
            List<Item> items = (List<Item>) request.getSession().getAttribute("items");
            Double total = 0.0;
            for (Item item : items){
                if (item.isDescuento()) {
                    Double desc = (item.getPrecio() * item.getCantidad()) * 0.1;
                    total += (item.getPrecio() * item.getCantidad()) - desc;
                }else {
                    total += item.getPrecio() * item.getCantidad();
                }
            }
            request.setAttribute("total" , total);
            request.getSession().setAttribute("total", total);
            request.setAttribute("items" , request.getSession().getAttribute("items"));
        }else {
            List<Item> items = new ArrayList<>();
            request.getSession().setAttribute("total", 0.0);
            request.setAttribute("total" , 0.0);
            request.setAttribute("items", items);
        }
        request.setAttribute("itemscarrito", request.getSession().getAttribute("itemscarrito"));
        request.setAttribute("user", request.getSession().getAttribute("user"));
        return "carrito";
    }

    @GetMapping("/additem")
    public String addItem(Integer id, HttpServletRequest request, boolean test){
        if(!checkSession(request)){
            request.setAttribute("nologin" , "nologin");
            return "redirect:/login";
        }
        Producto producto = productoRepository.findOne(id);
        PE pe = getPE(id);
        if (request.getSession().getAttribute("items") != null){
            List<Item> items = (List<Item>) request.getSession().getAttribute("items");
            boolean nuevoItem = true;
            for (Item item : items){
                if (item.getIdproducto() == producto.getIdproducto()){
                    Integer existenciaActual = pe.getExistencia() - (item.getCantidad() + 1);
                    if (existenciaActual > 0) {
                        item.setCantidad(item.getCantidad() + 1);
                        nuevoItem = false;
                    }else {
                        request.getSession().setAttribute("noexistencia", "noexistencia");
                        return "redirect:/producto?id=" + producto.getIdproducto();
                    }
                }
            }
            if (nuevoItem){
                Integer existenciaActual = pe.getExistencia() - 1;
                if (existenciaActual > 0) {
                    Item item = new Item(producto.getIdproducto(), producto.getNombre(), producto.getCategoria(), 1,
                            producto.getPrecio(), producto.getDescripcion());
                    item.setDescuento(test);
                    items.add(item);
                }else {
                    request.getSession().setAttribute("noexistencia", "noexistencia");
                    return "redirect:/producto?id=" + producto.getIdproducto();
                }
            }
            request.getSession().setAttribute("items", items);
            request.getSession().setAttribute("itemscarrito", items.size());
        }else {
            List<Item> items = new ArrayList<>();
            Integer existenciaActual = pe.getExistencia() - 1;
            if (existenciaActual > 0) {
                Item item = new Item(producto.getIdproducto(), producto.getNombre(), producto.getCategoria(), 1,
                        producto.getPrecio(), producto.getDescripcion());
                item.setDescuento(test);
                items.add(item);
            }else {
                request.getSession().setAttribute("noexistencia", "noexistencia");
                return "redirect:/producto?id=" + producto.getIdproducto();
            }
            request.getSession().setAttribute("items", items);
            request.getSession().setAttribute("itemscarrito", items.size());
        }
        request.getSession().setAttribute("itemadd", "itemadd");
        return "redirect:/producto?id=" + producto.getIdproducto();
    }

    @GetMapping("/deleteitem")
    public String deleteItem(Integer id, HttpServletRequest request){
        if(!checkSession(request)){
            request.setAttribute("nologin" , "nologin");
            return "redirect:/login";
        }
        List<Item> items = (List<Item>) request.getSession().getAttribute("items");
        boolean seElimina = true;
        Item itemAEliminar = null;
        for (Item item : items){
            if (item.getIdproducto() == id){
                if (item.getCantidad() > 1){
                    item.setCantidad(item.getCantidad() - 1);
                    break;
                }else{
                    itemAEliminar = item;
                    break;
                }
            }
        }
        items.remove(itemAEliminar);
        request.getSession().setAttribute("items", items);
        request.getSession().setAttribute("itemscarrito", items.size());
        return "redirect:/carrito";
    }

    @GetMapping("/proceso-compra")
    public String procesoCompra(HttpServletRequest request){
        if(!checkSession(request)){
            request.setAttribute("nologin" , "nologin");
            return "redirect:/login";
        }
        request.setAttribute("user", request.getSession().getAttribute("user"));
        request.setAttribute("subtotal", request.getSession().getAttribute("total"));
        request.setAttribute("itemscarrito", request.getSession().getAttribute("itemscarrito"));
        return "metodo-pagov2";
    }

    @PostMapping("/proceso-pago")
    public String procesoPago(HttpServletRequest request, Double iva, String cuponFinal, Double gastosenvio, Double totalFinal,
                              String calle, Integer number, String colonia, String city, Integer postal, String country,
                              String phone, Integer noTargeta){
        if(!checkSession(request)){
            request.setAttribute("nologin" , "nologin");
            return "redirect:/login";
        }
        List<Item> items = (List<Item>) request.getSession().getAttribute("items");
        Double subtotal = (Double) request.getSession().getAttribute("total");
        User user = (User) request.getSession().getAttribute("user");
        Envio envio = new Envio(calle,colonia,number,city,country,postal,phone);
        envioRepository.save(envio);
        LocalDate localDate = LocalDate.now();
        Compra compra = new Compra(localDate,totalFinal,envio.getIdenvio(),user.getIdusers());
        compraRepository.save(compra);
        for (Item item : items){
            CP cp = new CP(compra.getIdcompras(),item.getIdproducto());
            cpRepository.save(cp);
            PE pe = getPE(item.getIdproducto());
            Integer existencia = pe.getExistencia() - item.getCantidad();
            pe.setExistencia(existencia);
            peRepository.save(pe);
        }
        request.getSession().setAttribute("items", null);
        request.getSession().setAttribute("itemscarrito", null);
        return "redirect:/";
    }

    @GetMapping("/forgotPassword")
    public String renderForgotPassword(){
        return "forgetPass";
    }

    @PostMapping("/forgotPassword")
    public String forgotPassword(String correo, String rrecuperacion){
        List<User> all = repository.findAll();
        boolean noConcide = true;
        for (User user : all){
            if (user.getCorreo().equals(correo) && user.getRrecuperacion().equals(rrecuperacion)){
                String newPassword = generatePassword(10);
                String mensaje = "Querido " + user.getNombre() + ":\n\nSe ha solicitado recupercion de contraseña" +
                        "\nNueva Contraseña: " + newPassword + ".\n\nAttn: CompuCom";
                SendMail sendMail = new SendMail(mensaje,"Recuperacion de Contraseña - CompuCom", user.getCorreo());
                sendMail.send();
                user.setPassword(new CryptMD5().cryptMD5(newPassword));
                repository.save(user);
                noConcide = false;
                break;
            }
        }
        if (noConcide){
            String mensaje = "Querido Usuario:'\n\n\nNo se pudo completar el proceso de de recuperacion de contraseña" +
                    "\n\nCausa: Respuesta de recuperacion erronea";
            SendMail sendMail = new SendMail(mensaje,"Recuperacion de Contraseña - CompuCom", correo);
            sendMail.send();
        }
        return "redirect:/";
    }

    private String generatePassword(int numberChar){
        char[] word = new char[numberChar];
        for (int i = 0; i < word.length; i++){
            word[i] = (char) ('a' + new Random().nextInt(26));
        }
        return new String(word);
    }

    @GetMapping("/recuperarCuenta")
    public String renderRecuperarCuenta(){
        return "recuperarCuenta";
    }

    @PostMapping("/recuperarCuenta")
    public String recuperarCuenta(String correo, String rrecuperacion) {
        List<User> all = repository.findAll();
        boolean noConcide = true;
        for (User user : all) {
            if (user.getCorreo().equals(correo) && user.getRrecuperacion().equals(rrecuperacion)) {
                user.setEnabled(true);
                repository.save(user);
                noConcide = false;
                break;
            }
        }
        if (noConcide) {

        }
        return "redirect:/";
    }

    @GetMapping("/estadisticas")
    public String estadisticas(HttpServletRequest request){
        if(!checkSession(request)){
            request.setAttribute("nologin" , "nologin");
            return "redirect:/login";
        }
        /*Venta ventasDD = ventasRepository.findOne(1);
        Venta ventasTeclado = ventasRepository.findOne(2);
        Venta ventasMonitor = ventasRepository.findOne(3);
        Venta ventasMouse = ventasRepository.findOne(4);
        request.setAttribute("dd", ventasDD);
        request.setAttribute("teclado", ventasTeclado);
        request.setAttribute("monitor", ventasMonitor);
        request.setAttribute("mouse", ventasMouse);*/
        return "estadisticas";
    }

    @GetMapping("/historial")
    public String historial(HttpServletRequest request){
        if(!checkSession(request)){
            request.setAttribute("nologin" , "nologin");
            return "redirect:/login";
        }
        return "historial";
    }

    public static void saveCookie(String cookieName, String value, int maxAge, HttpServletResponse response) {
        Cookie cookie = new Cookie(cookieName, value);
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);
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
