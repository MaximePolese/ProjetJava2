package com.example.projetjava2;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Objects;

@RestController
public class CharacterController {
    private final PersonnageDao personnageDao;

    public CharacterController(PersonnageDao personnageDao) {
        this.personnageDao = personnageDao;
    }

    @GetMapping("/Personnages")
    public List<Personnage> listePersonnages() {
        return personnageDao.findAll();
    }

    @GetMapping(value = "/Personnages/{id}")
    public Personnage afficherUnPersonnage(@PathVariable int id) {
        return personnageDao.findById(id);
    }

    @PostMapping(value = "/Personnages")
    public ResponseEntity<Personnage> ajouterPersonnage(@RequestBody Personnage personnage) {
        Personnage persoAdded = personnageDao.save(personnage);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(persoAdded.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping(value = "/Personnages/{id}")
    public ResponseEntity<Personnage> modifierUnPersonnage(@RequestBody Personnage personnage, @PathVariable int id) {
        Personnage persoModif = personnageDao.findById(id);
        if (Objects.isNull(persoModif)) {
            return ResponseEntity.notFound().build();
        } else {
            personnageDao.update(personnage, id);
            return ResponseEntity.ok(personnage);
        }
    }

    @DeleteMapping(value = "/Personnages/{id}")
    public ResponseEntity<Personnage> supprimerUnPersonnage(@PathVariable int id) {
        Personnage persoDelete = personnageDao.findById(id);
        if (Objects.isNull(persoDelete)) {
            return ResponseEntity.notFound().build();
        } else {
            personnageDao.delete(id);
        }
        return ResponseEntity.ok().build();
    }

}
