package net.brian.heroesskills.bukkit.commands;

import net.brian.heroesskills.HeroesSkills;
import net.brian.heroesskills.bukkit.commands.subcommands.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
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
        register(new SkillPointCommand(plugin));
        register(new InfoCommand(plugin));
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(args.length == 0) {
            if(sender instanceof Player player){
                plugin.getMainPathGui().open(player);
            }
            return true;
        }
        getCommand(args[0]).ifPresentOrElse(subCommand -> {
            subCommand.onCommand(sender,args);
        },()->{
            sender.sendMessage("找不到指令");
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
