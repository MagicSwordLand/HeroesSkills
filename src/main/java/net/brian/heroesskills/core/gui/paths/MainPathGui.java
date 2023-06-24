package net.brian.heroesskills.core.gui.paths;

import net.brian.heroesskills.HeroesSkills;
import net.brian.heroesskills.api.gui.SimpleGui;
import net.brian.heroesskills.api.players.PlayerSkillProfile;
import net.brian.heroesskills.bukkit.configs.Language;
import net.brian.heroesskills.core.utils.Icon;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
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
        Inventory inv = Bukkit.createInventory(null,54, Language.MAIN_PATH_GUI_TITLE);
        Optional<PlayerSkillProfile> skillProfile = PlayerSkillProfile.get(humanEntity.getUniqueId());
        if(skillProfile.isEmpty()) return inv;
        paths.values().forEach(subPath -> {
            ItemStack icon = subPath.icon.apply(skillProfile.get()).build((Player) humanEntity);
            if(!humanEntity.hasPermission("hs.path."+subPath.getID()))
                icon.setType(Material.BARRIER);
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
                if(event.getWhoClicked().hasPermission("hs.path."+entry.getKey())){
                    Bukkit.getScheduler().runTaskLater(
                            HeroesSkills.getInstance(),
                            ()->entry.getValue().open(event.getWhoClicked()),
                            1L
                    );
                }
                else event.getWhoClicked().sendMessage(Language.PATH_NOT_UNLOCKED);
            }
        }
    }


    public void registerPath(String ID, int slot,String guiTitle, Function<PlayerSkillProfile,Icon> display,Map<Integer,Function<PlayerSkillProfile, Icon>> staticElements){
        SubPath subPath = new SubPath(plugin,ID,this,slot,guiTitle,display);
        paths.put(ID,subPath);
        staticElements.forEach(subPath::addStaticElement);
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
