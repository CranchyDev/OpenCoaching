package com.opencoaching.opencoaching;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GetStuffFromDB implements CommandLineRunner {

    private final CoachesRepository coachesRepository;

    public GetStuffFromDB(CoachesRepository coachesRepository) {
        this.coachesRepository = coachesRepository;
    }

    @Override
    public void run(String... args) {
        if (coachesRepository.count() == 0) {
            coachesRepository.saveAll(List.of(
                new Coaches("Christallis28", "Emerald"),
                new Coaches("Abzlol", "Master"),
                new Coaches("Julian Guacho", "Bronze"),
                new Coaches("IAMBAD", "Challenger"),
                new Coaches("Train Station29", "Platinum"),
                new Coaches("Buranka", "Diamond"),
                new Coaches("Acktivo", "Silver"),
                new Coaches("NomNomNom", "Iron"),
                new Coaches("MyBotIsFeeding", "Grandmaster")
            ));
        }
    }
}