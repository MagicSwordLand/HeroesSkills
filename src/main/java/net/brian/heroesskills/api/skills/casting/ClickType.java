package net.brian.heroesskills.api.skills.casting;

import lombok.Getter;

public enum ClickType {
    RIGHT("R"),LEFT("L");

    @Getter
    final String display;

    ClickType(String display){
        this.display = display;
    }
}
