package com.example.projetjava2;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    public List<Personnage> listeDesPersonnages() {
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
            @ApiResponse(responseCode = "200", description = "Personnage ajouté",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Personnage.class))})
    })
    @PostMapping(value = "/personnages")
    public Personnage ajouterUnPersonnage(@Valid @RequestBody Personnage personnage) {
        return personnageDao.save(personnage);
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
            personnageDao.deleteById(id);
        }
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Modifier un personnage par son id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Personnage modifié",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Personnage.class))}),
            @ApiResponse(responseCode = "404", description = "Not found",
                    content = @Content)})
    @PutMapping(value = "/personnages/{id}")
    public ResponseEntity<Personnage> modifierUnPersonnage(@Valid @RequestBody Personnage personnage, @PathVariable int id) {
        Personnage persoModif = personnageDao.findById(id);
        if (Objects.isNull(persoModif)) {
            return ResponseEntity.notFound().build();
        } else {
            personnageDao.save(personnage);
            return ResponseEntity.ok(personnage);
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
