@file:Suppress("PackageName")

package org.firstinspires.ftc.teamcode.Systems

import dev.nextftc.core.commands.Command
import dev.nextftc.core.commands.delays.Delay
import dev.nextftc.core.commands.groups.SequentialGroup
import dev.nextftc.core.commands.utility.InstantCommand
import dev.nextftc.core.subsystems.SubsystemGroup
import org.firstinspires.ftc.teamcode.Next.Shooter.FlyWheel
import org.firstinspires.ftc.teamcode.Next.Shooter.Hood
import org.firstinspires.ftc.teamcode.Lower.Gate.Gate
import org.firstinspires.ftc.teamcode.Lower.Intake.Intake

// Pre-built commands for autonomous

object ShooterCommands : SubsystemGroup(FlyWheel, Hood, Gate) {
    
    // Shoot with velocity - replaces FlyWheel.setVelocity() + Hood.mid() + Gate.open/close
    fun shoot(velocity: Double): Command = SequentialGroup(
        InstantCommand { FlyWheel.setVelocity(velocity) },
        InstantCommand { Hood.mid() },
        Delay(0.4),
        InstantCommand { Gate.open() },
        Delay(0.5),
        InstantCommand { Gate.close() },
        InstantCommand { FlyWheel.setVelocity(0.0) }
    )
    
    // Pre-built shoot commands
    val shootFar: Command = shoot(1900.0)
    val shootMid: Command = shoot(1500.0)
    val shootClose: Command = shoot(1000.0)
}

object IntakeCommands : SubsystemGroup(Intake) {
    
    // Run intake - replaces Intake.run()
    val run: Command = InstantCommand { Intake.run() }
    
    // Stop intake - replaces Intake.stop()
    val stop: Command = InstantCommand { Intake.stop() }
    
    // Run intake for X seconds
    fun runFor(seconds: Double): Command = SequentialGroup(
        InstantCommand { Intake.run() },
        Delay(seconds),
        InstantCommand { Intake.stop() }
    )
    
    // Reverse intake
    val reverse: Command = InstantCommand { Intake.reverse() }
}

object GateCommands : SubsystemGroup(Gate) {
    
    val open: Command = InstantCommand { Gate.open() }
    val close: Command = InstantCommand { Gate.close() }
}

object HoodCommands : SubsystemGroup(Hood) {
    
    val close: Command = InstantCommand { Hood.close() }
    val mid: Command = InstantCommand { Hood.mid() }
    val far: Command = InstantCommand { Hood.far() }
}
