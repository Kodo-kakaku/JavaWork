package ru.otus.servlet;

import ru.otus.crm.model.Client;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.services.TemplateProcessor;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClientsServlet extends HttpServlet {

    private static final String USERS_PAGE_TEMPLATE = "clients.html";
    private static final String TEMPLATE_ATTR_CLIENT_LIST = "clients";

    private final TemplateProcessor templateProcessor;
    private final DBServiceClient serviceClient;

    public ClientsServlet(TemplateProcessor templateProcessor, DBServiceClient serviceClient) {
        this.templateProcessor = templateProcessor;
        this.serviceClient = serviceClient;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
        Map<String, Object> paramsMap = new HashMap<>();
        List<Client> clients = serviceClient.findAll();
        paramsMap.put(TEMPLATE_ATTR_CLIENT_LIST, clients);

        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(USERS_PAGE_TEMPLATE, paramsMap));
    }

}
