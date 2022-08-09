package net.brian.heroesskills.api.gui;

import net.brian.heroesskills.HeroesSkills;
import net.brian.heroesskills.api.players.PlayerSkillProfile;
import net.brian.heroesskills.core.utils.Icon;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class SimpleGui implements Gui {

    protected HashMap<HumanEntity,Inventory> viewers = new HashMap<>();

    protected Consumer<HumanEntity> onClose = null;

    protected HashMap<Integer, Function<PlayerSkillProfile, Icon>> staticElements = new HashMap<>();

    protected HeroesSkills plugin;
    public SimpleGui(HeroesSkills plugin){
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this,plugin);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event){

        if(viewers.containsKey(event.getPlayer())){
            viewers.remove(event.getPlayer());
            if(onClose != null)
                onClose.accept(event.getPlayer());
        }
    }

    @EventHandler
    public void onClick(InventoryClickEvent event){
        if(viewers.containsKey(event.getWhoClicked())) {
            event.setCancelled(true);
            click(event);
        }
    }

    public void open(HumanEntity humanEntity){
        if(viewers.containsKey(humanEntity)) return;
        Inventory inv = getView(humanEntity);
        PlayerSkillProfile.get(humanEntity.getUniqueId()).ifPresent(profile->{
            staticElements.forEach((slot,element)->{
                inv.setItem(slot,element.apply(profile).build((Player) humanEntity));
            });
        });
        humanEntity.openInventory(inv);
        viewers.put(humanEntity,inv);
    }

    public abstract Inventory getView(HumanEntity humanEntity);

    public abstract void click(InventoryClickEvent event);

    public void addStaticElement(int slot,Function<PlayerSkillProfile, Icon> fun) {
        staticElements.put(slot,fun);
    }
}

