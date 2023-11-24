package com.example.projetjava2;

import java.util.List;

public interface PersonnageDao {
    List<Personnage> findAll();

    Personnage findById(int id);

    Personnage save(Personnage product);
}
