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
		SBM thisMachine = new SBM(this);
		/*
		Ideally (imo), SBM should live in the hardware, so the opmode shouldn't create its own. just access it with 'robot.thismachine.[method]'. (will need to change it to public) 
		*/
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
			telemetry.addData("Tracked Objects: ", thisMachine.SBMTelemetry("Name"));
			telemetry.addData("Tracked Objects; ",thisMachine.SBMTelemetry("Objects"));
			telemetry.addData("Tracked End Times: ",thisMachine.SBMTelemetry("FinishTime"));
			telemetry.addData("Tracked Reset States: ",thisMachine.SBMTelemetry("ResetState"));
			telemetry.addData("Tracked Objects: ", thisMachine.testNames);
			telemetry.addData("Tracked Add Times: ", thisMachine.trackedAddSBM);
			/*
			I remember you saying you were having issues with telemetry, I looked at the docs and this should(?) work. 
			what you are calling is the 'addData(String caption, Object value)' method, and what the method does to the value is call 'value.toString()' on it, 
			which according to some sources just returns the values as array (i.e. [value1, value2, value3,...])
			Something you could maybe do instead is:
			• make the arraylists public, and access them directly
			• make getters for each arraylist.
			*/

			// ArrayList<HardwareDevice> objs = thisMachine.SBMTelemetry("Objects");
			// ArrayList<Double> fTimes = thisMachine.SBMTelemetry("FinishTime");
			
			// for (int ObjNum = 0; ObjNum < objs.size(); ObjNum++){
			// 	telemetry.addData("Object: ", "%.0f Until: %.2f", ObjNum, fTimes.get(ObjNum));
			// 	telemetry.addData("Cycle", ObjNum);
			// }
			telemetry.update();
			
			if(gamepad1.xWasReleased()){
				robot.runMotor("", 1000, 0);
				/*
				runMotor takes in 2 parameters? why are you calling it with 3?
				*/
			}

			if(gamepad1.yWasReleased()){
				/* robot.runMotor2(runtime.milliseconds());  Commented out because the method in hardware is commented out. */
			}

			if(checkTime + cycleTime < runtime.milliseconds()){
				thisMachine.checkSBM(runtime.milliseconds());
				/* 
				Just wanted to point out that this SBM doesn't have anything in it. robot.runMotor(stuff) puts it in SBMTestHardwares SBM, 
				which you don't call checks on. Again, I suggest removing SBM from the opmode, and keeping it in the hardware only. 
				*/
				trackCheckTimeRuns++;
				checkTime = runtime.milliseconds();
			}
		}
	}
}
