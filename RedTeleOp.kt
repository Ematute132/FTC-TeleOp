package org.firstinspires.ftc.teamcode.opmodes.teleop

import dev.nextftc.core.units.deg
import dev.nextftc.core.units.rad

/**
 * Red Alliance TeleOp
 * 
 * Field positions (inches) - adjust based on your field layout:
 * - Goal position: Right side (Red alliance)
 * - Starting position: Left side of field
 */
class RedTeleOp : TeleOpBase(
    isBlue = false,                          // Red alliance
    goalX = 55.0,                            // Red goal X (adjust to your field)
    goalY = 35.0,                            // Red goal Y (adjust to your field)
    resetModeParams = ResetModeParams(
        x = 12.0,                            // Red starting X
        y = 60.0,                            // Red starting Y  
        h = 180.0.deg                        // Red facing left (180°)
    ),
    distanceToTime = { distance ->
        // Tune this formula for your robot's speed
        distance / 40.0                      // 40 inches/second base speed
    }
)
