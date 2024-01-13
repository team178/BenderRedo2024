// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.arm.ArmPosition;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Claw;
import edu.wpi.first.wpilibj.Preferences;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {

  private final CommandXboxController m_driverController = new CommandXboxController(OperatorConstants.kDriverControllerPort);
  private final CommandXboxController m_auxController = new CommandXboxController(OperatorConstants.kAuxDriverControllerPort);

  private Arm m_arm;
  private Claw m_claw;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    Preferences.removeAll();
    m_arm = new Arm();
    m_claw = new Claw();
    // Configure the trigger bindings
    configureBindings();
  }

  /**
   * Use this method to define your trigger->command mappings. Triggers can be created via the
   * {@link Trigger#Trigger(java.util.function.BooleanSupplier)} constructor with an arbitrary
   * predicate, or via the named factories in {@link
   * edu.wpi.first.wpilibj2.command.button.CommandGenericHID}'s subclasses for {@link
   * CommandXboxController Xbox}/{@link edu.wpi.first.wpilibj2.command.button.CommandPS4Controller
   * PS4} controllers or {@link edu.wpi.first.wpilibj2.command.button.CommandJoystick Flight
   * joysticks}.
   */
  private void configureBindings() {
    m_auxController.leftBumper().onTrue(m_claw.runToggleClaw());

    m_auxController.leftStick().onTrue(m_arm.runLowerTest());
    m_auxController.leftStick().onFalse(m_arm.runCutMotors());
    m_auxController.rightStick().onTrue(m_arm.runUpperTest());
    m_auxController.rightStick().onFalse(m_arm.runCutMotors());

    m_auxController.rightTrigger().onTrue(m_arm.runToSetpoint());
    m_auxController.rightTrigger().onFalse(m_arm.runGetOffSetpoint());

    m_auxController.b().onTrue(m_arm.runSetArmPosition(ArmPosition.HOME));
    m_auxController.y().onTrue(m_arm.runSetArmPosition(ArmPosition.SUBSTATION));
    // m_auxController.a().onTrue(m_arm.runSetArmPosition(ArmPosition.LOW));
    // m_auxController.x().onTrue(m_arm.runSetArmPosition(ArmPosition.HIGH));
    m_auxController.rightBumper().onTrue(m_arm.runSetArmPosition(ArmPosition.BACK));

    m_auxController.povUpLeft().whileTrue(m_arm.runBumpLower(-0.1));
    m_auxController.povDownLeft().whileTrue(m_arm.runBumpLower(0.1));

    m_auxController.povUpRight().whileTrue(m_arm.runBumpUpper(-0.1));
    m_auxController.povDownRight().whileTrue(m_arm.runBumpUpper(0.1));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An example command will be run in autonomous
    return Commands.run(() -> {});
  }
}
