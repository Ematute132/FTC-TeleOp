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

*Last updated: Feb 2026*
