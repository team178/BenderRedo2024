package frc.robot.commands.arm;

public enum ArmPosition {
    //! These values are not adjusted
    HOME(309.897908, 342.843705),
    SUBSTATION(240.671931, 293.881183),
    // HIGH(2.976432, 3.154062),
    // LOW(3.587507, 4.466632),
    BACK(290, 315);
    
    private double lowerSetpoint;
    private double upperSetpoint;

    private ArmPosition(double lowerSetpoint, double upperSetpoint) {
        this.lowerSetpoint = lowerSetpoint;
        this.upperSetpoint = upperSetpoint;
    }

    public double getLowerSetpoint() {
        return lowerSetpoint;
    }

    public double getUpperSetpoint() {
        return upperSetpoint;
    }
}
