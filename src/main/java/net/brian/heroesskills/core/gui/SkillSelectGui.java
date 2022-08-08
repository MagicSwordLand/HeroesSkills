package net.brian.heroesskills.core.gui;

import net.brian.heroesskills.HeroesSkills;
import net.brian.heroesskills.api.gui.GuiElement;
import net.brian.heroesskills.api.gui.PageGui;
import net.brian.heroesskills.api.players.PlayerSkillProfile;
import net.brian.heroesskills.api.players.SkillData;
import net.brian.heroesskills.api.skills.ActiveSkill;
import net.brian.heroesskills.api.skills.SkillManager;
import net.brian.heroesskills.api.skills.casting.ClickSequence;
import net.brian.heroesskills.core.utils.Icon;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class SkillSelectGui extends PageGui {


    ItemStack RLL = Icon.create(Material.BOOK,"&e點擊設定RLL主動技");
    ItemStack RRR = Icon.create(Material.BOOK,"&e點擊設定RRR主動技");
    ItemStack RRL = Icon.create(Material.BOOK,"&e點擊設定RRL主動技");
    ItemStack RLR = Icon.create(Material.BOOK,"&e點擊設定RLR主動技");


    private HashMap<Integer,GuiElement> elements = new HashMap<>();
    private final SkillManager skillManager;


    @Override
    protected List<Inventory> getPages(HumanEntity humanEntity) {
        List<Inventory> pages = new ArrayList<>();
        Optional<PlayerSkillProfile> optSkillProfile = PlayerSkillProfile.get(humanEntity.getUniqueId());
        if(optSkillProfile.isEmpty()) {
            humanEntity.sendMessage("資料載入中");
            return pages;
        }
        PlayerSkillProfile skillProfile = optSkillProfile.get();
        Inventory firstPage = Bukkit.createInventory(null,27);
        pages.add(firstPage);
        elements.forEach((slot,element)->firstPage.setItem(slot,element.getDisplay(skillProfile)));


        Iterator<Map.Entry<String, SkillData>> it = skillProfile.getSkills().iterator();
        int skillIndex = 0;
        Inventory inv = null;
        while (it.hasNext()){
            Map.Entry<String,SkillData> entry = it.next();
            if(skillIndex % 45 == 0){
                inv = Bukkit.createInventory(null,54);
                skillIndex = 0;
                pages.add(inv);
            }

            Optional<ActiveSkill> skill= skillManager.getActiveSkill(entry.getKey());
            if(skill.isPresent()) {
                inv.setItem(skillIndex, skill.get().getDisplay(skillProfile, entry.getValue()));
                skillIndex++;
            }
        }
        if(inv == null){
            humanEntity.sendMessage("你當前沒有任何主動技能");
        }

        return pages;
    }

    @Override
    protected void onClick(InventoryClickEvent event, Cache cache) {
        if(event.getClickedInventory() == null || !event.getClickedInventory().getType().equals(InventoryType.CHEST)) return;
        if(cache.currentPage == 0){
            GuiElement element = elements.get(event.getSlot());
            element.getAction().accept(event);
            return;
        }
        String pickedSkillID = Icon.read(event.getCurrentItem(),"SkillID");
        if(pickedSkillID == null) {
            return;
        }
        Optional<ActiveSkill> pickedSkill = skillManager.getActiveSkill(pickedSkillID);
        if(pickedSkill.isEmpty()) {
            return;
        }
        PlayerSkillProfile.get(event.getWhoClicked().getUniqueId()).ifPresent(profile->{
            SelectCache selectCache = (SelectCache) getCache(event.getWhoClicked());
            profile.setButtonSkill(selectCache.clickSequence, pickedSkill.get());
            refresh(event.getWhoClicked());
            firstPage(event.getWhoClicked());
        });
    }

    @Override
    protected SelectCache newCache(HumanEntity humanEntity) {
        return new SelectCache();
    }


    private static class SelectCache extends Cache{
        ClickSequence clickSequence = ClickSequence.RRL;
    }

    public SkillSelectGui(HeroesSkills plugin, SkillManager skillManager)  {
        Bukkit.getPluginManager().registerEvents(this,plugin);
        this.skillManager = skillManager;
        elements.put(10,get(ClickSequence.RRL,RRL));
        elements.put(12,get(ClickSequence.RRR,RRR));
        elements.put(14,get(ClickSequence.RLL,RLL));
        elements.put(16,get(ClickSequence.RLR,RLR));
    }

    private GuiElement get(ClickSequence clickSequence,ItemStack itemStack){
        GuiElement element = new GuiElement();
        element.setDisplay(skillProfile -> {
            Optional<ActiveSkill> abstractSkill = skillProfile.getButtonSkill(clickSequence);
            if(abstractSkill.isEmpty()) return itemStack;
            SkillData skillData = skillProfile.getSkillData(abstractSkill.get().getSkillID());
            if(skillData.isEmpty()) return itemStack;
            return abstractSkill.get().getDisplay(skillProfile,skillData);
        });
        element.setAction(event -> {
            if(event.isLeftClick()){
                ((SelectCache)getCache(event.getWhoClicked())).clickSequence = clickSequence;
                turnPage(event.getWhoClicked(),1);
            }
            else PlayerSkillProfile.get(event.getWhoClicked().getUniqueId()).ifPresent(data->{
                data.setButtonSkill(clickSequence,null);
                refresh(event.getWhoClicked());
            });
        });
        return element;
    }


    private void refresh(HumanEntity humanEntity){
        Cache cache = getCache(humanEntity);
        if(cache != null && !cache.pages.isEmpty()){
            Inventory inv = cache.pages.get(0);
            PlayerSkillProfile.get(humanEntity.getUniqueId()).ifPresent(skillProfile -> {
                elements.forEach((slot,element)->inv.setItem(slot,element.getDisplay(skillProfile)));
            });
        }
    }

}
