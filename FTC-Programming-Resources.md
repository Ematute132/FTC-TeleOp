# FTC Programming Resources

## Overview

This document compiles resources for FTC (FIRST Tech Challenge) robotics programming, focusing on modern Kotlin-based approaches.

---

## 1. Official FTC SDK

**Repository:** https://github.com/FIRST-Tech-Challenge/FtcRobotController

- **Current Season:** DECODE (2025-2026)
- **Min Android Studio:** Ladybug (2024.2)
- **Language:** Java/Kotlin
- **Documentation:** https://ftc-docs.firstinspires.org/

---

## 2. NextFTC (Recommended Modern SDK)

**Website:** https://nextftc.dev/

### What is NextFTC?
- Modern FTC control library written in **Kotlin**
- Simplifies coding with reusable components and intuitive interfaces
- Built on modern software practices (coroutines, DSLs)

### Installation
```groovy
// In TeamCode build.gradle dependencies:
implementation 'dev.nextftc:core:1.0.0'
implementation 'dev.nextftc:hardware:1.0.1'
implementation 'dev.nextftc.extensions:pedro:1.0.0'
```

### Key Features
- Command-based system (similar to WPILib)
- Bulk reading optimizations
- Telemetry panels
- Bindings system for gamepad inputs

### Documentation
- v0 docs: https://v0.nextftc.dev/
- Extension docs: https://nextftc.dev/extensions/

---

## 3. PedroPathing (GVF Path Following)

**Website:** https://pedropathing.com/
**GitHub:** https://github.com/Pedro-Pathing

