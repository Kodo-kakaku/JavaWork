package ru.otus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.model.Client;
import ru.otus.services.DBServiceClient;

import java.util.List;

@Controller
public class ClientController {

    private final DBServiceClient clientService;

    public ClientController(DBServiceClient clientService) {
        this.clientService = clientService;
    }

    @GetMapping("/")
    public String clientsListView(Model model) {

        List<Client> clients = clientService.findAll();
        model.addAttribute("clients", clients);
        return "clients";
    }

    @GetMapping("/client/save")
    public String clientCreateView(Model model) {

        model.addAttribute("client", new Client());
        return "new_client";
    }

    @PostMapping("/client/save")
    public RedirectView clientSave(@ModelAttribute Client client, Model model) {

        Client savedClient = clientService.saveClient(client);
        model.addAttribute("client", savedClient);
        return new RedirectView("/", true);
    }

    @GetMapping ("/client/search")
    public String searchByName(Model model, String name) {

        List<Client> clients;

        if (name != null && !"".equals(name)) {
            clients = clientService.findByName(name);
        } else {
            clients = clientService.findAll();
        }

        model.addAttribute("clients", clients);
        return "clients";
    }
}
