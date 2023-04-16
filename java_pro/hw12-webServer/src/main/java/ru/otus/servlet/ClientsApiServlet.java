package ru.otus.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import ru.otus.crm.model.Client;
import ru.otus.crm.service.DBServiceClient;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;


public class ClientsApiServlet extends HttpServlet {

    private final DBServiceClient serviceClient;

    public ClientsApiServlet(DBServiceClient serviceClient) {
        this.serviceClient = serviceClient;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ObjectMapper mapper = new ObjectMapper();
        Client client = mapper.readValue(request.getInputStream(),Client.class);
        serviceClient.saveClient(client);
        response.sendRedirect("/clients");
    }
}
