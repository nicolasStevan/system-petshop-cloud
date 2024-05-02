package com.petshopsystemfirebase.petshopsystemfirebasedemo.controller;

import com.petshopsystemfirebase.petshopsystemfirebasedemo.entity.Cliente;
import com.petshopsystemfirebase.petshopsystemfirebasedemo.service.AnimalService;
import com.petshopsystemfirebase.petshopsystemfirebasedemo.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;

    @PostMapping("/clienteAdd")
    public String saveClient(@RequestBody Cliente cliente) throws ExecutionException, InterruptedException{
        return clienteService.saveCliente(cliente);
    }
}
