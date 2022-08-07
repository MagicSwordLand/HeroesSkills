package net.brian.heroesskills.bukkit.commands.subcommands;

import net.brian.heroesskills.HeroesSkills;
import net.brian.heroesskills.api.players.PlayerSkillProfile;
import net.brian.heroesskills.bukkit.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class AddAllCommand extends SubCommand {

    public AddAllCommand(HeroesSkills plugin) {
        super(plugin, "addall");
    }

    // /hs addall <player>
    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if(sender.hasPermission("HeroesSkill.admin") && args.length >= 2){
            Player player = Bukkit.getPlayer(args[1]);
            if(player == null){
                sender.sendMessage("找不到該玩家"+args[1]);
                return;
            }
            PlayerSkillProfile.get(player.getUniqueId()).ifPresent(skillProfile -> {
                plugin.getSkillManager().getSkills().forEach(skill->{
                    skillProfile.addSkill(skill,1);
                    player.sendMessage("added "+skill.getSkillID());
                });
            });
        }
    }
}
