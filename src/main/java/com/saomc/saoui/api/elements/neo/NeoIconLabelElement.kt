package com.saomc.saoui.api.elements.neo

import com.saomc.saoui.GLCore
import com.saomc.saoui.api.screens.IIcon
import com.saomc.saoui.neo.screens.unaryPlus
import com.saomc.saoui.resources.StringNames
import com.saomc.saoui.util.ColorUtil
import com.teamwizardry.librarianlib.features.helpers.vec
import com.teamwizardry.librarianlib.features.kotlin.plus
import com.teamwizardry.librarianlib.features.math.BoundingBox2D
import com.teamwizardry.librarianlib.features.math.Vec2d
import kotlin.math.max

/**
 * Part of saoui by Bluexin, released under GNU GPLv3.
 *
 * @author Bluexin
 */
open class NeoIconLabelElement(icon: IIcon, open val label: String = "", var width: Int = 84, var height: Int = 18, pos: Vec2d = Vec2d.ZERO) : NeoIconElement(icon, pos) {

    override val boundingBox get() = BoundingBox2D(pos, pos + vec(width, height))

    override var idealBoundingBox: BoundingBox2D
        get() = BoundingBox2D(pos, pos + vec(max(26 + GLCore.glStringWidth(label), width), height))
        set(value) {
            width = value.widthI()
        }

    override fun draw(mouse: Vec2d, partialTicks: Float) { // TODO: scrolling if too many elements
        if (opacity < 0.03 || scale == Vec2d.ZERO) return
        GLCore.pushMatrix()
        if (scale != Vec2d.ONE) GLCore.glScalef(scale.xf, scale.yf, 1f)
        GLCore.color(ColorUtil.multiplyAlpha(getColor(mouse), opacity))
        GLCore.glBindTexture(StringNames.slot)
        GLCore.glTexturedRectV2(pos.x, pos.y, width = width.toDouble(), height = height.toDouble(), srcX = 0.0, srcY = 40.0, srcWidth = 84.0, srcHeight = 18.0)
        val color = ColorUtil.multiplyAlpha(getTextColor(mouse), opacity)
        GLCore.color(color)
        if (icon.rl != null) GLCore.glBindTexture(icon.rl!!)
        icon.glDrawUnsafe(pos + vec(1, 1))
        GLCore.glString(label, pos + vec(22, height / 2), color, centered = true)

        drawChildren(mouse, partialTicks)
        GLCore.popMatrix()
    }

    override val childrenXOffset get() = width + 5

    override var visible: Boolean
        get() = super.visible
        set(value) {
            super.visible = value
            if (value) {
                opacity = 0f
                +basicAnimation(this, "opacity") {
                    from = 0f
                    to = 1f
                    duration = 4f
                }
            }
        }
}