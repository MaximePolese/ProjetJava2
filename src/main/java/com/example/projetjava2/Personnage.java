package com.example.projetjava2;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Personnage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Schema(name = "id", example = "1", required = true)
    private int id;
    @NotBlank(message = "Name is mandatory")
    @Schema(name = "name", example = "Maxime")
    private String name;
    @NotBlank(message = "Type is mandatory")
    @Schema(name = "type", example = "guerrier")
    private String type;
    @Min(value = 1, message = "Life can't be null")
    @Schema(name = "life", example = "10")
    private int life;

    public Personnage() {
    }

    public Personnage(int id, String name, String type, int life) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.life = life;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    @Override
    public String toString() {
        return "Personnage{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", life=" + life +
                '}';
    }
}
