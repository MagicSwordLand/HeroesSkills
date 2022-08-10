package net.brian.heroesskills.core.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import net.brian.heroesskills.HeroesSkills;
import net.brian.playerdatasync.util.IridiumColorAPI;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import javax.swing.text.StyleContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Icon {

    private Material mat = Material.BOOK;
    private List<String> lore = new ArrayList<>();
    private String display = "未設定名稱";
    private int model = 0;
    private String texture = "";
    private HashMap<String,String> tags = new HashMap<>();


    public ItemStack build(){
        ItemStack itemStack = new ItemStack(mat);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setLore(lore);
        meta.setDisplayName(display);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setCustomModelData(model);
        tags.forEach((k,v)-> {
            meta.getPersistentDataContainer().set(new NamespacedKey(HeroesSkills.getInstance(),k), PersistentDataType.STRING,v);
        });
        if(texture != null){
            SkullBuilder.addTexture(meta,texture);
        }
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public ItemStack build(Player player){
        lore = PlaceholderAPI.setPlaceholders(player,lore);
        display = PlaceholderAPI.setPlaceholders(player,display);
        return build();
    }

    public Icon addTags(String key,String value){
        tags.put(key,value);
        return this;
    }

    public Icon setMaterial(Material mat){
        this.mat = mat;
        return this;
    }

    public Icon setSkullTexture(String texture){
        this.texture = texture;
        return this;
    }

    public Icon setModelData(int modelData){
        this.model = modelData;
        return this;
    }

    public Icon setDisplayName(String display){
        this.display = IridiumColorAPI.process(display);
        return this;
    }

    public Icon addLore(String... lore){
        this.lore.addAll(IridiumColorAPI.process(Arrays.asList(lore)));
        return this;
    }

    public Icon addLore(List<String> lore){
        this.lore.addAll(lore);
        return this;
    }

    public static ItemStack create(Material mat,String... texts){
        ItemStack itemStack = new ItemStack(mat);
        ItemMeta meta = itemStack.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        if(texts.length > 0){
            meta.setDisplayName(IridiumColorAPI.process(texts[0]));
        }
        if(texts.length > 1){
            List<String> lore = new ArrayList<>();
            for(int i=1;i<texts.length;i++){
                lore.add(IridiumColorAPI.process(texts[i]));
            }
            meta.setLore(lore);
        }
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public static String read(ItemStack itemStack,String key){
        if(itemStack == null) return null;
        ItemMeta meta = itemStack.getItemMeta();
        if(meta == null) return null;
        return meta.getPersistentDataContainer().get(new NamespacedKey(HeroesSkills.getInstance(),key),PersistentDataType.STRING);
    }
}
