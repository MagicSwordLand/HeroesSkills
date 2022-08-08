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

    public static final String ICON_KEY = "SkillID";

    public AbstractSkill(String skillID,String displayName){
        this.skillID = skillID;
        this.displayName = IridiumColorAPI.process(displayName);
    }

    protected abstract Icon getIcon(PlayerSkillProfile player, SkillData skillData);

    public ItemStack getDisplay(PlayerSkillProfile profile,SkillData skillData){
        Icon icon = getIcon(profile,skillData);
        icon.addTags(ICON_KEY,skillID);
        return icon.build(profile.getPlayer());
    }

    // runs on player join , on levelup (right after onDeactivate) , and on active skill equip
    public void onActivate(@NotNull PlayerSkillProfile playerProfile,SkillData skillData){}

    //runs on player leave, on levelup (runs onActivate afterwards) , and on active skill unequip
    public void onDeactivate( @NotNull PlayerSkillProfile playerProfile, SkillData skillData){}


}
