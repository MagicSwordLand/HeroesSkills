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
import java.util.List;

public abstract class SimpleGui implements Listener,Gui {

    List<HumanEntity> viewers = new ArrayList<>();


    public SimpleGui(HeroesSkills plugin){
        Bukkit.getPluginManager().registerEvents(this,plugin);
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event){
        viewers.remove(event.getPlayer());
    }

    @EventHandler
    public void onClick(InventoryClickEvent event){
        if(viewers.contains(event.getWhoClicked())) event.setCancelled(true);
    }

    public void open(HumanEntity humanEntity){
        if(viewers.contains(humanEntity)) return;
        humanEntity.openInventory(getView(humanEntity));
        viewers.add(humanEntity);
    }

    public abstract Inventory getView(HumanEntity humanEntity);
}

