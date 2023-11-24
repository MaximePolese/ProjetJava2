package com.example.projetjava2;

import java.util.List;

public interface PersonnageDao {
    List<Personnage> findAll();

    Personnage findById(int id);

    Personnage add(Personnage personnage);

    void update(Personnage personnage, int id);

    void delete(int id);
}
