package net.brian.heroesskills.api.skills;

import lombok.Getter;
import lombok.Setter;
import net.brian.heroesskills.HeroesSkills;
import net.brian.heroesskills.api.players.PlayerSkillProfile;
import net.brian.heroesskills.api.players.SkillData;
import net.brian.heroesskills.api.players.mana.ManaEntity;
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
    protected double cooldown = 10;




    public ActiveSkill(String skillID,String displayName) {
        super(skillID,displayName);
    }


    public abstract void onCast(@NotNull Player player, @NotNull PlayerSkillProfile playerProfile);

    public void cast(Player player, @NotNull PlayerSkillProfile playerProfile){
        ManaEntity manaEntity = HeroesSkills.getInstance().getManaProvider().getManaEntity(player.getUniqueId());
        Optional<SkillData> skillData = playerProfile.getSkillData(skillID);
        if(skillData.isEmpty()) return ;
        long a = ((long) cooldown)*1000 - (System.currentTimeMillis() - skillData.get().lastCast);
        if(a > 0) {
            player.sendMessage(displayName+"技能冷卻中"+ TimeUnit.getDisplayTime(a));
        }
        else if(!manaEntity.consume(manaConsume)){
            player.sendMessage("施放此技能需要 "+manaConsume+" 點魔力，你只有"+manaEntity.getMana());
        }
        else {
            onCast(player, playerProfile);
            skillData.get().lastCast = System.currentTimeMillis();
            player.sendMessage("成功施放 "+ displayName);
        }
    }
}
