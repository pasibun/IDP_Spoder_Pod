package org.nhl.spoderpod.hexapod.libraries;

/***
 * 
 * @author Fre'git gud' meine
 *
 */
public final class L_Calculations {

	public final static double RAD_TO_DEGREE = 180 / Math.PI;
	public final static int COXA = 36;
	public final static int FEMUR = 90;
	public final static int TIBIA = 125;
	public final static int RIGHT_ANGLE = 90;

	public static int InsideServo(int x, int y, int z) {
		return (int) (Math.asin(z / Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2))) * RAD_TO_DEGREE);
	}

	public static int MiddleServo(int x, int y, int z) {
		return (int) ((Math.acos((Math.pow(FEMUR, 2) + (Math.pow(Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2)) - COXA, 2)) + Math.pow(y, 2) - Math.pow(TIBIA, 2))
				
				/

				(2 * FEMUR * Math.sqrt(Math.pow((Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2)) - COXA), 2) + Math.pow(y, 2))))

				* RAD_TO_DEGREE)
				
				- (RIGHT_ANGLE - (Math.acos(y / Math.sqrt(Math.pow(Math.sqrt(Math.pow(x, 2) + Math.pow(z, 2))- COXA, 2) + Math.pow(y, 2))))
				
				* RAD_TO_DEGREE));
	}

	public static int OutsideServo(int x, int y, int z) {
		return (int) (Math.acos(((Math.pow(FEMUR, 2)+ Math.pow(TIBIA, 2)) - (Math.pow(Math.sqrt(Math.pow(x, 2)+ Math.pow(z, 2)) - COXA , 2) + Math.pow(y,2)))
				
				/ (2*FEMUR*TIBIA))
				
				* RAD_TO_DEGREE);
	}
}
