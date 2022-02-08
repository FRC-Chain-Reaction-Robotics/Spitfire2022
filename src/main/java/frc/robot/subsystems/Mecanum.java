package frc.robot.subsystems;

import static com.revrobotics.CANSparkMax.IdleMode.*;
import static com.revrobotics.CANSparkMaxLowLevel.MotorType.kBrushless;
import static edu.wpi.first.wpilibj.SPI.Port.kOnboardCS0;

// import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.*;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
public class Mecanum extends SubsystemBase
{
	protected CANSparkMax lf = new CANSparkMax(Constants.LF_MOTOR_ID, kBrushless);
	protected CANSparkMax lb = new CANSparkMax(Constants.LB_MOTOR_ID, kBrushless);
	protected CANSparkMax rf = new CANSparkMax(Constants.RF_MOTOR_ID, kBrushless);
	protected CANSparkMax rb = new CANSparkMax(Constants.RB_MOTOR_ID, kBrushless);
	
    protected RelativeEncoder lfEncoder = lf.getEncoder();
    protected RelativeEncoder lbEncoder = lb.getEncoder();
    protected RelativeEncoder rfEncoder = rf.getEncoder();
    protected RelativeEncoder rbEncoder = rb.getEncoder();
    
	protected Gyro gyro = new ADXRS450_Gyro(kOnboardCS0);
	// AHRS gyro = new AHRS();

	MecanumDrive md = new MecanumDrive(lf, lb, rf, rb);

	public static final double SLOW_MODE_SPEED = 0.25;
	// public static final double AUTON_SPEED = 0.3;
	public static final double AUTON_SPEED = 0.37;
	public static final double TELEOP_SPEED = 0.75;

	/** Creates a new ExampleSubsystem. */
	public Mecanum()
	{
		md.setMaxOutput(TELEOP_SPEED); // spitfire
		md.setDeadband(0.05);

		resetGyro();
		resetEncoders();
        
        rfEncoder.setPositionConversionFactor(0.0454);
        rbEncoder.setPositionConversionFactor(0.0454);
        lfEncoder.setPositionConversionFactor(0.0454);
		lbEncoder.setPositionConversionFactor(0.0454);
		
        rf.setInverted(true);
        rb.setInverted(true);

		lb.setIdleMode(kCoast);
		rf.setIdleMode(kCoast);
		rb.setIdleMode(kCoast);
		lf.setIdleMode(kCoast);

		lf.burnFlash();
		lb.burnFlash();
		rf.burnFlash();
		rb.burnFlash();

	//	SmartDashboard.putData((Sendable) gyro);
	}

	/**
   * Drive method for Mecanum platform.
   *
   * <p>Angles are measured clockwise from the positive X axis. The robot's speed is independent
   * from its angle or rotation rate.
   *
   * @param ySpeed The robot's speed along the Y axis [-1.0..1.0]. Right is positive.
   * @param xSpeed The robot's speed along the X axis [-1.0..1.0]. Forward is positive.
   * @param zRotation The robot's rotation rate around the Z axis [-1.0..1.0]. Clockwise is positive.
   */
	public void drive(double ySpeed, double xSpeed, double zRotation)
	{
		md.driveCartesian(ySpeed, xSpeed, zRotation);
	}
	
	/**
   * Drive method for Mecanum platform.
   *
   * <p>Angles are measured clockwise from the positive X axis. The robot's speed is independent
   * from its angle or rotation rate.
   *
   * @param ySpeed The robot's speed along the Y axis [-1.0..1.0]. Right is positive.
   * @param xSpeed The robot's speed along the X axis [-1.0..1.0]. Forward is positive.
   * @param zRotation The robot's rotation rate around the Z axis [-1.0..1.0]. Clockwise is positive.
   * @param maxOutput The maximum output value of the drivetrain. Everything will be capped off at this value.s
   */
	public void drive(double ySpeed, double xSpeed, double zRotation, double maxOutput)
	{
        System.out.println("HEY!");
		md.setMaxOutput(maxOutput);
		drive(ySpeed, xSpeed, zRotation);
	}

	public void fieldOrientedDrive(double ySpeed, double xSpeed, double zRotation, double maxOutput)
	{
		md.setMaxOutput(maxOutput);
		md.driveCartesian(ySpeed, xSpeed, zRotation, gyro.getAngle());
	}

	public void resetGyro()
	{
		gyro.reset();
		gyro.calibrate();
	}

	public void resetEncoders()
    {
        lbEncoder.setPosition(0);
        rbEncoder.setPosition(0);
        lfEncoder.setPosition(0);
        rfEncoder.setPosition(0);
	}

	public double getDistance()
	{
		return lfEncoder.getPosition();
	}

	public double getAngle()
	{
		return gyro.getAngle();
	}

	public void setMaxOutput(double maxOutput)
	{
		md.setMaxOutput(maxOutput);
	}

	@Override
	public void periodic()
	{
		SmartDashboard.putNumber("lf position", lfEncoder.getPosition());
		SmartDashboard.putNumber("rf position", rfEncoder.getPosition());
		SmartDashboard.putNumber("lb position", lbEncoder.getPosition());
		SmartDashboard.putNumber("rb position", rbEncoder.getPosition());

		SmartDashboard.putNumber("rb Velocity", rbEncoder.getVelocity());
		SmartDashboard.putNumber("lb Velocity", lbEncoder.getVelocity());
		SmartDashboard.putNumber("lf Velocity", lfEncoder.getVelocity());
		SmartDashboard.putNumber("rf Velocity", rfEncoder.getVelocity());
	}
}