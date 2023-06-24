package net.brian.heroesskills.api.players;

import lombok.Getter;
import net.brian.heroesskills.HeroesSkills;
import net.brian.heroesskills.api.skills.AbstractSkill;
import net.brian.heroesskills.api.skills.ActiveSkill;
import net.brian.heroesskills.api.skills.SkillManager;
import net.brian.heroesskills.api.skills.casting.ClickSequence;
import net.brian.playerdatasync.PlayerDataSync;
import net.brian.playerdatasync.objects.PlayerData;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;
import java.util.*;

public class PlayerSkillProfile extends PlayerData  {

    private HashMap<Integer,String> castingButtonSettings = new HashMap<>(
            Map.of(0,"",1,"",2,"",3,"")
    );

    private HashMap<String,SkillData> skillDataMap = new HashMap<>();


    private HashMap<String,Integer> skillPointSource = new HashMap<>();

    @Getter
    private int skillPoint = 0;

    @Getter
    private transient Player player;

    public boolean buffNotAlly = true;

    public boolean damageNotEnemy = false;

    public PlayerSkillProfile(UUID uuid) {
        super(uuid);
    }



    public static Optional<PlayerSkillProfile> get(UUID uuid){
        return PlayerDataSync.getInstance().getPlayerDatasRepository().getPlayerCache(uuid)
                .flatMap(c->c.getPlayerData("heroesskills", PlayerSkillProfile.class));
    }


    private static final SkillData EMPTY_DATA = new SkillData();
    public SkillData getSkillData(String skillID){
        return skillDataMap.getOrDefault(skillID,EMPTY_DATA);
    }

    public Set<Map.Entry<String, SkillData>> getSkills() {
        return skillDataMap.entrySet();
    }


    @Override
    public void onJoin(Player player) {
        this.player = player;
        SkillManager skillManager = HeroesSkills.getInstance().getSkillManager();
        skillDataMap.forEach((id,data)->{
            skillManager.getSkill(id).ifPresent(abstractSkill -> {
                abstractSkill.onActivate(this,data);
            });
        });
        castingButtonSettings.values().forEach(s->{
            HeroesSkills.getInstance().getSkillManager().getActiveSkill(s).ifPresent(skill->{
                skill.onEquip(this,getSkillData(s));
            });
        });
    }

    @Override
    public void onQuit(Player player){
        SkillManager skillManager = HeroesSkills.getInstance().getSkillManager();
        skillDataMap.forEach((id,data)->{
            skillManager.getSkill(id).ifPresent(abstractSkill -> {
                abstractSkill.onDeactivate(this,data);
            });
        });
    }

    public Optional<ActiveSkill> getButtonSkill(ClickSequence clickSequence){
        return HeroesSkills.getInstance().getSkillManager().getActiveSkill(castingButtonSettings.get(clickSequence.getId()));
    }

    public void setButtonSkill(ClickSequence clickSequence,@Nullable ActiveSkill activeSkill){
        if(activeSkill != null){
            for (Map.Entry<Integer, String> entry : castingButtonSettings.entrySet()) {
                if(entry.getValue().equals(activeSkill.getSkillID())){
                    player.sendMessage("你已經綁定過此技能了");
                    return;
                }
            }
        }
        String originSkill = castingButtonSettings.get(clickSequence.getId());
        HeroesSkills.getInstance().getSkillManager().getSkill(originSkill).ifPresent(skill->{
            if(skill instanceof ActiveSkill activeSkill1) activeSkill1.onUnEquip(this,skillDataMap.get(originSkill));
        });
        if(activeSkill == null){
            castingButtonSettings.put(clickSequence.getId(),"");
            return;
        }
        if(!skillDataMap.containsKey(activeSkill.getSkillID())) {
            player.sendMessage("你未擁有技能"+activeSkill.getDisplayName());
            return;
        }
        castingButtonSettings.put(clickSequence.getId(),activeSkill.getSkillID());
        activeSkill.onEquip(this,skillDataMap.get(activeSkill.getSkillID()));
    }

    public void giveSkillPoint(int amount,String source){
        skillPointSource.compute(source,(k,v)->{
            if(v == null) v = 0;
            v+=amount;
            skillPoint += amount;
            return v;
        });
    }

    public void setSkillPoint(String source,int amount){
        skillPointSource.put(source,amount);
        resetSkillPoints();
    }

    public void resetSkillPoints(){
        skillDataMap.forEach((id,data)->{
            if(data.isEmpty()) return;

            HeroesSkills.getInstance().getSkillManager().getSkill(id).ifPresent(skill->{
                data.level = 0;
                skill.onDeactivate(this,data);
                if(skill instanceof ActiveSkill activeSkill){
                    activeSkill.onUnEquip(this,data);
                }
            });
        });
        skillPoint = 0;
        for (Map.Entry<String, Integer> entry : skillPointSource.entrySet()) {
            if(!entry.getKey().equals("tempt")){
                skillPoint += entry.getValue();
            }
        }
        for(int i=0;i<4;i++) castingButtonSettings.put(i,"");
    }

    public void addSkill(AbstractSkill skill, int levels){
        skillDataMap.compute(skill.getSkillID(),(k,v)->{
            if(v == null) v = new SkillData();
            int targetLevel = v.level + levels;
            v.level = Math.min(skill.getMaxLevel(),targetLevel);
            skill.onDeactivate(this,v);
            skill.onActivate(this,v);
            return v;
        });
    }

    public void assignSkillPoint(int amount,AbstractSkill abstractSkill){
        skillDataMap.compute(abstractSkill.getSkillID(),(k,v)->{
            if(v == null) {
                v = new SkillData();
            }
            int increaseLevel = Math.min(abstractSkill.getMaxLevel()-v.level,amount);
            if(skillPoint >= increaseLevel){
                if(v.level > 0) {
                    abstractSkill.onDeactivate(this, v);
                }
                player.playSound(player.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP,1,1);
                v.level += increaseLevel;
                skillPoint -= increaseLevel;
                abstractSkill.onActivate(this,v);
            }
            else player.sendMessage("操作失敗，技能點不足，剩餘"+skillPoint);
            return v;
        });
    }

    public int getMaxSkillPoint(){
        int points = 0;
        for (Integer i : skillPointSource.values()) {
            points+=i;
        }
        return points;
    }

    public void reduceCoolDown(String skill,double seconds){
        SkillData skillData = skillDataMap.get(skill);
        if(skillData != null) skillData.lastCast -= seconds*1000L;
    }

}

