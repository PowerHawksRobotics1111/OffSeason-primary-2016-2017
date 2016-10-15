
package org.usfirst.frc.team1111.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.networktables.NetworkTable;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
    final String defaultAuto = "Default";
    final String customAuto = "My Auto";
    String autoSelected;
    SendableChooser chooser;
    

    
    NetworkTable networkTable;
    
    public Robot() {
		networkTable = NetworkTable.getTable("GRIP/contoursReport");
	}
    /*
     * 

	

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 
	public void robotInit() {
		chooser = new SendableChooser();
		chooser.addDefault("Default Auto", defaultAuto);
		chooser.addObject("My Auto", customAuto);
		SmartDashboard.putData("Auto choices", chooser);

		
			System.out.print("areas: ");
			for (double area : areas) {
				System.out.print(area + " ");
			}
			System.out.println();
			Timer.delay(1);
		}
	}
     */
	
    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
	public void robotInit() {
        chooser = new SendableChooser();
        chooser.addDefault("Default Auto", defaultAuto);
        chooser.addObject("My Auto", customAuto);
        SmartDashboard.putData("Auto choices", chooser);
    }
    
	/**
	 * This autonomous (along with the chooser code above) shows how to select between different autonomous modes
	 * using the dashboard. The sendable chooser code works with the Java SmartDashboard. If you prefer the LabVIEW
	 * Dashboard, remove all of the chooser code and uncomment the getString line to get the auto name from the text box
	 * below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the switch structure below with additional strings.
	 * If using the SendableChooser make sure to add them to the chooser code above as well.
	 */
    @Override
	public void autonomousInit() {
    	autoSelected = (String) chooser.getSelected();
//		autoSelected = SmartDashboard.getString("Auto Selector", defaultAuto);
		System.out.println("Auto selected: " + autoSelected);
    }
    
	class ParticleReport
	{
		final static double AREA_RATIO = 1.0 / 3.0, ASPECT_RATIO = 1.6, MOI = .28;

		double areaRatio;
		double aspectRatio;
		double momentOfInertia;
		
		double area, solidity, width, height, centerX, centerY;//TODO store every value for future calculations

		public ParticleReport(double area, double solidity, double width, double height)
		{
			if (solidity < AREA_RATIO)
				areaRatio = solidity / AREA_RATIO * 100;
			else
				areaRatio = (solidity - 2 * AREA_RATIO) / AREA_RATIO * -100;// If more than 2/3., returns a negative value
		
			double tempAspectRatio = width/height;
			if (tempAspectRatio < ASPECT_RATIO)
				aspectRatio = tempAspectRatio / ASPECT_RATIO * 100;
			else
				aspectRatio = (tempAspectRatio - 2 * ASPECT_RATIO) / ASPECT_RATIO * -100;// If more than 3.2, returns a negative value
		
			double tempMomentOfInertia = width*(height*height*height)/12; //MOI = b*h^3 / 12
			if (tempMomentOfInertia < MOI)
				momentOfInertia = tempMomentOfInertia / MOI * 100;
			else
				momentOfInertia = (tempMomentOfInertia - 2 * MOI) / MOI * -100;// If more than .56, returns a negative value
		
		}
	}

    /**
     * This function is called periodically during autonomous
     */
    @Override
	public void autonomousPeriodic() {
    	switch(autoSelected) {
    	case customAuto:
        //Put custom auto code here   
            break;
    	case defaultAuto:
    	default:
    	//Put default auto code here
            break;
    	}
    	
        double[] areas;
        double[] widths;
        double[] soliditys;
        double[] centerXs;
        double[] centerYs;
        double[] heights;
    	
        double[] defaultValue = new double[0];
        areas = networkTable.getNumberArray("area", defaultValue);
        widths = networkTable.getNumberArray("width", defaultValue);
        soliditys = networkTable.getNumberArray("solidity", defaultValue);
        centerXs = networkTable.getNumberArray("centerX", defaultValue);
        centerYs = networkTable.getNumberArray("centerY", defaultValue);
        heights = networkTable.getNumberArray("height", defaultValue);
        
        
        ParticleReport[] particles;
		particles = new ParticleReport[soliditys.length];
        
        for(int i = 0; i < soliditys.length; i++)
        {
        	particles[i] = new ParticleReport(areas[i], soliditys[i], widths[i], heights[i]);
        }
    }

    /**
     * This function is called periodically during operator control
     */
    @Override
	public void teleopPeriodic() {
        
    }
    
    /**
     * This function is called periodically during test mode
     */
    @Override
	public void testPeriodic() {
    
    }
    
}
