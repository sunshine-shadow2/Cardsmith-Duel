package com.cardsmithduel.backpack;

import com.cardsmithduel.common.BaseCard;
import com.cardsmithduel.registry.ModMenus;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;

/**
 * 卡牌背包容器菜单类
 * 
 * 负责管理背包界面中的所有物品槽位，包括：
 * - 背包专属的27个格子（3行9列）
 * - 玩家物品栏的27个格子（3行9列）
 * - 玩家快捷栏的9个格子（1行9列）
 */
public class CardBackpackMenu extends AbstractContainerMenu {
    
    /** 背包物品处理器，管理27个专属存储格子 */
    private final ItemStackHandler backpackInventory;
    
    /** 玩家物品栏引用，用于访问玩家的物品栏和快捷栏 */
    private final Inventory playerInventory;
    
    /**
     * 背包格子起始索引
     * 
     * 所有背包格子从索引0开始排列。
     * 索引范围：[0, 27)
     */
    private static final int BACKPACK_START_SLOT = 0;
    
    /**
     * 背包格子结束索引（exclusive）
     * 
     * 背包共27个格子，索引范围为0-26。
     * 此值为结束索引，不包含在有效范围内。
     * 计算方式：9列 × 3行 = 27个格子
     */
    private static final int BACKPACK_END_SLOT = 27;
    
    /**
     * 玩家物品栏起始索引
     * 
     * 玩家物品栏从索引27开始（紧接着背包格子之后）。
     * 索引范围：[27, 54)
     * 
     * Minecraft玩家物品栏结构：
     * - 索引27-53：主物品栏（3行9列）
     * - 索引0-8：快捷栏（1行9列）
     */
    private static final int PLAYER_INVENTORY_START = 27;
    
    /**
     * 玩家物品栏结束索引（exclusive）
     * 
     * 玩家物品栏共27个格子，索引范围为27-53。
     * 此值为结束索引，不包含在有效范围内。
     * 
     * @see #PLAYER_INVENTORY_START
     */
    private static final int PLAYER_INVENTORY_END = 54;

    /**
     * 从网络缓冲区创建菜单实例（客户端调用）
     * 
     * 当客户端从服务端接收菜单open packet时调用此构造函数。
     * 此时还没有背包数据，需要服务端发送完整数据。
     *
     * @param containerId    容器ID，用于标识此菜单实例
     * @param playerInventory 玩家物品栏引用
     * @param buf            网络数据缓冲区
     */
    public CardBackpackMenu(int containerId, Inventory playerInventory, FriendlyByteBuf buf) {
        this(containerId, playerInventory, new ItemStackHandler(CardBackpack.SLOT_COUNT));
    }

    /**
     * 创建菜单实例（通用构造函数）
     * 
     * 初始化背包容器，建立背包格子与玩家物品栏之间的连接。
     *
     * @param containerId         容器ID
     * @param playerInventory     玩家物品栏引用
     * @param backpackInventory   背包物品处理器
     */
    public CardBackpackMenu(int containerId, Inventory playerInventory, ItemStackHandler backpackInventory) {
        super(ModMenus.CARD_BACKPACK_MENU.get(), containerId);
        this.backpackInventory = backpackInventory;
        this.playerInventory = playerInventory;

        addBackpackSlots(backpackInventory);
        addPlayerInventorySlots(playerInventory);
    }

    /**
     * 添加背包格子（3行9列布局）
     * 
     * 布局说明：
     * - 第1行：索引0-8，y坐标为18
     * - 第2行：索引9-17，y坐标为36
     * - 第3行：索引18-26，y坐标为54
     * 
     * 每个格子间距18像素，符合Minecraft标准UI间距。
     * 只接受BaseCard类型的物品（卡牌）。
     *
     * @param handler 背包物品处理器
     */
    private void addBackpackSlots(IItemHandler handler) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                int index = col + row * 9;
                int x = 8 + col * 18;
                int y = 18 + row * 18;
                this.addSlot(new SlotItemHandler(handler, index, x, y) {
                    @Override
                    public boolean mayPlace(ItemStack stack) {
                        return stack.getItem() instanceof BaseCard;
                    }
                });
            }
        }
    }

    /**
     * 添加玩家物品栏和快捷栏格子
     * 
     * 玩家物品栏布局：
     * - 主物品栏（3行9列）：索引27-53，起始y坐标84
     * - 快捷栏（1行9列）：索引0-8，起始y坐标142
     * 
     * @param playerInv 玩家物品栏引用
     */
    private void addPlayerInventorySlots(Inventory playerInv) {
        // 添加玩家主物品栏（3行9列）
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                int slotIndex = col + row * 9 + 9; // +9 跳过快捷栏
                int x = 8 + col * 18;
                int y = 84 + row * 18;
                this.addSlot(new Slot(playerInv, slotIndex, x, y));
            }
        }

        // 添加玩家快捷栏（1行9列）
        for (int col = 0; col < 9; col++) {
            int x = 8 + col * 18;
            int y = 142; // 快捷栏y坐标
            this.addSlot(new Slot(playerInv, col, x, y));
        }
    }

    /**
     * 处理快速移动物品逻辑（Shift+左键点击）
     * 
     * 当玩家使用Shift+点击时，物品会在背包和玩家物品栏之间自动移动：
     * - 从背包移动到玩家物品栏（索引0-26 → 索引27-53）
     * - 从玩家物品栏移动到背包（仅限卡牌物品）
     * 
     * 移动规则：
     * 1. 如果物品槽索引 < 27，视为背包格子，目标为玩家物品栏
     * 2. 如果物品槽索引 >= 27，视为玩家物品栏，目标为背包
     * 3. 只有BaseCard类型的物品才能从玩家物品栏移动到背包
     *
     * @param player 执行操作的玩家
     * @param index  被点击的槽位索引
     * @return 被移动的物品堆栈，如果移动失败则返回空物品
     */
    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);

        if (slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();

            if (index < BACKPACK_END_SLOT) {
                // 从背包移动到玩家物品栏
                // moveItemStackTo参数：物品、起始索引、结束索引、是否反向（true=优先填充满堆叠）
                if (!this.moveItemStackTo(itemstack1, PLAYER_INVENTORY_START, PLAYER_INVENTORY_END, true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                // 从玩家物品栏移动到背包（仅限卡牌）
                if (itemstack1.getItem() instanceof BaseCard) {
                    if (!this.moveItemStackTo(itemstack1, BACKPACK_START_SLOT, BACKPACK_END_SLOT, false)) {
                        return ItemStack.EMPTY;
                    }
                } else {
                    // 非卡牌物品不能放入背包
                    return ItemStack.EMPTY;
                }
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, itemstack1);
        }

        return itemstack;
    }

    /**
     * 检查菜单是否仍然对玩家有效
     * 
     * 当玩家死亡或退出世界时，此方法返回false，
     * 菜单将自动关闭。
     *
     * @param player 要检查的玩家
     * @return 如果菜单对玩家仍然有效则返回true
     */
    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    /**
     * 获取背包物品处理器
     * 
     * 用于外部访问背包的物品存储。
     *
     * @return 背包物品处理器
     */
    public ItemStackHandler getBackpackInventory() {
        return backpackInventory;
    }
}
