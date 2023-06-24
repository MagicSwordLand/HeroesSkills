package net.brian.heroesskills.api.skills;

import java.util.Collection;
import java.util.Optional;

public interface SkillManager {

    void register(AbstractSkill abstractSkill);
    Optional<AbstractSkill> getSkill(String id);
    Optional<ActiveSkill> getActiveSkill(String id);
    Collection<ActiveSkill> getActiveSkills();
    Collection<AbstractSkill> getSkills();
    void clearSkills();



}
