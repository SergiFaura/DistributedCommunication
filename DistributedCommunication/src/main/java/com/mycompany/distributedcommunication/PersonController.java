package com.mycompany.distributedcommunication;

// Importa las anotaciones y clases necesarias de Spring Framework
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// Marca la clase como un controlador REST, lo que significa que manejará las solicitudes HTTP
@RestController
// Define la ruta base para las solicitudes HTTP manejadas por este controlador
@RequestMapping("/persons")
public class PersonController {

    // Inyecta una instancia de PersonRepository, que proporciona métodos para interactuar con la base de datos
    @Autowired
    private PersonRepository personRepository;

    // Maneja las solicitudes GET a /persons para obtener una lista de todas las personas
    @GetMapping
    public List<Person> getAllPersons() {
        // Llama al método findAll() del repositorio para obtener todas las personas
        return personRepository.findAll();
    }

    // Maneja las solicitudes GET a /persons/{id} para obtener una persona específica por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Person> getPersonById(@PathVariable Long id) {
        // Busca una persona por su ID
        Optional<Person> person = personRepository.findById(id);
        // Si se encuentra la persona, devuelve una respuesta HTTP 200 (OK) con la persona encontrada
        if (person.isPresent()) {
            return ResponseEntity.ok(person.get());
        } else {
            // Si no se encuentra la persona, devuelve una respuesta HTTP 404 (Not Found)
            return ResponseEntity.notFound().build();
        }
    }

    // Maneja las solicitudes POST a /persons para crear una nueva persona
    @PostMapping
    public Person createPerson(@RequestBody Person person) {
        // Guarda la nueva persona en la base de datos y la devuelve en la respuesta
        return personRepository.save(person);
    }

    // Maneja las solicitudes PUT a /persons/{id} para actualizar una persona existente
    @PutMapping("/{id}")
    public ResponseEntity<Person> updatePerson(@PathVariable Long id, @RequestBody Person personDetails) {
        // Busca una persona por su ID
        Optional<Person> person = personRepository.findById(id);
        // Si se encuentra la persona, actualiza sus detalles y la guarda
        if (person.isPresent()) {
            Person personToUpdate = person.get();
            // Actualiza el nombre y la edad de la persona con los detalles proporcionados en la solicitud
            personToUpdate.setName(personDetails.getName());
            personToUpdate.setAge(personDetails.getAge());
            // Guarda la persona actualizada y devuelve una respuesta HTTP 200 (OK)
            return ResponseEntity.ok(personRepository.save(personToUpdate));
        } else {
            // Si no se encuentra la persona, devuelve una respuesta HTTP 404 (Not Found)
            return ResponseEntity.notFound().build();
        }
    }

    // Maneja las solicitudes DELETE a /persons/{id} para eliminar una persona por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerson(@PathVariable Long id) {
        // Busca una persona por su ID
        Optional<Person> person = personRepository.findById(id);
        // Si se encuentra la persona, la elimina de la base de datos
        if (person.isPresent()) {
            personRepository.delete(person.get());
            // Devuelve una respuesta HTTP 200 (OK) sin contenido
            return ResponseEntity.ok().build();
        } else {
            // Si no se encuentra la persona, devuelve una respuesta HTTP 404 (Not Found)
            return ResponseEntity.notFound().build();
        }
    }
}
