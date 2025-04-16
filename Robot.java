// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;
import edu.wpi.first.wpilibj.motorcontrol.VictorSP;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;

/**
 * The methods in this class are called automatically corresponding to each
 * mode, as described in
 * the TimedRobot documentation. If you change the name of this class or the
 * package after creating
 * this project, you must also update the Main.java file in the project.
 */
public class Robot extends TimedRobot {

  private final RobotContainer m_robotContainer;
  private static final int kJoystickPort = 1;
  private Command m_autonomousCommand;

  private static final int kCoralGrabberIntake = 3;
  private static final int kCoralGrabberPivot = 7;
  private static final int kIntakeArmRight = 1;
  private static final int kIntakeArmLeft = 2;
  private static final int kIntakeMotorPort = 0;
  private static final int kElevatorRight = 5;
  private static final int kElevatorLeft = 6;


  private final XboxController m_joystick;
  private final PWMSparkMax m_elevatorright;
  private final PWMSparkMax m_elevatorleft;
  /*
   * private final VictorSP m_coralgrabberintake;
   * private final VictorSP m_coralgrabberpivot;
   */
  private final VictorSP m_intakearmright;
  private final VictorSP m_intakearmleft;
  private final VictorSP m_intakemotor;

  /** Called once at the beginning of the robot program. */
  public Robot() {
    m_intakearmright = new VictorSP(kIntakeArmRight);
    m_intakearmleft = new VictorSP(kIntakeArmLeft);
    m_intakemotor = new VictorSP(kIntakeMotorPort);
    m_elevatorleft = new PWMSparkMax(kElevatorLeft);
    m_elevatorright = new PWMSparkMax(kElevatorRight);
    /*
     * m_motor = new VictorSP(kMotorPort);
     * m_motor = new VictorSP(kMotorPort);
     */
    m_joystick = new XboxController(kJoystickPort);
    /**
     * This function is run when the robot is first started up and should be used
     * for any
     * initialization code.
z
     */

    // Instantiate our RobotContainer. This will perform all our button bindings,
    // and put our
    // autonomous chooser on the dashboard.
    m_robotContainer = new RobotContainer();
  }

  @Override
  public void teleopPeriodic() {
    if (m_joystick.getRightBumperButton()) {
      m_intakemotor.set(1.0); // Set motor to full speed
    } else if (m_joystick.getLeftBumperButton()) {
      m_intakemotor.set(-1.0); // Set motor to reverse
    } else {
      m_intakemotor.set(0.0); // Stop the motor
    }
    /*
     * Intake Arm
     * The intake arm is controlled by the left joystick Y-axis.
     * The left joystick Y-axis is inverted
     */
    {
      m_intakearmleft.set(m_joystick.getLeftY() * -0.5); // Set motor to full speed
      m_intakearmright.set(m_joystick.getLeftY() * 0.5);
    }
    /*
     */

    /*
     * Elevator Down
     * The elevator Down is controlled by the left trigger.
     */
    {
      m_elevatorleft.set(m_joystick.getLeftTriggerAxis() * -0.5); // Set motor to full speed
      m_elevatorright.set(m_joystick.getLeftTriggerAxis() * 0.5);
      /*
       * Elevator Up
       * The elevator Up is controlled by the right trigger.
       */
      m_elevatorleft.set(m_joystick.getRightTriggerAxis() * -0.5); // Set motor to full speed
      m_elevatorright.set(m_joystick.getRightTriggerAxis() * 0.5);
    }

  }

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

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items
   * like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>
   * This runs after the mode specific periodic functions, but before LiveWindow
   * and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
    // Runs the Scheduler. This is responsible for polling buttons, adding
    // newly-scheduled
    // commands, running already-scheduled commands, removing finished or
    // interrupted commands,
    // and running subsystem periodic() methods. This must be called from the
    // robot's periodic
    // block in order for anything in the Command-based framework to work.
    CommandScheduler.getInstance().run();
  }

  /** This function is called once each time the robot enters Disabled mode. */
  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
  }

  /**
   * This autonomous runs the autonomous command selected by your
   * {@link RobotContainer} class.
   */
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
  public void autonomousPeriodic() {
  }

  /** This function is called periodically during operator control. */

  @Override
  public void testInit() {
    // Cancels all running commands at the start of test mode.
    CommandScheduler.getInstance().cancelAll();
  }

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {
  }

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {
  }

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {
  }
}