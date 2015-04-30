package test1;

import java.time.chrono.Era;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.MatOfPoint2f;
import org.opencv.core.Point;
import org.opencv.core.RotatedRect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class test {

	public static void main(String[] args) {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		// Mat mat = new Mat(100, 100, CvType.CV_8UC1);
		// System.out.println("mat = " + mat.dump());

		// Highgui.imwrite("test.png", mat);

		Mat src, src_gray;
		int tresh = 100;
		int max_tresh = 255;

		src = Highgui.imread("images/balloon.png");
		if (src == null)
			return;
		src_gray = new Mat();
		Imgproc.cvtColor(src, src_gray, Imgproc.COLOR_BGR2GRAY);

		Imgproc.GaussianBlur(src_gray, src_gray, new Size(9, 9), 2, 2);

		Mat circles = new Mat();

		Imgproc.HoughCircles(src_gray, circles, Imgproc.CV_HOUGH_GRADIENT, 1,
				src_gray.rows() / 8, 200, 100, 0, 0);

		Highgui.imwrite("images/ballooncircle.png", thresh_callback(src, 190));
		System.out.println("DONE");
	}

	static Mat thresh_callback(Mat src_gray, int thresh) {
		Mat threshold_output = new Mat();
		List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
		Mat hierarchy = new Mat();

		Imgproc.threshold(src_gray, threshold_output, thresh, 255,
				Imgproc.THRESH_BINARY);
		Imgproc.cvtColor(threshold_output, threshold_output,
				Imgproc.COLOR_BGR2GRAY);
		Imgproc.findContours(threshold_output, contours, hierarchy,
				Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

		List<RotatedRect> minEllipse = new ArrayList<RotatedRect>();
		List<MatOfPoint> minEllipseContours = new ArrayList<MatOfPoint>();

		for (MatOfPoint contour : contours) {
			MatOfPoint2f contour2f = new MatOfPoint2f(contour.toArray());
			if (contour.size().area() > 5) {
				minEllipse.add(Imgproc.fitEllipse(contour2f));
				minEllipseContours.add(contour);
			}
		}

		Mat drawing = Mat.zeros(threshold_output.size(), CvType.CV_8UC3);
		/*
		 * for(MatOfPoint contour : contours) { Scalar color = new
		 * Scalar(Math.random()*255, Math.random()*255, Math.random()*255);
		 * Imgproc.drawContours(drawing, contours, 0, color,1,8, new Mat(), 0,
		 * new Point()); Core.ellipse(drawing, minEllipse.get(0), color, 2,8);
		 * 
		 * //MatOfPoint2f rect_points[]; minRects.add(points(rect_points)); }
		 */

		for (int i = 0; i < minEllipseContours.size(); i++) {

			double a = Imgproc.contourArea(minEllipseContours.get(i));
			if (a > 100 && a < 15000) {
				Scalar color = new Scalar(Math.random() * 255,
						Math.random() * 255, Math.random() * 255);
				Imgproc.drawContours(drawing, minEllipseContours, i, color, 1,
						8, new Mat(), 0, new Point());
				Core.ellipse(drawing, minEllipse.get(i), color, 2, 8);
				System.out.println(a);
			}

		}
		return drawing;
	}
}
