// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.shoot.ShootCommand;
import frc.robot.commands.shoot.StopShooterCommand;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.Mecanum;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.POVButton;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();

  private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);

  Mecanum dt = new Mecanum();
 // Saitek flightStick = new Saitek(0);
//	XboxController operatorController = new XboxController(1);
	XboxController driverController = new XboxController(0);

  Shooter shooter = new Shooter();

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {

    dt.register();

    var driveCommand = new RunCommand(() -> dt.drive(
				0,
				driverController.getLeftY(),
				driverController.getRightX(),
				Mecanum.TELEOP_SPEED),
			dt);

      /*
      var rumbleOnCommand = new RunCommand(() -> driverController.setRumble(RumbleType.kLeftRumble, 1.0))
			.alongWith(new RunCommand(() -> driverController.setRumble(RumbleType.kRightRumble, 1.0)));
			
		var rumbleOffCommand = new RunCommand(() -> driverController.setRumble(RumbleType.kLeftRumble, 0.0))
			.alongWith(new RunCommand(() -> driverController.setRumble(RumbleType.kRightRumble, 0.0)));
      
      var shootButton = new POVButton(driverController, 0);
		  var shootReverseButton = new POVButton(driverController, 180);

      shootButton.whileHeld(new ShootCommand(Shooter.RPM_10FTLINE, shooter).alongWith(rumbleOnCommand))
		.or(shootReverseButton.whenActive(new RunCommand(shooter::reverse, shooter)))
					.whenInactive(new StopShooterCommand(shooter).alongWith(rumbleOffCommand));
*/
      dt.setDefaultCommand(driveCommand);

      

    // Configure the button bindings
   // configureButtonBindings();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {}

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_autoCommand;
  }
}
