package net.brian.heroesskills.bukkit.commands.subcommands;

import net.brian.heroesskills.HeroesSkills;
import net.brian.heroesskills.api.players.PlayerSkillProfile;
import net.brian.heroesskills.bukkit.commands.SubCommand;
import net.brian.heroesskills.bukkit.configs.Language;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BuffNotAllyCommand extends SubCommand {

    public BuffNotAllyCommand(HeroesSkills plugin) {
        super(plugin, "BuffNotAlly");
    }

    // /hs buffnotally

    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if(sender instanceof Player player){
            PlayerSkillProfile.get(((Player) sender).getUniqueId()).ifPresent(skillProfile -> {
                skillProfile.buffNotAlly = !skillProfile.buffNotAlly;
                if(skillProfile.buffNotAlly){
                    player.sendMessage(Language.BUFF_ALLY_ENABLED);
                }
                else player.sendMessage(Language.BUFF_ALLY_DISABLED);
            });
        }
    }
}
