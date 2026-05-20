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
	public class SBMEntry {
		public HardwareDevice device;
		public double finishTime;
		public float resetState;

		public SBMEntry(HardwareDevice device, double finishTime, float resetState ){
			this.device = device;
			this.finishTime = finishTime;
			this.resetState = resetState;
		}

		@Override
		public String toString(){
			StringBuilder sb = new StringBuilder();
			sb.append("[");
			sb.append(myOpMode.hardwareMap.getNamesOf(device).iterator().next());
			sb.append(", ");
			sb.append(finishTime);
			sb.append(", ");
			sb.append(resetState);
			sb.append("]");
			return(sb.toString());
		}
	}

	public ArrayList<SBMEntry> SBMEntries = new ArrayList<SBMEntry>();
	
	public int trackedAddSBM;
	
	private SBMTestHardware theRobot = null;
	private SBMTestDriveCode myOpMode = null;
	
	public SBM(SBMTestHardware hardware /*, SBMTestDriveCode opmode*/) {
		theRobot = hardware;
		//myOpMode = opmode;
	} 

	//Used by other files to add objects and their timers to the SBM checklist
	public void addSBM(HardwareDevice object, double endTime, float state ) {
		SBMEntry newEntry = new SBMEntry(object, endTime, state );
		SBMEntries.add(newEntry);
		trackedAddSBM++;
	}
	
	public void checkSBM(double runtime) {
		//go through all objects in sbm
		for (SBMEntry entry : SBMEntries){
			if (entry.finishTime < runtime){
				if (entry.device instanceof DcMotor) {
					DcMotor temp = (DcMotor) entry.device;
					temp.setPower(entry.resetState);
				} else if (entry.device instanceof Servo) {
					Servo temp = (Servo) entry.device;
					temp.setPosition(entry.resetState);
				}
			}
		}
	}

	public int SMBSize() {
		return SBMEntries.size();
	}

	public ArrayList<SBMEntry> getEntries(){
		return SBMEntries;
	}
}
