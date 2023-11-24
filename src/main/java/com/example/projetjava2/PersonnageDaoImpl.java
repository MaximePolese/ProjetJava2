package com.example.projetjava2;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class PersonnageDaoImpl implements PersonnageDao {
    public static List<Personnage> personnages = new ArrayList<>();

    static {
        personnages.add(new Personnage(1, "Maxime", "Guerrier", 5));
        personnages.add(new Personnage(2, "Tata", "Magicien", 3));
    }

    @Override
    public List<Personnage> findAll() {
        return personnages;
    }

    @Override
    public Personnage findById(int id) {
        for (Personnage personnage : personnages) {
            if (personnage.getId() == id) {
                return personnage;
            }
        }
        return null;
    }

    @Override
    public Personnage save(Personnage personnage) {
        personnages.add(personnage);
        return personnage;
    }

}
