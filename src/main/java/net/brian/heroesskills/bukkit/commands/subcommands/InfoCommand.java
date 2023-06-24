package net.brian.heroesskills.bukkit.commands.subcommands;

import net.brian.heroesskills.HeroesSkills;
import net.brian.heroesskills.api.players.PlayerSkillProfile;
import net.brian.heroesskills.bukkit.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InfoCommand extends SubCommand {

    public InfoCommand(HeroesSkills plugin) {
        super(plugin, "info");
    }


    // /hs info <player>
    @Override
    public void onCommand(CommandSender sender, String[] args) {
        Player player = null;
        if(args.length > 1) player = Bukkit.getPlayer(args[1]);
        else if(sender instanceof Player p) player = p;
        if(player != null){
            PlayerSkillProfile.get(player.getUniqueId()).ifPresent(profile->{
                profile.getSkills().forEach(entry->{
                    plugin.getSkillManager().getSkill(entry.getKey()).ifPresent(skill->{
                        sender.sendMessage(skill.getDisplayName()+" :"+ entry.getValue().level);
                    });
                });
            });
        }
    }
}
