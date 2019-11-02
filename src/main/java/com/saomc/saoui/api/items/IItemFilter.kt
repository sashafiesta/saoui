/*
 * Copyright (C) 2016-2019 Arnaud 'Bluexin' Solé
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package com.saomc.saoui.api.items

import com.saomc.saoui.api.screens.IIcon
import com.saomc.saoui.util.IconCore
import com.teamwizardry.librarianlib.features.kotlin.Minecraft
import net.minecraft.inventory.IInventory
import net.minecraft.inventory.Slot
import net.minecraft.item.ItemStack

/**
 * This is used to send a specific set of items to be rendered as slots
 */
@FunctionalInterface
interface IItemFilter : (ItemStack, Boolean) -> Boolean, (ItemStack) -> Boolean {

    val displayName: String
    /**
     * @return Returns true if this is a Category or not
     * Categories do not have filters
     */
    val isCategory: Boolean
        get() = false
    val category: IItemFilter?
        get() = null

    val subFilters
        get() = ItemFilterRegister.filters().filter { it.category == this }

    val icon: IIcon
        get() = IconCore.ITEMS

    /**
     * Issues a check to see if an ItemStack matches a filter
     * Note that you will need to add your own filter when using
     * this.
     * Additional note that if isCategory is true, this will not
     * be checked
     *
     * @param stack The item stack to check
     * @param equipped If the item is Equipped or not
     * @return Returns a slot if true
     */
    override operator fun invoke(stack: ItemStack, equipped: Boolean): Boolean { return false }

    override fun invoke(p1: ItemStack) = this(p1, false)

    /**
     * If this filter should appear as a button in
     * the ingame menu
     */
    fun getValidSlots(): Set<Slot> {return hotbarSlots()}

    companion object {
        fun getPlayerSlots(inventory: IInventory, slotID: Int): Set<Slot>{
            return Minecraft().player.inventoryContainer.inventorySlots
                    .filter { slot -> slot.inventory == inventory && slot.slotIndex == slotID }
                    .toSet()
        }

        fun getPlayerSlots(inventory: IInventory, slotID: IntRange): Set<Slot>{
            return Minecraft().player.inventoryContainer.inventorySlots
                    .filter { slot -> slot.inventory == inventory && slotID.contains(slot.slotIndex) }
                    .toSet()
        }

        fun hotbarSlots(): Set<Slot>{
            return getPlayerSlots(Minecraft().player.inventory, 0..8)
        }

    }

}