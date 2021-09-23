package io.ruin.model.item.pet;

/**
 * @author Mrbennjerry - https://github.com/Mrbennjerry
 * Created on 9/22/2021
 */
public enum PetVariants {

    BABY_CHIN(Pet.BABY_CHINCHOMPA_BLACK, Pet.BABY_CHINCHOMPA_GOLD, Pet.BABY_CHINCHOMPA_GREY, Pet.BABY_CHINCHOMPA_RED),

    RIFT_GUARDIAN(Pet.RIFT_GUARDIAN_FIRE, Pet.RIFT_GUARDIAN_AIR, Pet.RIFT_GUARDIAN_MIND, Pet.RIFT_GUARDIAN_WATER,
            Pet.RIFT_GUARDIAN_EARTH, Pet.RIFT_GUARDIAN_BODY, Pet.RIFT_GUARDIAN_COSMIC, Pet.RIFT_GUARDIAN_CHAOS,
            Pet.RIFT_GUARDIAN_NATURE, Pet.RIFT_GUARDIAN_LAW, Pet.RIFT_GUARDIAN_DEATH, Pet.RIFT_GUARDIAN_SOUL,
            Pet.RIFT_GUARDIAN_ASTRAL, Pet.RIFT_GUARDIAN_BLOOD, Pet.RIFT_GUARDIAN_WRATH);

    private final Pet[] pets;

    PetVariants(Pet... pets) {
        this.pets = pets;
    }

    public Pet[] getPets() {
        return pets;
    }
}
