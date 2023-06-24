package net.brian.heroesskills.api.players;

import lombok.Getter;

public class SkillData {

    public static final SkillData EMPTY_DATA  = new SkillData();
    public static final SkillData LEVEL_1 = new SkillData();

    static {
        LEVEL_1.level = 1;
    }

    public int level = 0;
    public long lastCast = 0;

    public boolean isEmpty(){
        return level == 0;
    }


}
