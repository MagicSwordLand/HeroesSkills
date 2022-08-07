package net.brian.heroesskills.bukkit.scoreboard;

import org.bukkit.entity.Player;

import java.util.List;

public interface ScoreboardService {

    void update(Player player, int priority, List<String> lines);

}
