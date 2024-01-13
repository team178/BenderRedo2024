package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ArmConstants;
import frc.robot.commands.arm.ArmFF;
import frc.robot.commands.arm.ArmPID;
import frc.robot.commands.arm.ArmPosition;

public class Arm extends SubsystemBase {
    private CANSparkMax m_lowerMotor;
    private CANSparkMax m_upperMotor;

    private DutyCycleEncoder m_lowerEncoder;
    private DutyCycleEncoder m_upperEncoder;

    private ArmPID m_lowerPIDController;
    private ArmPID m_upperPIDController;
    private ArmFF m_lowerFFController;
    private ArmFF m_upperFFController;

    private DigitalInput m_lowerLimit;
    private DigitalInput m_upperLimit;

    private ArmPosition m_armPosition;

    private boolean toSetpoint;

    public Arm() {
        ArmConstants.initArmPreferences();
        Preferences.initDouble("kLowerTestOutput", 0.1);
        Preferences.initDouble("kUpperTestOutput", 0.1);

        m_lowerMotor = new CANSparkMax(ArmConstants.kLowerSparkPort, MotorType.kBrushless);
        m_lowerEncoder = new DutyCycleEncoder(ArmConstants.kLowerEncoderChannel);
        m_lowerLimit = new DigitalInput(ArmConstants.kLowerLimitChannel);
        m_lowerPIDController = new ArmPID(
            ArmConstants.getKLowerP(),
            ArmConstants.getKLowerI(),
            ArmConstants.getKLowerD()
        );
        m_lowerFFController = new ArmFF(
            ArmConstants.getKLowerS(),
            ArmConstants.getKLowerG(),
            ArmConstants.getKLowerV(),
            ArmConstants.getKLowerA()
        );

        m_upperMotor = new CANSparkMax(ArmConstants.kUpperSparkPort, MotorType.kBrushless);
        m_upperEncoder = new DutyCycleEncoder(ArmConstants.kUpperEncoderChannel);
        m_upperLimit = new DigitalInput(ArmConstants.kUpperLimitChannel);
        m_upperPIDController = new ArmPID(
            ArmConstants.getKUpperP(),
            ArmConstants.getKUpperI(),
            ArmConstants.getKUpperD()
        );
        m_upperFFController = new ArmFF(
            ArmConstants.getKUpperS(),
            ArmConstants.getKUpperG(),
            ArmConstants.getKUpperV(),
            ArmConstants.getKUpperA()
        );

        setArmPosition(ArmPosition.HOME);

        m_lowerMotor.restoreFactoryDefaults();
        m_upperMotor.restoreFactoryDefaults();
        
        //! double check
        m_lowerMotor.setInverted(false);
        m_upperMotor.setInverted(false);
        
        setBrake();

        //! double check how encoder class works
        // m_lowerEncoder.reset();
        // m_upperEncoder.reset();

        toSetpoint = false;
        
    }

    public void updateToPreferences() {
        m_lowerPIDController.setP(ArmConstants.getKLowerP());
        m_lowerPIDController.setI(ArmConstants.getKLowerI());
        m_lowerPIDController.setD(ArmConstants.getKLowerD());

        m_lowerFFController.setS(ArmConstants.getKLowerS());
        m_lowerFFController.setG(ArmConstants.getKLowerG());
        m_lowerFFController.setV(ArmConstants.getKLowerV());
        m_lowerFFController.setA(ArmConstants.getKLowerA());

        m_upperPIDController.setP(ArmConstants.getKUpperP());
        m_upperPIDController.setI(ArmConstants.getKUpperI());
        m_upperPIDController.setD(ArmConstants.getKUpperD());

        m_upperFFController.setS(ArmConstants.getKUpperS());
        m_upperFFController.setG(ArmConstants.getKUpperG());
        m_upperFFController.setV(ArmConstants.getKUpperV());
        m_upperFFController.setA(ArmConstants.getKUpperA());
    }

    public double getLowerPosition() {
        return m_lowerEncoder.getAbsolutePosition() * 360;
    }

    public double getUpperPosition() {
        return m_upperEncoder.getAbsolutePosition() * 360;
    }

    public void setBrake() {
        m_lowerMotor.setIdleMode(IdleMode.kBrake);
        m_upperMotor.setIdleMode(IdleMode.kBrake);
    }

    public void setCoast() {
        m_lowerMotor.setIdleMode(IdleMode.kCoast);
        m_upperMotor.setIdleMode(IdleMode.kCoast);
    }

