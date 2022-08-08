package net.brian.heroesskills.api.gui;

import net.brian.heroesskills.HeroesSkills;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public abstract class SimpleGui implements Gui {

    protected HashMap<HumanEntity,Inventory> viewers = new HashMap<>();

    protected Consumer<HumanEntity> onClose = null;

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
        humanEntity.openInventory(inv);
        viewers.put(humanEntity,inv);
    }

    public abstract Inventory getView(HumanEntity humanEntity);

    public abstract void click(InventoryClickEvent event);
}

