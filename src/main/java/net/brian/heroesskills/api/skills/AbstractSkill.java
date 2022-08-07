package net.brian.heroesskills.api.skills;

import lombok.Getter;
import net.brian.heroesskills.api.players.PlayerSkillProfile;
import net.brian.heroesskills.api.players.SkillData;
import net.brian.heroesskills.core.utils.Icon;
import net.brian.playerdatasync.util.IridiumColorAPI;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class AbstractSkill {


    @Getter
    protected final String skillID;

    @Getter
    protected int maxLevel = 10;

    @Getter
    protected String displayName = "";

    public AbstractSkill(String skillID,String displayName){
        this.skillID = skillID;
        this.displayName = IridiumColorAPI.process(displayName);
    }

    protected abstract Icon getIcon(HumanEntity player, SkillData skillData);

    public ItemStack getDisplay(HumanEntity player,SkillData skillData){
        Icon icon = getIcon(player,skillData);
        icon.addTags("SkillID",skillID);
        return icon.build();
    }

    // runs on player join , on levelup (right after onDeactivate) , and on active skill equip
    public void onActivate(@NotNull PlayerSkillProfile playerProfile){}

    //runs on player leave, on levelup (runs onActivate afterwards) , and on active skill unequip
    public void onDeactivate( @NotNull PlayerSkillProfile playerProfile){}


}
