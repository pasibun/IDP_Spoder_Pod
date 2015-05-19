package testOpenCV;

import java.util.Scanner;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class test3 {

	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		//int hsvLow;
		//int hsvHigh;
		
		System.out.println("What color do you want to search?");
		//Color color = Color.valueOf(new Scanner(System.in));
		Scanner input = new Scanner(System.in);
		String color = input.nextLine();
			

		Mat image = Highgui.imread("images/balloon.png");

		Mat sat = new Mat();
		Mat imgThresholded = new Mat();
		
		Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(10, 10));
		
		Imgproc.cvtColor(image, sat, Imgproc.COLOR_RGB2HSV);
		//i*2 - i * 2 + 10
		//blue: 10 - 20  
		//red: 114 - 124
		if("red".equals(color))
			Core.inRange(sat, new Scalar(114, 170, 0), new Scalar(124, 255, 255), imgThresholded);
		else if("blue".equals(color))
			Core.inRange(sat, new Scalar(10, 200, 0), new Scalar(20, 255, 255), imgThresholded);
		Imgproc.morphologyEx(sat, sat, Imgproc.MORPH_OPEN, kernel);
		Imgproc.morphologyEx(sat, sat, Imgproc.MORPH_CLOSE, kernel);		
		
		System.out.println("Done!");
		Highgui.imwrite("images/ballooncircle" /*+  i  +*/ + ".png", imgThresholded);
		
	}
		

}
