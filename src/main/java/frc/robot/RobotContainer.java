// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.subsystems.Sensor;
import frc.robot.subsystems.Superstructure;
//import frc.robot.subsystems.Superstructure;
//import frc.robot.subsystems.SensorTwo;
import frc.robot.subsystems.XRPArm;
import frc.robot.subsystems.XRPDrivetrain;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.auton.Auton;
import frc.robot.auton.AutonChooser;
import frc.robot.commands.TankDrive;
import edu.wpi.first.wpilibj.AnalogInput;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final XRPDrivetrain m_xrpDrivetrain = new XRPDrivetrain();

  private final CommandXboxController driver = new CommandXboxController(0);

  //private final Sensor m_sensor = new Sensor();

  //create an object for the auton
  
  private final Command m_autoCommand = new Auton(m_xrpDrivetrain);

  private final Superstructure m_superstructure = new Superstructure(m_xrpDrivetrain, driver.a());

  private final XRPArm m_arm = new XRPArm();

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    mapAutonOptions();
    AutonChooser.putChooser();

  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link edu.wpi.first.wpilibj.GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {

    m_xrpDrivetrain.setDefaultCommand(getTankDriveCommand());
    
    driver.leftTrigger()
      .onTrue(new InstantCommand(() -> m_arm.setAngle(180), m_arm))
      .onFalse(new InstantCommand(() -> m_arm.setAngle(0), m_arm));
      
  }

  private void mapAutonOptions() {

  }


  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */

  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return AutonChooser.getSelectedAuton();
  }

  public Command getTankDriveCommand() {
    return new TankDrive(
      m_xrpDrivetrain, () -> -driver.getLeftY(), () -> -driver.getRightY());
  }

 
  // public Command getSensorTestCommand() {
  //   return Commands.run(() -> System.out.println(leftSensor.getVoltage()));


  // }

   public void startSuperstructure() {
     m_superstructure.start();
   }

   public Runnable superstructureFastPeriodic() {
     return m_superstructure::fastPeriodic;
   }
}
