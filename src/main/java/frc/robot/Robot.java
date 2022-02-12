// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import libs.IO.ConsoleController;

import java.util.Map;
import java.util.function.DoubleSupplier;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {

  //might need to change the port numbers, guessed these numbers
  private static final int kMotorPort1 = 1;
  private static final int kMotorPort2 = 2;
  private static final int kMotorPort3 = 3;
  private static final int kMotorPort4 = 4;

  private static final int kJoystickPort = 0;

  private WPI_TalonSRX m_motor1;
  private WPI_TalonSRX m_motor2;
  private WPI_TalonSRX m_motor3;
  private WPI_TalonSRX m_motor4;

  private ConsoleController m_joystick;

  private Command m_autonomousCommand;

  private SendableChooser<DoubleSupplier> m_motor1_axis = new SendableChooser<>();
  private SendableChooser<DoubleSupplier> m_motor2_axis = new SendableChooser<>();
  private SendableChooser<DoubleSupplier> m_motor3_axis = new SendableChooser<>();
  private SendableChooser<DoubleSupplier> m_motor4_axis = new SendableChooser<>();

  private NetworkTableEntry m_motor1_speed;
  private NetworkTableEntry m_motor2_speed;
  private NetworkTableEntry m_motor3_speed;
  private NetworkTableEntry m_motor4_speed;

  private RobotContainer m_robotContainer;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
    
    m_motor1 = new WPI_TalonSRX(kMotorPort1);
    m_motor2 = new WPI_TalonSRX(kMotorPort2);
    m_motor3 = new WPI_TalonSRX(kMotorPort3);
    m_motor4 = new WPI_TalonSRX(kMotorPort4);
    
    m_joystick = new ConsoleController(kJoystickPort);

    // Don't need a robot container
    // m_robotContainer = new RobotContainer();

    // Shuffleboard Motor control pickers

    ShuffleboardTab motorControlTab = Shuffleboard.getTab("Motor Control");


    m_motor1_axis.setDefaultOption("None", () -> 0.0);
    m_motor1_axis.addOption("Left Stick X", m_joystick::getLeftStickX);
    m_motor1_axis.addOption("Left Stick Y", m_joystick::getLeftStickY);
    m_motor1_axis.addOption("Right Stick X", m_joystick::getRightStickX);
    m_motor1_axis.addOption("Right Stick Y", m_joystick::getRightStickY);
    m_motor1_axis.addOption("Left Trigger", m_joystick::getLeftTrigger);
    m_motor1_axis.addOption("Right Trigger", m_joystick::getRightTrigger);

    motorControlTab
    .add("Motor 1", m_motor1_axis)
      .withSize(2, 1)
        .withPosition(0, 0);

    m_motor1_speed = motorControlTab
    .add("Max Speed for Motor 1", 1)
      .withWidget(BuiltInWidgets.kNumberSlider)
        .withProperties(Map.of("min", -1, "max", 1))
          .withPosition(0, 1)
            .withSize(2, 1)
              .getEntry();

    m_motor2_axis.setDefaultOption("None", () -> 0.0);
    m_motor2_axis.addOption("Left Stick Y", m_joystick::getLeftStickY);
    m_motor2_axis.addOption("Left Stick X", m_joystick::getLeftStickX);
    m_motor2_axis.addOption("Right Stick X", m_joystick::getRightStickX);
    m_motor2_axis.addOption("Right Stick Y", m_joystick::getRightStickY);
    m_motor2_axis.addOption("Left Trigger", m_joystick::getLeftTrigger);
    m_motor2_axis.addOption("Right Trigger", m_joystick::getRightTrigger);

    motorControlTab
    .add("Motor 2", m_motor2_axis)
      .withSize(2, 1)
        .withPosition(2, 0);

    m_motor2_speed = motorControlTab
    .add("Max Speed for Motor 2", 1)
      .withWidget(BuiltInWidgets.kNumberSlider)
        .withProperties(Map.of("min", -1, "max", 1))
          .withPosition(2, 1)
            .withSize(2, 1)
             .getEntry();

    m_motor3_axis.setDefaultOption("None", () -> 0.0);
    m_motor3_axis.addOption("Left Stick X", m_joystick::getLeftStickX);
    m_motor3_axis.addOption("Left Stick Y", m_joystick::getLeftStickY);
    m_motor3_axis.addOption("Right Stick X", m_joystick::getRightStickX);
    m_motor3_axis.addOption("Right Stick Y", m_joystick::getRightStickY);
    m_motor3_axis.addOption("Left Trigger", m_joystick::getLeftTrigger);
    m_motor3_axis.addOption("Right Trigger", m_joystick::getRightTrigger);

    motorControlTab
    .add("Motor 3", m_motor3_axis)
      .withSize(2, 1)
        .withPosition(4, 0);

    m_motor3_speed = motorControlTab
    .add("Max Speed for Motor 3", 1)
      .withWidget(BuiltInWidgets.kNumberSlider)
        .withProperties(Map.of("min", -1, "max", 1))
          .withPosition(4, 1)
            .withSize(2, 1)
              .getEntry();

    m_motor4_axis.setDefaultOption("None", () -> 0.0);
    m_motor4_axis.addOption("Left Stick X", m_joystick::getLeftStickX);
    m_motor4_axis.addOption("Left Stick Y", m_joystick::getLeftStickY);
    m_motor4_axis.addOption("Right Stick X", m_joystick::getRightStickX);
    m_motor4_axis.addOption("Right Stick Y", m_joystick::getRightStickY);
    m_motor4_axis.addOption("Left Trigger", m_joystick::getLeftTrigger);
    m_motor4_axis.addOption("Right Trigger", m_joystick::getRightTrigger);

    motorControlTab
    .add("Motor 4", m_motor4_axis)
      .withSize(2, 1)
        .withPosition(6, 0);

    m_motor4_speed = motorControlTab
    .add("Max Speed for Motor 4", 1)
      .withWidget(BuiltInWidgets.kNumberSlider)
        .withProperties(Map.of("min", -1, "max", 1))
          .withPosition(6, 1)
            .withSize(2, 1)
              .getEntry();
  }

  /**
   * This function is called every robot packet, no matter the mode. Use this for items like
   * diagnostics that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler.  This is responsible for polling buttons, adding newly-scheduled
    // commands, running already-scheduled commands, removing finished or interrupted commands,
    // and running subsystem periodic() methods.  This must be called from the robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  /** This autonomous runs the autonomous command selected by your {@link RobotContainer} class. */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_robotContainer.getAutonomousCommand();

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.schedule();
    }
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {}

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {
    m_motor1.set(m_motor1_axis.getSelected().getAsDouble() * m_motor1_speed.getDouble(1.0));
    m_motor2.set(m_motor2_axis.getSelected().getAsDouble() * m_motor2_speed.getDouble(1.0));
    m_motor3.set(m_motor3_axis.getSelected().getAsDouble() * m_motor3_speed.getDouble(1.0));
    m_motor4.set(m_motor4_axis.getSelected().getAsDouble() * m_motor4_speed.getDouble(1.0));
  }
}
