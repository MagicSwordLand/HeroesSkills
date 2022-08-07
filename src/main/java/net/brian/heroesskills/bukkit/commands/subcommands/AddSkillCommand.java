package net.brian.heroesskills.bukkit.commands.subcommands;

import net.brian.heroesskills.HeroesSkills;
import net.brian.heroesskills.api.players.PlayerSkillProfile;
import net.brian.heroesskills.api.skills.AbstractSkill;
import net.brian.heroesskills.bukkit.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;

public class AddSkillCommand extends SubCommand {


    public AddSkillCommand(HeroesSkills plugin) {
        super(plugin, "add");
    }

    // /hs add <player> <skill> <amount>
    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if(sender.hasPermission("HeroesSkill.admin")) return;
        if(args.length < 3) return;
        Player player = Bukkit.getPlayer(args[1]);
        if(player == null){
            sender.sendMessage("找不到該玩家"+args[1]);
            return;
        }
        Optional<AbstractSkill> abstractSkill = plugin.getSkillManager().get(args[2]);
        if(abstractSkill.isEmpty()) {
            sender.sendMessage("找不到技能"+args[2]);
            return;
        }

        PlayerSkillProfile.get(player.getUniqueId()).ifPresent(skillProfile -> {
            int amount = 1;
            if(args.length >= 4 ) amount = Integer.parseInt(args[3]);
            skillProfile.addSkill(abstractSkill.get(),amount);
        });
    }
}
