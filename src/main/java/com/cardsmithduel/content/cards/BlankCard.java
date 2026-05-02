package com.cardsmithduel.content.cards;

import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

/**
 * 空白卡牌
 *
 * 中文名称：空白卡牌
 * 英文名称：Blank Card
 *
 * 物品ID：cardsmithduel:blank_card
 *
 * 作用说明：
 *   - 合成材料，用于与其他物品合成功能卡牌
 *   - 本身没有使用效果
 *
 * 合成配方：
 *   - 无（直接通过 /give 命令获取或创意模式标签页获取）
 *
 * 获取方式：
 *   - 命令：/give @s cardsmithduel:blank_card
 *   - 创意模式标签页：卡牌匠造・对决
 */
public class BlankCard extends Item {
    public BlankCard() {
        super(new Item.Properties());
    }

    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltipComponents, TooltipFlag flag) {
        tooltipComponents.add(Component.translatable("item.cardsmithduel.blank_card.tooltip"));
    }
}