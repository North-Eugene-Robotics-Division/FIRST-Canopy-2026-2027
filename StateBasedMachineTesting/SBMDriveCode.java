package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.robot.Robot;
import java.util.ArrayList;
import com.qualcomm.robotcore.hardware.HardwareDevice;

import org.firstinspires.ftc.teamcode.SBMTestHardware;

@TeleOp(name="SBM_Test_Drive_Code", group="Linear OpMode")

public class SBMTestDriveCode extends LinearOpMode {
	private ElapsedTime runtime = new ElapsedTime();
	
	private int trackCheckTimeRuns = 0;
	private int cycleTime = 1000;
	
	private double checkTime;

	@Override
	public void runOpMode() {
		
		SBMTestHardware robot = new SBMTestHardware(this);
		robot.init();
		
		telemetry.addData("Status", "Initialized");
		telemetry.update();

		// Wait for the game to start (driver presses PLAY)
		waitForStart();
		runtime.reset();
		checkTime = runtime.milliseconds();
		
		// run until the end of the match (driver presses STOP)
		while (opModeIsActive()) {
			telemetry.addData("Status", "Running");
			telemetry.addData("Runtime: ", runtime.milliseconds());
			telemetry.addData("SBM Checks: ", trackCheckTimeRuns);
			ArrayList<SBM.SBMEntry> entries = robot.hardwareSBM.getEntries();
			for (int i=0; i < entries.size();, i++){
				telemetry.addData("", entries.get(i).toString);
			}
			telemetry.addData("Tracked Add Times: ", robot.hardwareSBM.trackedAddSBM);

			telemetry.update();
			
			if(gamepad1.xWasReleased()){
				robot.runMotor(robot.motor1, 1000, 0);
			}

			if(checkTime + cycleTime < runtime.milliseconds()){
				robot.hardwareSBM.checkSBM(runtime.milliseconds());
				trackCheckTimeRuns++;
				checkTime = runtime.milliseconds();
			}
		}
	}
}
