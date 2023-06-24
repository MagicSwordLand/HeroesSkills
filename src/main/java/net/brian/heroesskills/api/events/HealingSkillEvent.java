package net.brian.heroesskills.api.events;

import lombok.Getter;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class HealingSkillEvent extends Event {

    private final static HandlerList handlerlist = new HandlerList();

    @Getter
    private final Entity caster;
    @Getter
    private final Entity target;
    @Getter
    private final double amount;

    public HealingSkillEvent(Entity caster, Entity target, double amount){
        this.caster= caster;
        this.target =target;
        this.amount = amount;
    }


    public static HandlerList getHandlerList(){
        return handlerlist;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlerlist;
    }
}
