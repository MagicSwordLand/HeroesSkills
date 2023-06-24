package net.brian.heroesskills.core.compabilities.mmo;

import io.lumine.mythic.lib.api.stat.SharedStat;
import lombok.Getter;
import net.Indyuce.mmoitems.api.player.PlayerData;
import net.Indyuce.mmoitems.api.player.RPGPlayer;
import net.brian.heroesskills.HeroesSkills;
import net.brian.heroesskills.api.players.mana.ManaEntity;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

public class MMOManaEntity extends RPGPlayer implements ManaEntity {

    private PlayerData playerData;

    @Getter
    private double mana;

    public MMOManaEntity(PlayerData playerData){
        super(playerData);
        this.playerData = playerData;
    }

    @Override
    public double getMaxMana() {
        return playerData.getStats().getMap().getStat(SharedStat.MAX_MANA);
    }

    @Override
    public boolean consume(double amount) {
        if(mana >= amount){
            mana -= amount;
            return true;
        }
        return false;
    }

    @Override
    public void add(double amount) {
        mana = Math.max(0,Math.min(mana+amount,getMaxMana()));
    }

    @Override
    public double getManaRegen() {
        return playerData.getStats().getMap().getStat(SharedStat.MANA_REGENERATION);
    }

    @Override
    public int getLevel() {
        return playerData.getPlayer().getLevel();
    }

    @Override
    public String getClassName() {
        return MMOManaEntity.class.getName();
    }

    @Override
    public double getStamina() {
        return 0;
    }

    @Override
    public void setMana(double v) {
        mana = v;
    }

    @Override
    public void setStamina(double v) {

    }
}
