package org.usfirst.frc.team1111.robot;

public class Vision {

	static final double xRes = 0.0;
	static final double yRes = 0.0;

	static final int theta = 0;
	static double thetaRad = Math.toRadians(theta);
	static double tanTheta = Math.tan(thetaRad);

	static final double xSizeTarget = 0.0;

	static double distanceTimesTargetPixelWidth = (xSizeTarget * xRes) / (2 * tanTheta);

	public static double calcDistance(double targetPixelWidth)
	{
		// target width / target pixel width = camera width/camera pixel width
		// camera width = 2* dist from center to edge of camera view = 2*distance * tangent(theta)
		// distance = (target width *camera pixel width)/(2*target pixel width*tangent(theta))
		return distanceTimesTargetPixelWidth / targetPixelWidth;
	}

	double xAdjustCoordSystem(double pixel)//for moving, means axis is more normalised.
	{
		return (pixel - (xRes / 2)) / (xRes / 2);
	}

	double yAdjustCoordSystem(double pixel)
	{
		return (pixel - (yRes / 2)) / (yRes / 2);
	}

}