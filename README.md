# HeroesSkills
A skill system for any mmorpg server.
Create custom skill paths and gain skills points to learn active or passive skills. 

Features:
- RoadMaps for players to learn and unlock skills.
- Supports [MythicMobs](https://www.spigotmc.org/resources/%E2%9A%94-mythicmobs-free-version-%E2%96%BAthe-1-custom-mob-creator%E2%97%84.5702/)
skills.
- Detects click sequence(RRR, RLR, RLL, RRL) on certain items to cast skills.
- Allows players to bind skills to certain click sequence

```kt
var passiveSkill = PassiveStatSkill(
    "技能id",
    "&c血量增幅", //顯示名稱
    10, //最大等級
    "MAX_HEALTH", //素質 ID
    { player, data -> 5.0+data.level*2 }, //素質增加的量
    { player, data -> Icon() //底下設定物品
        .setMaterial(Material.REDSTONE_BLOCK) // 材質
        .setModelData(0) // model
        .setDisplayName("&c血量增幅 Lv.${data.level}") // 物品名稱
        .addLore( // 物品的 lore
            "&7增加 ${5+data.level*2} 的最大血量",
            "&e${data.level}/10"
        )
    }
);
```
```kt
var activeSkill = MythicActiveSkill(
    "MythicMobs的技能ID", //同時也是這個技能的id
"顯示名稱",
    5, //最大等級
    10.0, //魔力消耗
    20.0  //冷卻  秒數
) { player, data -> //底下設定物品
    Icon()
        .setMaterial(Material.REDSTONE_BLOCK) // 材質
        .setModelData(0) // model
        .setDisplayName("&c血量增幅 ${data.level}") // 物品名稱
        .addLore( // 物品的 lore
            "&7增加 ${10 + data.level} 的最大血量",
            "&e${data.level}/10"
        )
}
```
register it
```kt
HeroesSkills.getInstance().mainPathGui.registerElement(
    PathElement(
        passiveSkill,
        mutableMapOf("health" to 9)
    )
)
HeroesSkills.getInstance().skillManager.register(passiveSkill);
```

Checkout for other builtin skill classes [here](https://github.com/MagicSwordLand/HeroesSkills/tree/main/src/main/java/net/brian/heroesskills/core/skills).
