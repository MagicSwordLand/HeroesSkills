package net.brian.heroesskills.core.gui.paths;

import net.brian.heroesskills.HeroesSkills;
import net.brian.heroesskills.api.gui.SimpleGui;
import net.brian.heroesskills.api.players.PlayerSkillProfile;
import net.brian.heroesskills.core.utils.Icon;
import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class MainPathGui extends SimpleGui {

    HashMap<String, SubPath> paths = new HashMap<>();


    public MainPathGui(HeroesSkills plugin) {
        super(plugin);
    }

    @Override
    public Inventory getView(HumanEntity humanEntity) {
        Inventory inv = Bukkit.createInventory(null,54);
        Optional<PlayerSkillProfile> skillProfile = PlayerSkillProfile.get(humanEntity.getUniqueId());
        if(skillProfile.isEmpty()) return inv;
        paths.values().forEach(subPath -> {
            inv.setItem(subPath.slot, subPath.icon.apply(skillProfile.get()).build((Player) humanEntity));
        });
        return inv;
    }

    @Override
    public void click(InventoryClickEvent event) {
        if(event.getClickedInventory()== null || !event.getClickedInventory().getType().equals(InventoryType.CHEST)) return;
        if(event.getCurrentItem() == null) return;
        for (Map.Entry<String, SubPath> entry : paths.entrySet()) {
            if(entry.getValue().slot == event.getSlot()){
                entry.getValue().open(event.getWhoClicked());
                return;
            }
        }
    }


    public void registerPath(String ID,int slot, Function<PlayerSkillProfile,Icon> display){
        paths.put(ID,new SubPath(plugin,this,slot,display));
    }

    public void registerElement(PathElement pathElement){
        pathElement.getPathSlots().forEach((pathID,slot)->{
            getSubPath(pathID).ifPresent(subPath -> subPath.putElement(slot,pathElement));
        });
    }

    public Optional<SubPath> getSubPath(String id){
        return Optional.ofNullable(paths.get(id));
    }

}
