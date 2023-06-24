package net.brian.heroesskills.bukkit.commands.subcommands;

import net.brian.heroesskills.HeroesSkills;
import net.brian.heroesskills.api.players.PlayerSkillProfile;
import net.brian.heroesskills.bukkit.commands.SubCommand;
import net.brian.heroesskills.bukkit.configs.Language;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DamageNotEnemyCommand extends SubCommand {

    public DamageNotEnemyCommand(HeroesSkills plugin) {
        super(plugin, "DamageNotEnemy");
    }

    // /hs damagenotenemy
    @Override
    public void onCommand(CommandSender sender, String[] args) {
        if(sender instanceof Player player){
            PlayerSkillProfile.get(((Player) sender).getUniqueId()).ifPresent(skillProfile -> {
                skillProfile.damageNotEnemy = !skillProfile.damageNotEnemy;
                if(skillProfile.damageNotEnemy){
                    player.sendMessage(Language.ATTACK_NOT_ENEMY_ENABLED);
                }
                else player.sendMessage(Language.ATTACK_NOT_ENEMY_DISABLED);
            });
        }
    }
}