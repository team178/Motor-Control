// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import libs.IO.ConsoleController; 
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX; 

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {


  private static final int kMotorPort1 = 0;
  private static final int kMotorPort2= 0;
  private static final int kMotorPort3 = 0;
  private static final int kMotorPort4 = 0;
  private static final int kJoystickPort = 0;

  private TalonSRX m_motor1;
  private TalonSRX m_motor2;
  private TalonSRX m_motor3;
  private TalonSRX m_motor4;

  private ConsoleController m_joystick;

  private Command m_autonomousCommand;

  private RobotContainer m_robotContainer;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    // Instantiate our RobotContainer.  This will perform all our button bindings, and put our
    // autonomous chooser on the dashboard.
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

    m_motor1 = new TalonSRX(kMotorPort1);
    m_motor2 = new TalonSRX(kMotorPort2);
    m_motor3 = new TalonSRX(kMotorPort3);
    m_motor4 = new TalonSRX(kMotorPort4);
    
    m_joystick = new ConsoleController(kJoystickPort);

    m_robotContainer = new RobotContainer();

  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {

    int motorControlOption = 0;

    if(motorControlOption == 0){
      m_motor1.set(ControlMode.PercentOutput, m_joystick.getLeftStickY());
      m_motor2.set(ControlMode.PercentOutput, m_joystick.getLeftStickX());
      m_motor3.set(ControlMode.PercentOutput, m_joystick.getRightStickY());
      m_motor4.set(ControlMode.PercentOutput, m_joystick.getRightStickX());
    }
    else if(motorControlOption == 1){
      m_motor1.set(ControlMode.PercentOutput, m_joystick.getLeftStickY());
      m_motor2.set(ControlMode.PercentOutput, m_joystick.getLeftStickY());
      m_motor3.set(ControlMode.PercentOutput, m_joystick.getLeftStickY());
      m_motor4.set(ControlMode.PercentOutput, m_joystick.getLeftStickY());
    }

  }
}
