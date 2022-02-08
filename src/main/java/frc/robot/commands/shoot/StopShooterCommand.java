package frc.robot.commands.shoot;

import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.CommandBase;

/** An example command that uses an example subsystem. */
public class StopShooterCommand extends CommandBase
{
    Shooter shooter;

    public StopShooterCommand(Shooter shooter)
    {
        this.shooter = shooter;
		addRequirements(shooter);
	}

	// Called every time the scheduler runs while the command is scheduled.
	@Override
    public void execute()
    {
        shooter.stop();
    }

	@Override
    public boolean isFinished()
    {
        return shooter.atSetpoint();
	}
}
