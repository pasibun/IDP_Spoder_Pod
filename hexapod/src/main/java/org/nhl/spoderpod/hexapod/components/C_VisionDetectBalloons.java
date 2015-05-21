package org.nhl.spoderpod.hexapod.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;
import org.opencv.imgproc.Moments;

public class C_VisionDetectBalloons {

	public static void main(String[] args) {
System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		
		double focalLength = 3.6;
		int realObjectHeight = 265;
		int imgHeight = 972;
		int objectHeight = 0;
		double sensorHeight = 2.724;

		System.out.println("What color do you want to search?");
		Scanner input = new Scanner(System.in);
		String color = input.nextLine();
		
		//input image

		Mat image = Highgui.imread("balooo.png");
		Mat sat = new Mat();
		Mat imgThresholded = new Mat();
		Mat canny_output = new Mat();
		
		
		//returns structuring element of the specified shape and size for morphological operations
		Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE,
				new Size(10, 10));			

		Imgproc.cvtColor(image, sat, Imgproc.COLOR_RGB2HSV);
		// blue: 10 - 20
		// red: 114 - 124
		
		//search for red objects
		if ("red".equals(color))
			Core.inRange(sat, new Scalar(114, 170, 0),
					new Scalar(124, 255, 255), imgThresholded);
		
		//search for blue objects
		else if ("blue".equals(color))
			Core.inRange(sat, new Scalar(0, 100, 100), new Scalar(45, 255, 255),
					imgThresholded);
		else
		{
			System.out.println("pick other color");
			main(args);
		}
		
		Imgproc.morphologyEx(sat, sat, Imgproc.MORPH_OPEN, kernel);
		Imgproc.morphologyEx(sat, sat, Imgproc.MORPH_CLOSE, kernel);
		
		System.out.println("Pixel waarde: " + " " + Arrays.toString(sat.get(499, 643)));

		//list of contours
		List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
		Mat hierarchy = new Mat();
		
		//fills small holes in the thresholded image
		Imgproc.dilate(imgThresholded, imgThresholded, kernel);

		System.out.println("Done!");	
		
		//print binairy image 
		Highgui.imwrite("ballooncircle" /* + i + */+ ".png", imgThresholded);
		
		
		//finds contours in the binairy image
		Imgproc.findContours(imgThresholded, contours, hierarchy,
				Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

		List<MatOfPoint> minBalloonContours = new ArrayList<MatOfPoint>();
		for (MatOfPoint contour : contours) {
			if (contour.size().area() > 5) {
				minBalloonContours.add(contour);
			}
		}
		for (int i = 0; i < minBalloonContours.size(); i++) {	

			//area of white blobs in amage
			double area = Imgproc.contourArea(minBalloonContours.get(i));
			if (area > 3000 && area < 1000000)
			{
				System.out.println("Area size: " + " " + area);
				//draws a rectangle around the white blobs
				Rect bounding_rect = Imgproc.boundingRect(minBalloonContours.get(i));
				System.out.println("HEIGHT" + " " + bounding_rect.height);
				//y size of the object in image
				objectHeight = bounding_rect.height;
				
				//calculate distance to object
				double distance = ((focalLength * realObjectHeight * imgHeight)/(objectHeight * sensorHeight))/10;
				System.out.println("Distance to object: " + " " + distance + " " + "CM");								
			}					
		}

		//finds edges in an image
		Imgproc.Canny(imgThresholded, canny_output, 100, 100 * 2);
		Imgproc.findContours(canny_output, contours, hierarchy,
				Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
		
		
		//finding the center of the white blobs
		List<Moments> mu = new ArrayList<Moments>(contours.size());
		for (int i = 0; i < contours.size(); i++) {
			mu.add(i, Imgproc.moments(contours.get(i), false));
		}
		List<Point> mc = new ArrayList<Point>(contours.size());
		for (int i = 0; i < contours.size(); i++) {
			mc.add(new Point(mu.get(i).get_m10() / mu.get(i).get_m00(), mu.get(
					i).get_m01()
					/ mu.get(i).get_m00()));
		}
		
		
		System.out.println("Info: Area and Contour Length");

		for (int i = 0; i < contours.size(); i++) {
			MatOfPoint2f contour = new MatOfPoint2f(contours.get(i).toArray());
			if (Imgproc.contourArea(contour) > 3000 && Imgproc.contourArea(contour) < 1000000) {
				System.out
						.println(String
								.format("* Contour[%d] - Area (M_00) = %.2f - Area OpenCV: %.2f - Length: %.2f \n",
										i, mu.get(i).get_m00(),
										Imgproc.contourArea(contour),
										Imgproc.arcLength(contour, true)));
				
				//check if balloon is at the right or left side
				if (mc.get(i).x > image.width() / 2) {
					System.out.println("Right: " + mc.get(i));
				} 
				else {
					System.out.println("Left: " + mc.get(i));
				} 
				
			}
		}

	}

}
