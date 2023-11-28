package com.example.projetjava2;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Objects;

@RestController
public class CharacterController {
    private final PersonnageDao personnageDao;

    public CharacterController(PersonnageDao personnageDao) {
        this.personnageDao = personnageDao;
    }

    @Operation(summary = "Afficher tous les personnages")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Personnages affichés",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Personnage.class))})
    })
    @GetMapping("/personnages")
    public Personnage[] listePersonnages() {
        return personnageDao.findAll();
    }

    @Operation(summary = "Afficher un personnage par son id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Personnage affiché",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Personnage.class))})
    })
    @GetMapping(value = "/personnages/{id}")
    public Personnage afficherUnPersonnage(@PathVariable int id) {
        return personnageDao.findById(id);
    }

    @Operation(summary = "Ajouter un personnage")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Personnage ajouté",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Personnage.class))})
    })
    @PostMapping(value = "/personnages")
    public ResponseEntity<Personnage> ajouterPersonnage(@RequestBody Personnage personnage) {
        Personnage persoAdded = personnageDao.save(personnage);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(persoAdded.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Modifier un personnage par son id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Personnage modifié",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Personnage.class))}),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content)})
    @PutMapping(value = "/personnages/{id}")
    public ResponseEntity<Personnage> modifierUnPersonnage(@RequestBody Personnage personnage, @PathVariable int id) {
        Personnage persoModif = personnageDao.findById(id);
        if (Objects.isNull(persoModif)) {
            return ResponseEntity.notFound().build();
        } else {
            personnageDao.update(personnage, id);
            return ResponseEntity.ok(personnage);
        }
    }

    @Operation(summary = "Supprimer un personnage")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Personnage supprimé",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Personnage.class))}),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content)})
    @DeleteMapping(value = "/personnages/{id}")
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
