package net.brian.heroesskills.api.skills.casting;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public enum ClickSequence {

    RRR(new ArrayList<>(List.of(new ClickType[]{ClickType.RIGHT,ClickType.RIGHT,ClickType.RIGHT})),0),
    RRL(new ArrayList<>(List.of(new ClickType[]{ClickType.RIGHT,ClickType.RIGHT,ClickType.LEFT})),1),
    RLR(new ArrayList<>(List.of(new ClickType[]{ClickType.RIGHT,ClickType.LEFT,ClickType.RIGHT})),2),
    RLL(new ArrayList<>(List.of(new ClickType[]{ClickType.RIGHT,ClickType.LEFT,ClickType.LEFT})),3);

    @Getter
    final int id;
    final ArrayList<ClickType> sequence;

    ClickSequence(ArrayList<ClickType> sequence,int id){
        this.id = id;
        this.sequence = sequence;
    }

    public static Optional<ClickSequence> get(ArrayList<ClickType> sequence){
        if(sequence.size() < 3) return Optional.empty();
        for (ClickSequence clickSequence : values()) {
            boolean test = true;
            for(int i=0;i<3;i++){
                if (!clickSequence.sequence.get(i).equals(sequence.get(i))) {
                    test = false;
                    break;
                }
            }
            if(test) return Optional.of(clickSequence);
        }
        return Optional.empty();
    }

    public static Optional<ClickSequence> get(int id){
        for (ClickSequence clickSequence : values()) {
            if(clickSequence.id==id) return Optional.of(clickSequence);
        }
        return Optional.empty();
    }


}
