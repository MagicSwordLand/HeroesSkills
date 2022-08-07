package net.brian.heroesskills.core.compabilities.mmo;

import io.lumine.mythic.lib.MythicLib;
import io.lumine.mythic.lib.api.stat.SharedStat;
import io.lumine.mythic.lib.api.stat.StatMap;
import io.lumine.mythic.lib.api.stat.handler.StatHandler;
import net.Indyuce.mmoitems.MMOItems;
import net.Indyuce.mmoitems.api.player.PlayerData;
import net.Indyuce.mmoitems.api.player.RPGPlayer;
import net.Indyuce.mmoitems.comp.rpg.RPGHandler;
import net.brian.heroesskills.HeroesSkills;
import net.brian.heroesskills.api.players.mana.ManaEntity;
import net.brian.heroesskills.api.players.mana.ManaProvider;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.UUID;

public class MMOManaProvider implements ManaProvider , RPGHandler {

    HashMap<UUID,MMOManaEntity> manaEntityMap = new HashMap<>();


    public MMOManaProvider(HeroesSkills plugin){
        MMOItems.plugin.setRPG(this);
        MythicLib.inst().getStats().registerStat(SharedStat.MANA_REGENERATION, new StatHandler() {
            @Override
            public void runUpdate(StatMap statMap) {
            }
            @Override
            public double getBaseValue(StatMap statMap) {
                return 1;
            }
        });
        MythicLib.inst().getStats().registerStat(SharedStat.MAX_MANA, new StatHandler() {
            @Override
            public void runUpdate(StatMap statMap) {
            }
            @Override
            public double getBaseValue(StatMap statMap) {
                return 20;
            }
        });
        Bukkit.getScheduler().runTaskTimer(plugin,()->{
            manaEntityMap.forEach((uuid,manaEntity)->manaEntity.add(manaEntity.getManaRegen()));
        },100,40);
    }


    @Override
    public MMOManaEntity getManaEntity(UUID uuid) {
        return manaEntityMap.get(uuid);
    }

    @Override
    public RPGPlayer getInfo(PlayerData playerData) {
        return manaEntityMap.computeIfAbsent(playerData.getUniqueId(),k->new MMOManaEntity(playerData));
    }

    @Override
    public void refreshStats(PlayerData playerData) {

    }
}
