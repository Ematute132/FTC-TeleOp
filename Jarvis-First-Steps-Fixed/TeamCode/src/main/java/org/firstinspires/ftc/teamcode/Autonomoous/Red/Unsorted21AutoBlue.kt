package org.firstinspires.ftc.teamcode.Autonomoous.Red

import com.pedropathing.geometry.BezierCurve
import com.pedropathing.geometry.BezierLine
import com.pedropathing.geometry.Pose
import com.pedropathing.paths.PathChain
import com.qualcomm.robotcore.eventloop.opmode.Autonomous
import dev.nextftc.core.commands.delays.Delay
import dev.nextftc.core.commands.groups.ParallelGroup
import dev.nextftc.core.commands.groups.SequentialGroup
import dev.nextftc.core.commands.utility.InstantCommand
import dev.nextftc.core.components.BindingsComponent
import dev.nextftc.core.components.SubsystemComponent
import dev.nextftc.extensions.pedro.FollowPath
import dev.nextftc.extensions.pedro.PedroComponent
import dev.nextftc.extensions.pedro.PedroComponent.Companion.follower
import dev.nextftc.ftc.NextFTCOpMode
import dev.nextftc.ftc.components.BulkReadComponent
import org.firstinspires.ftc.teamcode.AutoAim.AutoAim
import org.firstinspires.ftc.teamcode.Lower.Drive.Drive
import org.firstinspires.ftc.teamcode.Lower.Gate.Gate
import org.firstinspires.ftc.teamcode.Lower.Intake.Intake
import org.firstinspires.ftc.teamcode.Next.Shooter.FlyWheel
import org.firstinspires.ftc.teamcode.Next.Shooter.Turret
import org.firstinspires.ftc.teamcode.Shooter.Hood.Hood
import org.firstinspires.ftc.teamcode.Shooter.Limelight.Limelight
import org.firstinspires.ftc.teamcode.pedroPathing.Constants
import org.firstinspires.ftc.teamcode.pedroPathing.Constants.createFollower

@Autonomous(name = "Unsorted 21-ball Blue", group = "Unsorted Auto", preselectTeleOp = "Blue TeleOp")
class Unsorted21AutoBlue(): NextFTCOpMode() {
    init {
        addComponents(
            PedroComponent(Constants::createFollower),
            SubsystemComponent(
                Drive, Intake, Gate, FlyWheel, Turret, Hood, Limelight, AutoAim
            ),
            BulkReadComponent, BindingsComponent
        )
    }

    private var paths: Array<PathChain> = arrayOf()

    override fun onInit() {
        Turret.alliance = Turret.Alliance.BLUE
    }

    override fun onStartButtonPressed() {
        follower.setStartingPose(Pose(34.0, 132.9, Math.toRadians(-180.0)))
        buildPaths()

        // Shoot preload
        val shootPreload = SequentialGroup(
            FollowPath(paths[0], true, 1.0),
            Delay(0.6),
            InstantCommand { FlyWheel.setVelocity(1500.0) },
            InstantCommand { Hood.mid() },
            InstantCommand { Gate.open() },
            Delay(0.5),
            InstantCommand { Intake.stop() },
            InstantCommand { FlyWheel.stop() },
            InstantCommand { Gate.close() }
        )

        // Second spike
        val secondSpike = SequentialGroup(
            FollowPath(paths[1], true, 1.0),
            InstantCommand { Intake.run },
            Delay(0.5),
            InstantCommand { Intake.stop },
            Delay(0.3),
            InstantCommand { FlyWheel.setVelocity(1500.0) },
            InstantCommand { Hood.mid() },
            InstantCommand { Gate.open() },
            Delay(0.5),
            InstantCommand { Intake.stop() },
            InstantCommand { FlyWheel.stop() },
            InstantCommand { Gate.close() }
        )

        // First spike
        val firstSpike = SequentialGroup(
            FollowPath(paths[2], true, 1.0),
            InstantCommand { Intake.run },
            Delay(0.5),
            InstantCommand { Intake.stop },
            Delay(0.3),
            InstantCommand { FlyWheel.setVelocity(1500.0) },
            InstantCommand { Hood.mid() },
            InstantCommand { Gate.open() },
            Delay(0.5),
            InstantCommand { Intake.stop() },
            InstantCommand { FlyWheel.stop() },
            InstantCommand { Gate.close() }
        )

        // Third spike
        val thirdSpike = SequentialGroup(
            FollowPath(paths[3], true, 1.0),
            InstantCommand { Intake.run },
            Delay(0.5),
            InstantCommand { Intake.stop },
            Delay(0.3),
            InstantCommand { FlyWheel.setVelocity(1500.0) },
            InstantCommand { Hood.mid() },
            InstantCommand { Gate.open() },
            Delay(0.5),
            InstantCommand { Intake.stop() },
            InstantCommand { FlyWheel.stop() },
            InstantCommand { Gate.close() }
        )

        // Leave
        val leave = FollowPath(paths[4], true, 1.0)

        // Run everything
        val main = SequentialGroup(
            shootPreload,
            secondSpike,
            firstSpike,
            thirdSpike,
            leave
        )
        main.schedule()
    }

    fun buildPaths() {
        paths = arrayOf()

        // Path 0: Start to shoot pose
        val shootpreload = follower.pathBuilder()
            .addPath(BezierLine(Pose(34.000, 132.900), Pose(56.000, 73.000)))
            .setLinearHeadingInterpolation(Math.toRadians(-180.0), Math.toRadians(-157.0))
            .build()

        // Path 1: Shoot to second spike (right side)
        val intakesecondspike = follower.pathBuilder()
            .addPath(BezierLine(Pose(56.000, 73.000), Pose(15.400, 56.200)))
            .setTangentHeadingInterpolation()
            .build()

        // Path 2: Second spike back to shoot
        val shootsecondspike = follower.pathBuilder()
            .addPath(BezierLine(Pose(15.400, 56.200), Pose(56.000, 73.000)))
            .setTangentHeadingInterpolation()
            .setReversed()
            .build()

        // Path 3: Third spike intake
        val intakethirdspike = follower.pathBuilder()
            .addPath(BezierCurve(
                Pose(56.000, 73.000),
                Pose(55.000, 32.000),
                Pose(45.000, 32.000),
                Pose(17.000, 34.000)
            ))
            .setLinearHeadingInterpolation(Math.toRadians(162.0), Math.toRadians(180.0))
            .build()

        // Path 4: Leave
        val leave = follower.pathBuilder()
            .addPath(BezierLine(Pose(17.000, 34.000), Pose(52.500, 72.500)))
            .setTangentHeadingInterpolation()
            .build()

        paths += shootpreload
        paths += intakesecondspike
        paths += shootsecondspike
        paths += intakethirdspike
        paths += leave
    }

    override fun onUpdate() {
        telemetry.update()
    }

    override fun onStop() {
        Intake.stop()
        Gate.close()
        FlyWheel.stop()
    }
}
