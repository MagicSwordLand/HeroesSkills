package net.brian.heroesskills.api.players.mana;

import java.util.UUID;

public interface ManaProvider {

    ManaEntity getManaEntity(UUID uuid);

}
