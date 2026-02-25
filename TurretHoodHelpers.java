// === Turret + Hood aiming helpers ===
// Add to your TeleOp or autonomous code

// Tunables (YOU set these)
private static final double TURRET_CENTER = 0.505; // your center servo pos
private static final double TURRET_RANGE_DEG = 180.0; // total mechanical range in degrees (ex: 180)
private static final double TURRET_MIN = 0.10; // clamp
private static final double TURRET_MAX = 0.90; // clamp

// Hood positions (example). Replace with your tuned values.
private static final double HOOD_NEAR = 0.20;
private static final double HOOD_MID = 0.35;
private static final double HOOD_FAR = 0.55;

private double clamp(double v, double lo, double hi) {
    return Math.max(lo, Math.min(hi, v));
}

// Normalize angle to (-180, 180]
private double wrapDeg(double deg) {
    while (deg <= -180) deg += 360;
    while (deg > 180) deg -= 360;
    return deg;
}

/**
 * Convert turret angle (deg) to servo position around TURRET_CENTER.
 * +deg means turret rotates "right" (clockwise) from center — flip sign if yours is opposite.
 */
private double turretDegToServo(double turretDeg) {
    // 1.0 servo span corresponds to TURRET_RANGE_DEG degrees
    double pos = TURRET_CENTER + (turretDeg / TURRET_RANGE_DEG);
    return clamp(pos, TURRET_MIN, TURRET_MAX);
}

/**
 * Main aim method:
 * - robotHeadingDeg: current robot heading in degrees
 * - desiredGlobalHeadingDeg: where you want the shooter to point in global space
 *
 * turretErrorDeg is how much turret must rotate to compensate robot heading.
 */
private void aimTurretAndHood(double robotHeadingDeg, double desiredGlobalHeadingDeg, double distanceInches) {
    // turret tries to remove robot rotation relative to the global target direction
    double turretErrorDeg = wrapDeg(desiredGlobalHeadingDeg - robotHeadingDeg);
    
    // If your turret goes the wrong direction, flip sign:
    // turretErrorDeg *= -1;
    
    double turretPos = turretDegToServo(turretErrorDeg);
    turret.setPosition(turretPos);

    // Simple hood mapping (replace with your own curve/lookup)
    double hoodPos;
    if (distanceInches < 30) hoodPos = HOOD_NEAR;
    else if (distanceInches < 55) hoodPos = HOOD_MID;
    else hoodPos = HOOD_FAR;
    
    hood.setPosition(hoodPos);
}
