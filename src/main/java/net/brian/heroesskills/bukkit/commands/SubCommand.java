package net.brian.heroesskills.bukkit.commands;

import net.brian.heroesskills.HeroesSkills;
import org.bukkit.command.CommandSender;

public abstract class SubCommand {

    protected final String name;
    protected final HeroesSkills plugin;

    public SubCommand(HeroesSkills plugin,String name){
        this.name = name;
        this.plugin = plugin;
    }

    public String getName() {
        return name;
    }

    public abstract void onCommand(CommandSender sender, String[] args);

}
