package net.brian.heroesskills.api.skills;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface SkillManager {

    void register(AbstractSkill abstractSkill);

    Optional<AbstractSkill> get(String id);

    Optional<ActiveSkill> getActiveSkill(String id);

    Collection<ActiveSkill> getActiveSkills();
    Collection<AbstractSkill> getSkills();

    void clear();

}
