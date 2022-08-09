package net.brian.heroesskills.api.skills;

import lombok.Getter;
import lombok.Setter;
import net.brian.heroesskills.HeroesSkills;
import net.brian.heroesskills.api.players.PlayerSkillProfile;
import net.brian.heroesskills.api.players.SkillData;
import net.brian.heroesskills.api.players.mana.ManaEntity;
import net.brian.heroesskills.bukkit.configs.Language;
import net.brian.playerdatasync.util.IridiumColorAPI;
import net.brian.playerdatasync.util.time.TimeUnit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public abstract class ActiveSkill extends AbstractSkill {



    @Setter
    protected double manaConsume = 10;

    //秒數
    @Setter
    @Getter
    protected double cooldown = 10;




    public ActiveSkill(String skillID,String displayName) {
        super(skillID,displayName);
    }


    public abstract void onCast(@NotNull Player player, @NotNull PlayerSkillProfile playerProfile);

    public void cast(Player player, @NotNull PlayerSkillProfile playerProfile){
        ManaEntity manaEntity = HeroesSkills.getInstance().getManaProvider().getManaEntity(player.getUniqueId());
        SkillData skillData = playerProfile.getSkillData(skillID);
        long a = ((long) cooldown)*1000 - (System.currentTimeMillis() - skillData.lastCast);
        if(a > 0) {
            player.sendMessage(Language.CAST_IN_COOLDOWN.replace("#cooldown#",TimeUnit.getDisplayTime(a)));
        }
        else if(!manaEntity.consume(manaConsume)){
            player.sendMessage(Language.CAST_NO_MANA.replace("#require#",manaConsume+""));
        }
        else {
            onCast(player, playerProfile);
            skillData.lastCast = System.currentTimeMillis();
            player.sendMessage(Language.CAST_SUCCESS.replace("#name#",displayName));
        }
    }
}
