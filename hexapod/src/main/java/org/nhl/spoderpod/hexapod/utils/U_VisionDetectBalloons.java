package org.nhl.spoderpod.hexapod.utils;

import java.util.ArrayList;
import java.util.List;

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

public class U_VisionDetectBalloons {
	private static final double FOCAL_LENGTH = 3.6;
	private static final int REAL_OBJECT_HEIGHT = 265;
	private static final int IMG_HEIGHT = 972;
	private static final double SENSOR_HEIGHT = 2.724;
	private int objectHeight;
	private double x;
	private double y;
	private double z;
	private String position;

	private Mat image;
	private Mat sat;
	private Mat imgThresholded;
	private Mat canny_output;
	private Mat kernel;
	private List<Moments> mu;
	private List<Point> mc;

	// list of contours
	private List<MatOfPoint> contours;
	private Mat hierarchy;
	private String color;
	private String location;

	public U_VisionDetectBalloons(String color) {
		image = new Mat();
		sat = new Mat();
		imgThresholded = new Mat();
		canny_output = new Mat();
		kernel = new Mat();
		hierarchy = new Mat();
		contours = new ArrayList<MatOfPoint>();
		this.color = color;
		detectBalloon();
	}

	private void detectBalloon() {
		image = Highgui.imread(location);

		// returns structuring element of the specified shape and size for
		// morphological operations
		kernel = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE, new Size(
				10, 10));

		Imgproc.cvtColor(image, sat, Imgproc.COLOR_RGB2HSV);

		// search for red objects
		if ("red".equals(color))
			Core.inRange(sat, new Scalar(114, 170, 0),
					new Scalar(124, 255, 255), imgThresholded);

		// search for blue objects
		else if ("blue".equals(color))
			Core.inRange(sat, new Scalar(0, 100, 100),
					new Scalar(45, 255, 255), imgThresholded);

		else {
			System.out.println("Wrong input! Pick valid color: red / blue");
			return;
		}

		// fills small holes in the thresholded image
		Imgproc.dilate(imgThresholded, imgThresholded, kernel);
		// print binairy image
		Highgui.imwrite("ballooncircle.png", imgThresholded);
		calcArea();
	}

	private void calcArea() {
		// finds contours in the binairy image
		Imgproc.findContours(imgThresholded, contours, hierarchy,
				Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

		List<MatOfPoint> minBalloonContours = new ArrayList<MatOfPoint>();

		for (MatOfPoint contour : contours) {
			if (contour.size().area() > 5) {
				minBalloonContours.add(contour);
			}
		}
		for (int i = 0; i < minBalloonContours.size(); i++) {

			// area of white blobs in amage
			double area = Imgproc.contourArea(minBalloonContours.get(i));
			if (area > 3000 && area < 1000000) {
				System.out.println("Area size: " + " " + area);
				// draws a rectangle around the white blobs
				Rect bounding_rect = Imgproc.boundingRect(minBalloonContours
						.get(i));
				System.out.println("HEIGHT" + " " + bounding_rect.height);
				// y size of the object in image
				objectHeight = bounding_rect.height;

				// calculate distance to object
				z = ((FOCAL_LENGTH * REAL_OBJECT_HEIGHT * IMG_HEIGHT) / (objectHeight * SENSOR_HEIGHT)) / 10;
				System.out.println("Distance to object: " + " " + z + " "
						+ "CM");
				findCenter();
			}
		}
	}

	private void findCenter() {
		// finds edges in an image
		Imgproc.Canny(imgThresholded, canny_output, 100, 100 * 2);
		Imgproc.findContours(canny_output, contours, hierarchy,
				Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

		// finding the center of the white blobs
		mu = new ArrayList<Moments>(contours.size());
		for (int i = 0; i < contours.size(); i++) {
			mu.add(i, Imgproc.moments(contours.get(i), false));
		}
		mc = new ArrayList<Point>(contours.size());
		for (int i = 0; i < contours.size(); i++) {
			mc.add(new Point(mu.get(i).get_m10() / mu.get(i).get_m00(), mu.get(
					i).get_m01()
					/ mu.get(i).get_m00()));
		}
		printInfo();
	}

	private void printInfo() {
		System.out.println("Info: Area and Contour Length");
		for (int i = 0; i < contours.size(); i++) {
			MatOfPoint2f contour = new MatOfPoint2f(contours.get(i).toArray());
			if (Imgproc.contourArea(contour) > 3000
					&& Imgproc.contourArea(contour) < 1000000) {
				System.out
						.println(String
								.format("* Contour[%d] - Area (M_00) = %.2f - Area OpenCV: %.2f - Length: %.2f \n",
										i, mu.get(i).get_m00(),
										Imgproc.contourArea(contour),
										Imgproc.arcLength(contour, true)));

				// check if balloon is at the right or left side
				if (mc.get(i).x > image.width() / 2) {
					System.out.println("Right: " + mc.get(i));
					position = "Right";
					String part = mc.get(i).toString();
					String[] parts = part.split(",");
					x = Double.parseDouble(parts[0]);
					y = Double.parseDouble(parts[1]);
				} else {
					System.out.println("Left: " + mc.get(i));
					position = "Left";
					String part = mc.get(i).toString();
					String[] parts = part.split(",");
					x = Double.parseDouble(parts[0]);
					y = Double.parseDouble(parts[1]);
				}
			}
		}
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public String getPosition() {
		return position;
	}
}
