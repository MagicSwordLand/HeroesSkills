package net.brian.heroesskills.core.gui.paths;

import lombok.Getter;
import net.brian.heroesskills.HeroesSkills;
import net.brian.heroesskills.api.gui.SimpleGui;
import net.brian.heroesskills.api.players.PlayerSkillProfile;
import net.brian.heroesskills.api.players.SkillData;
import net.brian.heroesskills.api.skills.AbstractSkill;
import net.brian.heroesskills.core.utils.Icon;
import net.brian.playerdatasync.gui.GuiPageElement;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

public class SubPath extends SimpleGui {


    MainPathGui mainPathGui;

    HashMap<Integer,PathElement> skillAllocation = new HashMap<>();

    @Getter
    Function<PlayerSkillProfile,Icon> icon;

    @Getter
    int slot;

    public SubPath(HeroesSkills plugin, MainPathGui mainPathGui, int slot, Function<PlayerSkillProfile,Icon> icon) {
        super(plugin);
        this.mainPathGui = mainPathGui;
        onClose = mainPathGui::open;
        this.icon = icon;
        this.slot = slot;
    }

    @Override
    public Inventory getView(HumanEntity humanEntity) {
        Inventory inv = Bukkit.createInventory(null,54);
        Optional<PlayerSkillProfile> profile = PlayerSkillProfile.get(humanEntity.getUniqueId());
        if(profile.isEmpty()) return inv;
        PlayerSkillProfile skillProfile = profile.get();
        skillAllocation.forEach((slot,pathElement)->{
            SkillData skillData = skillProfile.getSkillData(pathElement.getAbstractSkill().getSkillID());
            if(skillData.isEmpty()) {
                ItemStack icon = pathElement.getAbstractSkill().getDisplay(skillProfile,SkillData.EMPTY_DATA);
                icon.setType(Material.BARRIER);
                inv.setItem(slot,icon);
            }
            else {
                inv.setItem(slot,pathElement.getAbstractSkill().getDisplay(skillProfile,skillData));
            }
        });
        return inv;
    }

    HashMap<UUID,Long> clickCoolDown = new HashMap<>();

    @Override
    public void click(InventoryClickEvent event) {
        if(event.getClickedInventory() == null || !(event.getClickedInventory().getType().equals(InventoryType.CHEST))) return;
        if(!checkCoolDown(event.getWhoClicked())) return;
        PathElement pathElement = skillAllocation.get(event.getSlot());
        if(pathElement == null) return;
        PlayerSkillProfile.get(event.getWhoClicked().getUniqueId()).ifPresent(profile->{
            if(pathElement.valid(profile)){
                profile.assignSkillPoint(1,pathElement.getAbstractSkill());
                refresh(profile,event.getSlot());
            }
            else event.getWhoClicked().sendMessage("你尚未達到該天賦的需求");
        });
    }

    private boolean checkCoolDown(HumanEntity humanEntity){
        long lastClicked = clickCoolDown.getOrDefault(humanEntity.getUniqueId(),0L);
        if(System.currentTimeMillis() - lastClicked > 500) {
            clickCoolDown.put(humanEntity.getUniqueId(),System.currentTimeMillis());
            return true;
        }
        return false;
    }

    private void refresh(PlayerSkillProfile profile,int slot){
        Inventory inv = viewers.get(profile.getPlayer());
        if(inv == null) return;
        PathElement pathElement = skillAllocation.get(slot);
        if(pathElement == null) return;
        SkillData skillData = profile.getSkillData(pathElement.getAbstractSkill().getSkillID());
        if(!skillData.isEmpty()){
            inv.setItem(slot,pathElement.getAbstractSkill().getDisplay(profile,skillData));
        }
    }

    public int getUnlockedAmount(PlayerSkillProfile profile){
        int[] amount = new int[]{0};
        skillAllocation.forEach((slot,pathElement)->{
            SkillData skillData = profile.getSkillData(pathElement.getAbstractSkill().getSkillID());
            if(!skillData.isEmpty()) amount[0]++;
        });
        return amount[0];
    }

    public int getSkillAmount(){
        return skillAllocation.size();
    }

    public void putElement(int slot,PathElement skill){
        skillAllocation.put(slot,skill);
    }

}
