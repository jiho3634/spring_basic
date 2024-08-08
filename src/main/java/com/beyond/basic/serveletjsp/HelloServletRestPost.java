package com.beyond.basic.serveletjsp;

import com.beyond.basic.domain.Hello;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

@WebServlet("/hello/servlet/rest/post")
public class HelloServletRestPost extends HttpServlet {
    @Override

    //  HttpServletRequest 를 주입받아 원하는 정보를 가져올 수 있다.
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("\n\nHeader 정보 출력\n");
        //  Header 정보 출력 => req.getHeader()
        System.out.println(req.getHeader("Accept"));
        System.out.println(req.getHeader("Host"));
        System.out.println(req.getHeader("Connection"));
        System.out.println(req.getHeader("Cookie"));

        System.out.println("\n\nBody 정보 출력\n");
        //  Body 정보 출력 => req.getReader()
        BufferedReader br = req.getReader();
        String line = null;
        String value = "";
        while ((line = br.readLine()) != null) {
            value += line;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        Hello hello = objectMapper.readValue(value, Hello.class);
        System.out.println("hello = " + hello);
    }
}
