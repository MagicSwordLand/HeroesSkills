package net.brian.heroesskills.bukkit.commands.subcommands;

import net.brian.heroesskills.HeroesSkills;
import net.brian.heroesskills.api.players.PlayerSkillProfile;
import net.brian.heroesskills.bukkit.commands.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Optional;

public class SkillPointCommand extends SubCommand {

    public SkillPointCommand(HeroesSkills plugin) {
        super(plugin, "SkillPoint");
    }

    // /hs skillpoint add player 1 <source>
    // /hs skillpoint reset player
    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if(!sender.hasPermission("HeroesSkills.admin")) return;
        if(args.length < 3) return;
        Player player = Bukkit.getPlayer(args[2]);
        if(player == null){
            sender.sendMessage("找不到該玩家");
            return;
        }
        Optional<PlayerSkillProfile> skillProfile = PlayerSkillProfile.get(player.getUniqueId());
        if(skillProfile.isEmpty()) {
            player.sendMessage("你的資料尚未載入完畢，還不能進行技能點配置");
            return;
        }
        if(args[1].equalsIgnoreCase("reset")) {
            skillProfile.get().resetSkillPoints();
        }
        if(args[1].equalsIgnoreCase("add")){
            int amount = 1;
            String source = "tempt";
            if(args.length >= 4){
                amount = Integer.parseInt(args[3]);
                if(args.length >= 5){
                    source = args[4];
                }
                skillProfile.get().giveSkillPoint(amount,source);
            }
        }
    }
}