    public void setArmPosition(ArmPosition armPosition) {
        m_armPosition = armPosition;
        m_lowerPIDController.resetIntegralError();
        m_upperPIDController.resetIntegralError();
        m_lowerPIDController.setSetpoint(m_armPosition.getLowerSetpoint());
        m_upperPIDController.setSetpoint(m_armPosition.getUpperSetpoint());
    }

    public CommandBase runLowerTest() {
        return runOnce(() -> {
            m_lowerMotor.setVoltage(Preferences.getDouble("kLowerTestOutput", 0));
            System.out.println("Lower motor test speed on.");
        });
    }

    public CommandBase runUpperTest() {
        return runOnce(() -> {
            m_upperMotor.setVoltage(Preferences.getDouble("kUpperTestOutput", 0));
            System.out.println("Upper motor test speed on.");
        });
    }

    public CommandBase runCutMotors() {
        return runOnce(() -> {
            m_lowerMotor.setVoltage(0);
            m_upperMotor.setVoltage(0);
            System.out.println("Arm motors off.");
        });
    }

    public CommandBase runToSetpoint() {
        return runOnce(() -> {
            toSetpoint = true;
            System.out.println("On setpoint.");
        });
    }

    public CommandBase runGetOffSetpoint() {
        return runOnce(() -> {
            toSetpoint = false;
            m_lowerMotor.setVoltage(0);
            m_upperMotor.setVoltage(0);
            System.out.println("Off setpoint.");
        });
    }
    
    public CommandBase runSetArmPosition(ArmPosition newPosition) {
        return runOnce(() -> {
            setArmPosition(newPosition);
        });
    }

    public CommandBase runBumpLower(double bump) {
        return run(() -> {
            m_lowerPIDController.resetIntegralError();
            m_lowerPIDController.setSetpoint(m_lowerPIDController.getSetpoint() + bump);
        });
    } 

    public CommandBase runBumpUpper(double bump) {
        return run(() -> {
            m_upperPIDController.resetIntegralError();
            m_upperPIDController.setSetpoint(m_upperPIDController.getSetpoint() + bump);
        });
    } 
    
    @Override
    public void periodic() {
        updateToPreferences();
        SmartDashboard.putNumber("Lower Raw Position", m_lowerEncoder.getAbsolutePosition());
        SmartDashboard.putNumber("Upper Raw Position", m_upperEncoder.getAbsolutePosition());
        SmartDashboard.putNumber("Lower Position", getLowerPosition());
        SmartDashboard.putNumber("Upper Position", getUpperPosition());
        SmartDashboard.putNumber("Lower Pos Offset", m_lowerEncoder.getPositionOffset());
        SmartDashboard.putNumber("Upper Pos Offset", m_upperEncoder.getPositionOffset());

        SmartDashboard.putBoolean("Lower Home", m_lowerLimit.get());
        SmartDashboard.putBoolean("Upper Home", m_upperLimit.get());

        SmartDashboard.putNumber("Lower Setpoint", m_lowerPIDController.getSetpoint());
        SmartDashboard.putNumber("Upper Setpoint", m_upperPIDController.getSetpoint());

        double lowerPIDOutput = m_lowerPIDController.calculate(getLowerPosition());
        double upperPIDOutput = m_upperPIDController.calculate(getUpperPosition());
        
        double lowerRadians = Units.degreesToRadians(m_lowerPIDController.getSetpoint() - ArmConstants.kLowerOffset);
        double upperRadians = Units.degreesToRadians(m_upperPIDController.getSetpoint() - ArmConstants.kUpperOffset);
        SmartDashboard.putNumber("LowerRadians", Math.cos(Units.degreesToRadians(getLowerPosition() - ArmConstants.kLowerOffset)));
        SmartDashboard.putNumber("UpperRadians", Math.cos(Units.degreesToRadians(getUpperPosition() - ArmConstants.kUpperOffset)));
        double lowerFFOutput = m_lowerFFController.calculate(lowerRadians, 0);
        double upperFFOutput = m_upperFFController.calculate(upperRadians, 0);

        SmartDashboard.putNumber("Lower PID Output", lowerPIDOutput);
        SmartDashboard.putNumber("Upper PID Output", upperPIDOutput);
        SmartDashboard.putNumber("Lower FF Output", lowerFFOutput);
        SmartDashboard.putNumber("Upper FF Output", upperFFOutput);

        if(toSetpoint) {
            m_lowerMotor.setVoltage(MathUtil.clamp(lowerPIDOutput + lowerFFOutput, -ArmConstants.kLowerMaxVolts, ArmConstants.kLowerMaxVolts));
            m_upperMotor.setVoltage(MathUtil.clamp(upperPIDOutput + upperFFOutput, -ArmConstants.kUpperMaxVolts, ArmConstants.kUpperMaxVolts));
        }
    }

    
    
}
