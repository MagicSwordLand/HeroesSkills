package net.brian.heroesskills.bukkit.commands.subcommands;

import net.brian.heroesskills.HeroesSkills;
import net.brian.heroesskills.api.players.PlayerSkillProfile;
import net.brian.heroesskills.bukkit.commands.SubCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CastButtonCommand extends SubCommand {


    public CastButtonCommand(HeroesSkills plugin){
        super(plugin, "CastButton");
    }

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if(sender instanceof Player player){
            plugin.getSkillSelectGui().open(player);
        }
    }
}
