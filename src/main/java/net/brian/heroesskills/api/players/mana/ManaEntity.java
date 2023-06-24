package net.brian.heroesskills.api.players.mana;

public interface ManaEntity {


    double getMaxMana();
    double getMana();

    // Returns false if not enough mana
    boolean consume(double amount);

    void add(double amount);
    void setMana(double amount);

    // regens per 2 seconds
    double getManaRegen();


}
