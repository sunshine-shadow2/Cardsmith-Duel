package com.cardsmithduel.content.cards;

import com.cardsmithduel.common.BaseCard;
import com.cardsmithduel.api.CardEffect;
import com.cardsmithduel.content.effects.SpeedEffect;

/**
 * 迅捷卡牌
 *
 * 中文名称：迅捷卡牌
 * 英文名称：Swiftness Card
 *
 * 物品ID：cardsmithduel:swiftness_card
 *
 * 物品属性：
 *   - 堆叠数量：1（不可叠加）
 *
 * 作用说明：
 *   - 右键使用后获得 2 分钟的迅捷效果（移动速度提升 I）
 *   - 效果持续时间：120 秒（120 * 20 ticks）
 *   - 使用后消耗 1 张卡牌
 *
 * 效果类型：速度提升 (Speed)
 * 对应药水：迅捷药水 (Potion of Swiftness)
 *
 * 合成配方：
 *   - 材料：空白卡牌 + 迅捷药水 + 糖
 *   - 形状：
 *     [ 空白卡牌 ]
 *     [ 药水 ][ 糖 ][ 药水 ]
 *   - JSON文件：swiftness_card.json
 *
 * 获取方式：
 *   - 命令：/give @s cardsmithduel:swiftness_card
 *   - 合成：工作台合成
 *   - 创意模式标签页：卡牌匠造・对决
 *
 * 继承关系：
 *   - BaseCard（基类） -> SwiftnessCard
 *   - BaseCard 使用 SpeedEffect（效果实现）
 */
public class SwiftnessCard extends BaseCard {
    private static final CardEffect EFFECT = new SpeedEffect();

    public SwiftnessCard() {
        super(EFFECT, 1);
    }
}