### What is PedroPathing?
- **GVF (Generalized Vector Field)** path planning library
- Originally by FTC Team 10158 (Scott's Bots)
- Provides smoother, more accurate path following than traditional methods
- Includes **centripetal force correction**

### Key Concepts

```java
// Basic path creation
Path path = follower.pathBuilder()
    .addPath(new BezierLine(startPose, endPose))
    .setLinearHeadingInterpolation(startPose.getHeading(), endPose.getHeading())
    .build();

// Follow the path
follower.followPath(path);
```

### Tuning PIDF
- Video guide: https://www.youtube.com/watch?v=vihb2LPtSK0
- Critical for smooth movement

### NextFTC Integration
```groovy
implementation 'dev.nextftc.extensions:pedro:1.0.0'
```

---

## 4. FTCLib (Alternative Command System)

**Website:** https://ftclib.org/
**GitHub:** https://github.com/FTCLib/FTCLib
**Docs:** https://docs.ftclib.org/

### What is FTCLib?
- Port of WPILib (FRC standard) for FTC
- Command-based programming paradigm
- Trajectory following built-in

### Installation
```groovy
implementation 'com.ftclib:ftclib:3.0.0'
```

### Command System Basics
- **Subsystems** - Hardware abstractions (motors, sensors)
- **Commands** - Actions that run on subsystems
- **Command Groups** - Sequential or parallel execution

---

## 5. Quickstart Templates

| Library | Quickstart |
|---------|------------|
| NextFTC | https://github.com/NextFTC/NextFTC |
| PedroPathing | https://github.com/Pedro-Pathing/Quickstart |
| FTCLib | https://github.com/FTCLib/FTCLib-Quickstart |
| PedroPathing + Panels | https://github.com/Pedro-Pathing/Quickstart |

---

## 6. Current Season: DECODE (2025-2026)

- **Game Reveal:** September 2025
- **Key Focus:** Check official FTC game manual for rules
- **SDK Version:** 9.x (DECODE)

---

## 7. Recommended Stack

For a modern FTC robot:

```
Language:     Kotlin
SDK:          NextFTC
Pathing:      PedroPathing
Commands:     NextFTC built-in (or FTCLib)
Hardware:     Modern DC motors, encoders, sensors
```

---

## 8. Key YouTube Tutorials

- **PedroPathing Full Course:** https://www.youtube.com/playlist?list=PLRHdgFNRLyaMYGJtBMSFEpxNdMvuJOai6
- **How to Write FTC Auto with PedroPathing:** https://www.youtube.com/watch?v=gdkefs_VL-w

---

## 9. Code Structure Example (NextFTC)

```kotlin
class MyOpMode : NextFTCOpMode() {
    override fun onInit() {
        // Initialize hardware, subsystems
    }
    
    override fun onStartButtonPressed() {
        // Start autonomous actions
    }
    
    override fun onUpdate() {
        // Periodic loop (telemetry, etc.)
    }
}
```

---

## 10. Useful Links

| Resource | URL |
|----------|-----|
| FTC Docs | https://ftc-docs.firstinspires.org/ |
| NextFTC | https://nextftc.dev/ |
| PedroPathing | https://pedropathing.com/ |
| FTCLib | https://ftclib.org/ |
| KotlinFTC (samples) | https://github.com/BrandonPacewic/KotlinFTC |

---

## 11. LimeLight 3A (Vision)

**Website:** https://limelightvision.io/products/limelight-3a
**Docs:** https://docs.limelightvision.io/docs-limelight/
**FTC Javadoc:** https://javadoc.io/doc/org.firstinspires.ftc/Hardware/latest/com/qualcomm/hardware/limelightvision/package-summary.html

### Hardware Specs
- **Processor:** Quad-core Cortex-A72 @ 1.5GHz
- **Resolution:** 90 FPS @ 640x480, 60 FPS @ 1280x960
- **Mounting:** REV/GoBilda threaded holes, VHB tape, or zip ties
- **Connection:** USB-C to Control Hub

### Key Features
- AprilTag tracking & robot localization
- Color blob detection
- Neural network / custom Python pipelines
- Full-field pose estimation (MegaTag1 & MegaTag2)
- Browser-based configuration

---

### Basic Setup (FTC)

```kotlin
import com.qualcomm.hardware.limelightvision.*

// Initialize
limelight = hardwareMap.get(Limelight3A::class.java, "limelight")
limelight.setPollRateHz(100)  // Query rate
limelight.start()

// Get results
val result = limelight.latestResult
if (result != null && result.isValid) {
    val tx = result.tx  // Horizontal offset (degrees)
    val ty = result.ty  // Vertical offset (degrees)
    val ta = result.ta  // Target area (0-100%)
    val botpose = result.botpose  // Robot pose (MegaTag)
}
```

### Steering/PID Control

LimeLight doesn't have built-in PID in the sensor — you implement steering in code:

```kotlin
// Simple P-controller for steering
val steer = tx * kP  // kP typically 0.03-0.05

// Full PID example
class SteeringCommand(val kP: Double, val kI: Double, val kD: Double) {
    private var lastError = 0.0
    private var integral = 0.0
    
    fun calculate(tx: Double): Double {
        val error = tx
        integral += error
        val derivative = error - lastError
        lastError = error
        
        return kP * error + kI * integral + kD * derivative
    }
}

// Usage: steer = steeringCommand.calculate(tx)
```

**Typical Values:**
| Parameter | Range | Description |
|-----------|-------|-------------|
| kP | 0.02 - 0.08 | Proportional gain |
| kI | 0.0 - 0.01 | Integral gain (often 0) |
| kD | 0.1 - 0.5 | Derivative gain |

### Pipeline Settings (Web Interface)

| Setting | Description |
|---------|-------------|
| **Pipeline Type** | AprilTags, Neural Networks, Color, Python |
| **Camera Exposure** | Lower = less motion blur, more noise |
| **Threshold** | Color/contrast thresholds for detection |
| **Quality Threshold** | Filters spurious detections |
| **Full 3D** | Enables botPose (robot localization) |
| **Camera Offset** | Position relative to robot center |

### MegaTag2 (Pose Fusion)

MegaTag2 fuses IMU data with AprilTag for better accuracy:

```kotlin
// Update with IMU heading
val robotYaw = imu.angularOrientation.firstAngle
limelight.updateRobotOrientation(robotYaw)

// Get fused pose
val botpose = result.botpose
```

### NetworkTable Values

Key values available via NetworkTables:

| Key | Description |
|-----|-------------|
| `tx` | Horizontal offset (degrees) |
| `ty` | Vertical offset (degrees) |
| `ta` | Target area (%) |
| `tid` | AprilTag ID |
| `botpose` | Robot position (x, y, z, roll, pitch, yaw) |
| `pipeline` | Current pipeline index |

### Tuning Tips

1. **Exposure:** Set as low as possible while still detecting
2. **Quality Threshold:** Increase to filter noise
3. **LED Brightness:** Match to environment
4. **Multiple Tags:** Use for redundancy, single for speed

---

*Last updated: Feb 2026*
