package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import java.util.List;
import java.lang.reflect.Array;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import java.util.ArrayList;
import java.util.Collections;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.SBMTestHardware;
import org.firstinspires.ftc.teamcode.SBMTestDriveCode;

public class SBM {

	// HardwareDevice to allow for servos and motors, both implement it.
	public ArrayList<HardwareDevice> objects = new ArrayList<HardwareDevice>();
	
	//Time list is the time in miliseconds when the corresponding object needs to be turned off
	public ArrayList<Double> finishTime = new ArrayList<Double>();
	
	//ResetState is the value to set the hardware device to after the time is up - usually a default servo position or 0 for motors
	public ArrayList<Float> resetState = new ArrayList<Float>(); 
	/* 
	Why is this of type Float? You might run into conversion issues because double is what most things use. 
	*/
	
	public ArrayList<String> name = new ArrayList<String>();
	
	public ArrayList<String> testNames = new ArrayList<String>(List.of("John", "Jeff", "Jacob"));
	public int trackedAddSBM;
	
	private SBMTestHardware theRobot = null;
	
	// private SBMTestDriveCode myOpMode = null;
	
	public SBM(SBMTestHardware hardware) {
		theRobot = hardware;
	}
	
	// public SBM(SBMTestDriveCode opmode) {
	// 	myOpMode = opmode;
	// }
	/* 
	Why do we have 2 different versions? why is there an opmode version and a hardware version. you have both in both files. 
	(by the time you make the sbm in hardware, it has a reference to the opmode, and vice versa. can't you just make one constructor?) 
	*/

	//Used by other files to add objects and their timers to the SBM checklist
	public void addSBM(HardwareDevice object, double endTime, float state, String objName) {
		objects.add(object);
		finishTime.add(endTime);
		resetState.add(state);
		name.add(objName);
		trackedAddSBM++;
	}
	
	public void checkSBM(double runtime) {
		//go through all objects in sbm
		for (int i = 0; i < objects.size(); i++) { 
			HardwareDevice O = objects.get(i);
			// if the current time is past the object's finish time 
			if (finishTime.get(i) < runtime) {
				// possibly change this to switch statement
				if (O instanceof DcMotor) {
					DcMotor temp = (DcMotor) O;
					temp.setPower(resetState.get(i));
				} else if (O instanceof Servo) {
					Servo temp = (Servo) O;
					temp.setPosition(resetState.get(i));
				}	
			} 
		}
	}
	/*
	Improvements that could be made:
	1. cache objects.get(i), its used multiple times and we can just store it to a variable once. 
	2. Wrapper classes. 
	3. actually remove the objects entries when it is ready. to be executed. 
	*/
	
	public ArrayList SBMTelemetry(String list) {
		ArrayList<String> returnVal = new ArrayList<String>();
		returnVal.add("Unkown");
		if (list == "Objects") {
			return objects.size();
			/*
			Does this work? it's supposed to return an Arraylist, but this is an int.
			Formatting the lists beforehand and returning a string would make this better, just turn the int to a string.
			*/
		} else if (list == "FinishTime") {
			return finishTime;
		} else if (list == "ResetState") {
			return resetState;
		} else if (list == "Name") {
			return name;
		}
		return returnVal;
	}
	/*
	Perhaps replace with dedicated getters, also perhaps have them or this return formatted versions of the list.
	Use 'string.equals("string")' instead of ' string == "string"', as the latter can fail when you think it shouldn't.

	*/

}
