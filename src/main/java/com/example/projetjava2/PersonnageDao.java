package com.example.projetjava2;

public interface PersonnageDao {
    Personnage[] findAll();

    Personnage findById(int id);

    Personnage save(Personnage personnage);

    void update(Personnage personnage, int id);

    void delete(int id);
}
