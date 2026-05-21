package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import org.firstinspires.ftc.robotcore.external.State;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import java.util.Collections;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.CRServo;

import org.firstinspires.ftc.teamcode.SBM;

public class SBMTestHardware {
	
	public LinearOpMode myOpMode = null;

	public DcMotor motor1 = null;
	public DcMotor motor2 = null;
	
	public SBM hardwareSBM = null;
	
	public SBMTestHardware (LinearOpMode opmode) {
		myOpMode = opmode;
	}
	
	public void init () {
		motor1 = myOpMode.hardwareMap.get(DcMotor.class, "Motor1");
		motor2 = myOpMode.hardwareMap.get(DcMotor.class, "Motor2");
		
		motor1.setDirection(DcMotor.Direction.FORWARD);
		motor2.setDirection(DcMotor.Direction.FORWARD);
	
		hardwareSBM = new SBM(this /*, myOpMode*/);
	}
	
	public void runMotor(DcMotor object, double endTime, float state) {
		hardwareSBM.addSBM(object, endTime, state);
		object.setPower(0.5);
	}

	public void runCheckSBM(double runtime){
		hardwareSBM.checkSBM(runtime);
	}
}
