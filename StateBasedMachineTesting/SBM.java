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
			sb.append(theRobot.myOpMode.hardwareMap.getNamesOf(device).stream().findFirst().orElse("Unknown"));
			/* 
			The error was that you remove the myOpMode variable, which this used. I swapped it to use myOpMode from the hardware. 
			also added null checking.
			*/
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
	
	public SBM(SBMTestHardware hardware) {
		theRobot = hardware;
	} 

	//Used by other files to add objects and their timers to the SBM checklist
	public void addSBM(HardwareDevice object, double endTime, float state ) {
		SBMEntry newEntry = new SBMEntry(object, endTime, state );
		SBMEntries.add(newEntry);
		trackedAddSBM++;
	}
	
	public void checkSBM(double runtime) {
		//go through all objects in sbm
		ArrayList<SBMEntry> toRemove = new ArrayList<SBMEntry>();
		for (SBMEntry entry : SBMEntries){
			if (entry.finishTime < runtime){
				if (entry.device instanceof DcMotor) {
					DcMotor temp = (DcMotor) entry.device;
					temp.setPower(entry.resetState);
				} else if (entry.device instanceof Servo) {
					Servo temp = (Servo) entry.device;
					temp.setPosition(entry.resetState);
				}
				toRemove.add(entry);
			}
		}
		// SBMEntries.removeAll(toRemove);
		/* 
		I added removal to the list after they are processed, but I commented out the line so you can see that it works before we remove them.
		*/
	}

	public ArrayList<SBMEntry> getEntries(){
		return SBMEntries;
	}
}
