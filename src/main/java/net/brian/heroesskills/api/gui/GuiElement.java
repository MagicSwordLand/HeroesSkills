package net.brian.heroesskills.api.gui;

import lombok.Getter;
import lombok.Setter;
import net.brian.heroesskills.api.players.PlayerSkillProfile;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;
import java.util.function.Function;

public class GuiElement {

    @Getter
    @Setter
    private Function<PlayerSkillProfile,ItemStack> display;

    @Setter
    @Getter
    private Consumer<InventoryClickEvent> action;

    private static final ItemStack DEFAULT_DISPLAY = new ItemStack(Material.STONE);


    public void click(InventoryClickEvent event){
        if(action != null) action.accept(event);
    }


    public ItemStack getDisplay(PlayerSkillProfile skillProfile){
        if(display != null){
            return display.apply(skillProfile);
        }
        return DEFAULT_DISPLAY;
    }

}
