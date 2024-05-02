package com.petshopsystemfirebase.petshopsystemfirebasedemo.controller;

import com.petshopsystemfirebase.petshopsystemfirebasedemo.entity.Animal;
import com.petshopsystemfirebase.petshopsystemfirebasedemo.service.AnimalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/api")
public class AnimalController {
    @Autowired
    private AnimalService animalService;

    @PostMapping("/animais")
    public String saveAnimal(@RequestBody Animal animal) throws ExecutionException, InterruptedException {
        return animalService.saveAnimal(animal);
    }

    @GetMapping("/animais/{id}")
    public Animal getAnimal(@PathVariable long id) throws ExecutionException, InterruptedException {
        return animalService.getAnimalDetailsById(id);
    }

    @GetMapping("/animais")
    public List<Animal> getAllAnimals() throws ExecutionException, InterruptedException {
        return animalService.getAnimalDetails();
    }

    @PutMapping("/animais/{id}")
    public String update(@PathVariable String id, @RequestBody Animal animal) throws ExecutionException, InterruptedException {
        // Defina apenas os outros campos do animal para atualização
        Animal animalToUpdate = new Animal();
        animalToUpdate.setId(Long.parseLong(id)); // Define o ID do animal
        animalToUpdate.setIdade(animal.getIdade());
        animalToUpdate.setNome(animal.getNome()); // Atualiza o nome do animal
        animalToUpdate.setPeso(animal.getPeso());
        animalToUpdate.setRaca(animal.getRaca());

        return animalService.updateAnimal(animalToUpdate);
    }

    @DeleteMapping("/animais/{id}")
    public String deleteAnimal(@PathVariable String id) throws ExecutionException, InterruptedException {
        return animalService.deleteAnimal(id);
    }
}
