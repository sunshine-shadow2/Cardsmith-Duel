package com.cardsmithduel.common;

import com.cardsmithduel.api.CardEffect;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

/**
 * 卡牌基类
 *
 * 继承自 Item，封装所有功能卡牌的通用逻辑
 * 所有功能卡牌都需要继承此类
 */
public abstract class BaseCard extends Item {
    /** 卡牌效果，通过组合模式实现 */
    private final CardEffect effect;

    /**
     * 构造函数（默认可堆叠64个）
     *
     * @param effect 卡牌使用的效果
     */
    protected BaseCard(CardEffect effect) {
        this(effect, 64);
    }

    /**
     * 构造函数（自定义堆叠数量）
     *
     * @param effect        卡牌使用的效果
     * @param maxStackSize 最大堆叠数量（1 = 不可叠加）
     */
    protected BaseCard(CardEffect effect, int maxStackSize) {
        super(new Item.Properties().stacksTo(maxStackSize));
        this.effect = effect;
    }

    /**
     * 右键使用卡牌
     *
     * 逻辑：
     * 1. 获取玩家手中的卡牌物品
     * 2. 服务端执行效果应用（客户端不执行，防止作弊）
     * 3. 消耗1个卡牌物品
     *
     * @param level  世界（主世界/下界/末地等维度）
     * @param player 使用卡牌的玩家
     * @param hand   使用的手（主手/副手）
     * @return 交互结果
     */
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide) {
            effect.apply(player);
            stack.shrink(1);
        }
        return InteractionResultHolder.consume(stack);
    }

    /**
     * 鼠标悬停时显示的提示文本
     *
     * 自动根据物品ID查找对应的翻译key并显示
     * 例如：cardsmithduel:swiftness_card 会查找 item.cardsmithduel.swiftness_card.tooltip
     *
     * @param stack             物品堆栈
     * @param level             世界（主世界/下界/末地等维度）
     * @param tooltipComponents 提示文本列表（会被添加到UI显示）
     * @param flag              提示标志（决定显示方式）
     */
    @Override
    public void appendHoverText(ItemStack stack, Level level, List<Component> tooltipComponents, TooltipFlag flag) {
        String key = "item.cardsmithduel." + this.getDescriptionId() + ".tooltip";
        tooltipComponents.add(Component.translatable(key));
    }
}