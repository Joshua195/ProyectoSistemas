package com.proyecto.sistemas.utils;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class SendMail {

    private final String CUENTA_CORREO = "compu.com.ags@gmail.com";
    private final String CUENTA_PASSWORD = "compucomags2016";

    private String mensaje;
    private String asunto;
    private String[] to;

    public SendMail(String mensaje, String asunto, String to){
        this.mensaje = mensaje;
        this.asunto = asunto;
        String[] too = {to};
        this.to = too;
    }

    public void send(){
        String host = "smtp.gmail.com";
        Properties properties = System.getProperties();
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        properties.put("mail.smtp.starttls.enable","true");
        properties.put("mail.smtp.host",host);
        properties.put("mail.smtp.user", CUENTA_CORREO);
        properties.put("mail.smtp.password", CUENTA_PASSWORD);
        properties.put("mail.smtp.port", 587);
        properties.put("mail.smtp.auth", "true");
        Session session = Session.getDefaultInstance(properties);
        MimeMessage mimeMessage = new MimeMessage(session);
        try {
            mimeMessage.setFrom(new InternetAddress(CUENTA_CORREO));
            InternetAddress[] toAdress = new InternetAddress[to.length];
            for (int i = 0 ; i < to.length ; i++){
                toAdress[i] = new InternetAddress(to[i]);
            }

            for (int i = 0;  i < toAdress.length ;  i++){
                mimeMessage.setRecipient(Message.RecipientType.TO,toAdress[i]);
            }
            mimeMessage.setSubject(asunto);
            mimeMessage.setText(mensaje);
            Transport transport =  session.getTransport("smtp");
            transport.connect(host, CUENTA_CORREO, CUENTA_PASSWORD);
            transport.sendMessage(mimeMessage,mimeMessage.getAllRecipients());
            transport.close();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
