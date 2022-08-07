package net.brian.heroesskills.api.gui;

import org.bukkit.entity.HumanEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

import java.net.PortUnreachableException;
import java.util.*;

public abstract class PageGui implements Gui{

    private final HashMap<HumanEntity,Cache> viewers = new HashMap<>();



    @EventHandler
    public void click(InventoryClickEvent event) {
        Cache cache = viewers.get(event.getWhoClicked());
        if(cache != null){
            event.setCancelled(true);
            onClick(event,cache);
        }
    }

    @Override
    public void open(HumanEntity humanEntity) {
        if(viewers.containsKey(humanEntity)) return;
        Cache cache = newCache(humanEntity);
        cache.pages = getPages(humanEntity);
        if(cache.pages.size() > 0){
            humanEntity.openInventory(cache.pages.get(0));
            viewers.put(humanEntity,cache);
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event){
        viewers.remove(event.getPlayer());
    }

    protected abstract List<Inventory> getPages(HumanEntity humanEntity);

    protected abstract void onClick(InventoryClickEvent event,Cache cache);

    protected Cache newCache(HumanEntity humanEntity){
        return new Cache();
    }
    /*
    Allows negative numbers
     */
    public void turnPage(HumanEntity humanEntity,int pages){
        Cache cache = viewers.get(humanEntity);
        if(cache != null){
            int targetPage = cache.currentPage + pages;
            if(targetPage > 0 && targetPage < cache.pages.size()){
                cache.currentPage = targetPage;
                humanEntity.openInventory(cache.pages.get(targetPage));
                viewers.put(humanEntity,cache);
            }
        }
    }

    public void firstPage(HumanEntity humanEntity){
        Cache cache = viewers.get(humanEntity);
        if(cache != null){
            if(cache.pages.size() > 0){
                humanEntity.openInventory(cache.pages.get(0));
                viewers.put(humanEntity,cache);
                cache.currentPage = 0;
            }
        }
    }

    public void lastPage(HumanEntity humanEntity){
        Cache cache = viewers.get(humanEntity);
        if(cache != null){
            if(cache.pages.size() > 0){
                humanEntity.openInventory(cache.pages.get(cache.pages.size()-1));
                viewers.put(humanEntity,cache);
                cache.currentPage = cache.pages.size()-1;
            }
        }
    }

    protected Cache getCache(HumanEntity humanEntity){
        return viewers.get(humanEntity);
    }



    protected static class Cache{
        public int currentPage = 0;
        public List<Inventory> pages;
    }
    
}
