package testOpenCV;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;

public class test2 {

	public static void main(String[] args) throws InterruptedException {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		List<Mat> hsv_planes = new ArrayList<Mat>();
		Mat image = Highgui.imread("balloon3.png");
		Mat huered = new Mat();
		Mat scalehuered = new Mat();
		Mat scalesat = new Mat();
		Mat sat = new Mat();
		Mat balloonyness = new Mat();
		Mat thresh = new Mat();
		List<MatOfPoint> contours = new ArrayList<MatOfPoint>();
		Mat hierarchy = new Mat();
		List<MatOfPoint> blobs = new ArrayList<MatOfPoint>();

		Imgproc.cvtColor(image, sat, Imgproc.COLOR_RGB2HSV);
		Core.split(sat, hsv_planes);

		//Core.absdiff(hsv_planes.get(0), new Scalar(90), huered);
		//Core.divide(hsv_planes.get(0), new Scalar(4), scalehuered);
		Imgproc.threshold(hsv_planes.get(0), scalehuered, 0,5, 2);
		Imgproc.threshold(hsv_planes.get(1), scalehuered, 80, 100, 2);
		
		//Imgproc.threshold(hsv_planes.get(0), scalehuered, 165, 180, Imgproc.THRESH_BINARY);
		Core.divide(hsv_planes.get(1), new Scalar(16), scalesat);
		Core.multiply(scalehuered, scalesat, balloonyness);
		Imgproc.threshold(balloonyness, thresh, 200, 255, Imgproc.THRESH_BINARY);

		Imgproc.erode(thresh, thresh, Imgproc.getStructuringElement(
				Imgproc.MORPH_RECT, new Size(2, 2)));
		
		Imgproc.findContours(thresh, contours, hierarchy, Imgproc.RETR_CCOMP,
				Imgproc.CHAIN_APPROX_SIMPLE);

		for (MatOfPoint contour : contours) {
			if (Imgproc.contourArea(contour) > 1000) {
				blobs.add(contour);
			}
		}

		Mat output = new Mat(thresh.width(), thresh.height(), thresh.type());
		Imgproc.drawContours(output, blobs, -1, new Scalar(256));

		// Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_ELLIPSE,
		// new
		// Size(3,3));
		// Imgproc.morphologyEx(thresh, thresh, Imgproc.MORPH_OPEN, kernel);
		System.out.println("Done");
		Highgui.imwrite("ballooncircle3.png", output);

	}

}
