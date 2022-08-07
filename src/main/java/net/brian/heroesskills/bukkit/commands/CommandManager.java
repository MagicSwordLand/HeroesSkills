package net.brian.heroesskills.bukkit.commands;

import net.brian.heroesskills.HeroesSkills;
import net.brian.heroesskills.bukkit.commands.subcommands.AddAllCommand;
import net.brian.heroesskills.bukkit.commands.subcommands.AddSkillCommand;
import net.brian.heroesskills.bukkit.commands.subcommands.CastButtonCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommandManager implements CommandExecutor {

    private final HeroesSkills plugin;
    private final List<SubCommand> subCommands = new ArrayList<>();

    public CommandManager(HeroesSkills plugin){
        this.plugin = plugin;
        register(new AddAllCommand(plugin));
        register(new AddSkillCommand(plugin));
        register(new CastButtonCommand(plugin));
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length == 0) return true;
        getCommand(args[0]).ifPresent(subCommand -> {
            subCommand.onCommand(sender,args);
        });
        return true;
    }

    public Optional<SubCommand> getCommand(String name){
        for (SubCommand subCommand : subCommands) {
            if(subCommand.getName().equalsIgnoreCase(name)) return Optional.of(subCommand);
        }
        return Optional.empty();
    }

    public void register(@NotNull SubCommand subCommand){
        subCommands.add(subCommand);
    }
}
