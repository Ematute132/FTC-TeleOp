package org.firstinspires.ftc.teamcode.Autonomoous.Red

import com.bylazar.configurables.annotations.Configurable
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
import org.firstinspires.ftc.teamcode.Lower.Drive.Drive
import org.firstinspires.ftc.teamcode.Lower.Gate.Gate
import org.firstinspires.ftc.teamcode.Lower.Intake.Intake
import org.firstinspires.ftc.teamcode.Next.Shooter.FlyWheel
import org.firstinspires.ftc.teamcode.Next.Shooter.Turret
import org.firstinspires.ftc.teamcode.Shooter.Hood.Hood
import org.firstinspires.ftc.teamcode.Shooter.Limelight.Limelight
import org.firstinspires.ftc.teamcode.pedroPathing.Constants

@Configurable
@Autonomous(name = "21-ball Blue", group = "Competition", preselectTeleOp = "TeleOp - Blue")
class AutoBlue21 : NextFTCOpMode() {
    companion object {
        @JvmField var preloadVelocity: Double = 1900.0
        @JvmField var preloadShootDelay: Double = 0.5
        @JvmField var shotShootDelay: Double = 0.5
        @JvmField var shotIntakeDelay: Double = 2.0
    }

    init {
        addComponents(
            PedroComponent(Constants::createFollower),
            SubsystemComponent(
                Drive, Intake, Gate, FlyWheel, Turret, Hood, Limelight
            ),
            BulkReadComponent, BindingsComponent
        )
    }

    private var paths: Array<PathChain> = arrayOf()

    override fun onInit() {
        Turret.alliance = Turret.Alliance.BLUE
    }

    override fun onStartButtonPressed() {
        follower.setStartingPose(Pose(56.000, 9.000, Math.toRadians(180.0)))
        buildPaths()

        val main = SequentialGroup(
            // Shoot preload from starting score pose
            InstantCommand { FlyWheel.setVelocity(preloadVelocity) },
            InstantCommand { Hood.far() },
            InstantCommand { Turret.lock() },
            Delay(0.4),
            InstantCommand { Gate.open() },
            InstantCommand { Intake.run() },
            Delay(preloadShootDelay),
            InstantCommand { Gate.close() },

            // intakelow: drive to low balls, intake, return, shoot
            FollowPath(paths[0], true, 1.0),
            InstantCommand { Intake.run() },
            Delay(shotIntakeDelay),
            InstantCommand { Intake.stop() },
            FollowPath(paths[1], true, 1.0),
            InstantCommand { Hood.far() },
            Delay(0.4),
            InstantCommand { Gate.open() },
            InstantCommand { Intake.run() },
            Delay(shotShootDelay),
            InstantCommand { Gate.close() },

            // intakespike: drive to spike balls, intake, return, shoot
            FollowPath(paths[2], true, 1.0),
            InstantCommand { Intake.run() },
            Delay(shotIntakeDelay),
            InstantCommand { Intake.stop() },
            FollowPath(paths[3], true, 1.0),
            InstantCommand { Hood.far() },
            Delay(0.4),
            InstantCommand { Gate.open() },
            InstantCommand { Intake.run() },
            Delay(shotShootDelay),
            InstantCommand { Gate.close() },

            // intakehigh: drive to high balls, intake, return, shoot
            FollowPath(paths[4], true, 1.0),
            InstantCommand { Intake.run() },
            Delay(shotIntakeDelay),
            InstantCommand { Intake.stop() },
            FollowPath(paths[5], true, 1.0),
            InstantCommand { Hood.far() },
            Delay(0.4),
            InstantCommand { Gate.open() },
            InstantCommand { Intake.run() },
            Delay(shotShootDelay),
            InstantCommand { Gate.close() },

            // intakemid: drive to mid balls, intake, return, shoot
            FollowPath(paths[6], true, 1.0),
            InstantCommand { Intake.run() },
            Delay(shotIntakeDelay),
            InstantCommand { Intake.stop() },
            FollowPath(paths[7], true, 1.0),
            InstantCommand { Hood.far() },
            Delay(0.4),
            InstantCommand { Gate.open() },
            InstantCommand { Intake.run() },
            Delay(shotShootDelay),
            InstantCommand { Gate.close() },

            InstantCommand { FlyWheel.setVelocity(0.0) }
        )
        main.schedule()
    }

    private fun buildPaths() {
        paths = arrayOf()

        // Path 0: score pose → low balls
        val intakelow = follower.pathBuilder()
            .addPath(BezierLine(Pose(56.000, 9.000), Pose(10.000, 9.000)))
            .setLinearHeadingInterpolation(Math.toRadians(180.0), Math.toRadians(180.0))
            .build()

        // Path 1: low balls → score pose
        val scorePose1 = follower.pathBuilder()
            .addPath(BezierLine(Pose(10.000, 9.000), Pose(56.000, 9.000)))
            .setLinearHeadingInterpolation(Math.toRadians(180.0), Math.toRadians(180.0))
            .build()

        // Path 2: score pose → spike balls
        val intakespike = follower.pathBuilder()
            .addPath(BezierCurve(
                Pose(56.000, 9.000),
                Pose(44.500, 40.000),
                Pose(13.800, 36.300)
            ))
            .setLinearHeadingInterpolation(Math.toRadians(180.0), Math.toRadians(180.0))
            .build()

        // Path 3: spike balls → score pose
        val scorePose2 = follower.pathBuilder()
            .addPath(BezierLine(Pose(13.800, 36.300), Pose(56.000, 9.000)))
            .setLinearHeadingInterpolation(Math.toRadians(180.0), Math.toRadians(180.0))
            .build()

        // Path 4: score pose → high balls
        val intakehigh = follower.pathBuilder()
            .addPath(BezierLine(Pose(56.000, 9.000), Pose(10.000, 18.500)))
            .setLinearHeadingInterpolation(Math.toRadians(180.0), Math.toRadians(180.0))
            .build()

        // Path 5: high balls → score pose
        val scorePose3 = follower.pathBuilder()
            .addPath(BezierLine(Pose(10.000, 18.500), Pose(56.000, 9.000)))
            .setLinearHeadingInterpolation(Math.toRadians(180.0), Math.toRadians(180.0))
            .build()

        // Path 6: score pose → mid balls
        val intakemid = follower.pathBuilder()
            .addPath(BezierLine(Pose(56.000, 9.000), Pose(10.000, 13.750)))
            .setLinearHeadingInterpolation(Math.toRadians(180.0), Math.toRadians(180.0))
            .build()

        // Path 7: mid balls → score pose
        val scorePose4 = follower.pathBuilder()
            .addPath(BezierLine(Pose(10.000, 13.750), Pose(56.000, 9.000)))
            .setLinearHeadingInterpolation(Math.toRadians(180.0), Math.toRadians(180.0))
            .build()

        paths += intakelow
        paths += scorePose1
        paths += intakespike
        paths += scorePose2
        paths += intakehigh
        paths += scorePose3
        paths += intakemid
        paths += scorePose4
    }

    override fun onUpdate() {
        Drive.update()
        telemetry.update()
    }

    override fun onStop() {
        InstantCommand { Intake.stop() }.schedule()
        InstantCommand { Gate.close() }.schedule()
        InstantCommand { FlyWheel.setVelocity(0.0) }.schedule()
        follower.pose = Drive.lastKnown
    }
}
