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

    private final HashMap<String,AbstractSkill> cache = new HashMap<>();
    private final HashMap<String, ActiveSkill > activeSkillCache = new HashMap<>();

    public SkillManagerImpl(HeroesSkills plugin){
        this.plugin = plugin;
    }

    @Override
    public void register(AbstractSkill abstractSkill) {
        if(abstractSkill != null)
            cache.put(abstractSkill.getSkillID(),abstractSkill);
        if(abstractSkill instanceof ActiveSkill activeSkill)
            activeSkillCache.put(activeSkill.getSkillID(),activeSkill);
    }

    @Override
    public Optional<AbstractSkill> get(String id) {
        return Optional.ofNullable(cache.get(id));
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
        return cache.values();
    }

    @Override
    public void clear() {
        cache.clear();
    }
}
