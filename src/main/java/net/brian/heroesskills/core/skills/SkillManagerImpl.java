package net.brian.heroesskills.core.skills;

import net.brian.heroesskills.HeroesSkills;
import net.brian.heroesskills.api.skills.AbstractSkill;
import net.brian.heroesskills.api.skills.ActiveSkill;
import net.brian.heroesskills.api.skills.SkillManager;

import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;

public class SkillManagerImpl implements SkillManager {

    private final HeroesSkills plugin;

    private final HashMap<String,AbstractSkill> skillCache = new HashMap<>();
    private final HashMap<String, ActiveSkill > activeSkillCache = new HashMap<>();

    public SkillManagerImpl(HeroesSkills plugin){
        this.plugin = plugin;
    }

    @Override
    public void register(AbstractSkill abstractSkill) {
        if(abstractSkill != null)
            skillCache.compute(abstractSkill.getSkillID(), (k,v)->{
                if(v != null) v.onDispose();
                abstractSkill.onRegister();
                return abstractSkill;
            });
        if(abstractSkill instanceof ActiveSkill activeSkill)
            activeSkillCache.put(activeSkill.getSkillID(),activeSkill);
    }

    @Override
    public Optional<AbstractSkill> getSkill(String id) {
        return Optional.ofNullable(skillCache.get(id));
    }

    @Override
    public Optional<ActiveSkill> getActiveSkill(String id) {
        return Optional.ofNullable(activeSkillCache.get(id));
    }

    @Override
    public Collection<ActiveSkill> getActiveSkills() {
        return activeSkillCache.values();
    }

    @Override
    public Collection<AbstractSkill> getSkills() {
        return skillCache.values();
    }

    @Override
    public void clearSkills() {
        skillCache.clear();
    }


}
