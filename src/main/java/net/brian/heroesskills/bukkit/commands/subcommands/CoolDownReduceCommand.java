package net.brian.heroesskills.bukkit.commands.subcommands;

import net.brian.heroesskills.HeroesSkills;
import net.brian.heroesskills.api.players.PlayerSkillProfile;
import net.brian.heroesskills.api.players.SkillData;
import net.brian.heroesskills.bukkit.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CoolDownReduceCommand extends SubCommand {

    public CoolDownReduceCommand(HeroesSkills plugin) {
        super(plugin, "rcd");
    }

    // /hs rcd <player> <skill> <seconds>
    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if(!sender.hasPermission("HeroesSkill.admin")) return;
        if(args.length <3 ) return;
        Player target = Bukkit.getPlayer(args[1]);
        if(target == null) {
            sender.sendMessage("找不到玩家"+args[1]);
            return;
        }
        PlayerSkillProfile.get(target.getUniqueId()).ifPresent(skillProfile -> {
            SkillData skillData = skillProfile.getSkillData(args[2]);
            if(!skillData.isEmpty()){
                if(args.length >= 4){
                    skillData.lastCast -= 1000*Double.parseDouble(args[3]);
                }
                else skillData.lastCast = 0;
            }
        });
    }
}
