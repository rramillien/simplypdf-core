package com.web4enterprise.pdf.core;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.web4enterprise.pdf.core.font.Font;
import com.web4enterprise.pdf.core.font.FontStyle;

public class PdfTest {
	@Test
	public void testWrite() throws IOException, PdfGenerationException {
		try(OutputStream out = new FileOutputStream("testWrite.pdf")) {
			Pdf document = new Pdf();
			Image image = document.createImage(this.getClass().getResourceAsStream("/test.png"));
			Page page1 = document.createPage(595, 842);
			page1.addText(20, 700, 24, "Times-Roman");
			page1.addText(20, 660, 18, Font.TIMES_ROMAN.getVariant(FontStyle.BOLD), new Color(255, 255, 80), "Times-Roman Bold");
			page1.addText(20, 640, 12, Font.TIMES_ROMAN.getVariant(FontStyle.ITALIC), "Times-Roman Italic");
			page1.addText(20, 620, 6, Font.TIMES_ROMAN.getVariant(FontStyle.BOLD_ITALIC), "Times-Roman Bold Italic");
			page1.addText(200, 700, 24, Font.COURIER.getVariant(FontStyle.PLAIN), "Courier");
			page1.addText(200, 660, 18, Font.COURIER.getVariant(FontStyle.BOLD), new Color(255, 255, 80), "Courier Bold");
			page1.addText(200, 640, 12, Font.COURIER.getVariant(FontStyle.ITALIC), "Courier Italic");
			page1.addText(200, 620, 6, Font.COURIER.getVariant(FontStyle.BOLD_ITALIC), "Courier Bold Italic");
			page1.addText(320, 700, 24, Font.HELVTICA.getVariant(FontStyle.PLAIN), "Helvetica");
			page1.addText(320, 660, 18, Font.HELVTICA.getVariant(FontStyle.BOLD), new Color(255, 255, 80), "Helvetica Bold");
			page1.addText(320, 640, 12, Font.HELVTICA.getVariant(FontStyle.ITALIC), "Helvetica Italic");
			page1.addText(320, 620, 6, Font.HELVTICA.getVariant(FontStyle.BOLD_ITALIC), "Helvetica Bold Italic");
			image.setX(300);
			image.setY(300);
			image.setWidth(150);
			image.setHeight(75);
			page1.addImage(image);
			Image image2 = image.cloneReference();
			image2.setX(100);
			image2.setY(500);
			image2.setWidth(150);
			image2.setHeight(75);
			page1.addImage(image2);
			Page page2 = document.createPage(595, 842);
			page2.addText(100, 120, 50, "Another page...");
			
			page2.addPath(new StraightPath(new Point(100, 100), new Point(150, 150)));
			
			List<Point> points = new ArrayList<>();
			points.add(new Point(20, 10));
			points.add(new Point(30, 30));
			points.add(new Point(50, 30));
			StraightPath straightPath = new StraightPath(new Point(10, 10), points);
			straightPath.close();
			page2.addPath(straightPath);
			
			List<BezierPoint> bezierPoints = new ArrayList<>();
			bezierPoints.add(new BezierPoint(120, 10));
			bezierPoints.add(new BezierPoint(130, 30));
			bezierPoints.add(new BezierPoint(150, 30, 130, 20, 150, 20));
			page2.addPath(new BezierPath(new Point(110, 10), bezierPoints));
			
			List<BezierPoint> bezierPoints2 = new ArrayList<>();
			bezierPoints2.add(new BezierPoint(70, 10));
			bezierPoints2.add(new BezierPoint(80, 30));
			bezierPoints2.add(new BezierPoint(100, 30, 80, 20, 100, 20));
			BezierPath bezierPath2 = new BezierPath(new Point(60, 10), bezierPoints2);
			bezierPath2.fill();
			bezierPath2.setStrokeColor(new Color(127, 240, 0));
			bezierPath2.setLineWidth(3.0f);
			page2.addPath(bezierPath2);
			
			List<BezierPoint> bezierPoints3 = new ArrayList<>();
			bezierPoints3.add(new BezierPoint(170, 10));
			bezierPoints3.add(new BezierPoint(180, 30));
			bezierPoints3.add(new BezierPoint(200, 30, 180, 20, 200, 20));
			BezierPath bezierPath3 = new BezierPath(new Point(160, 10), bezierPoints3);
			bezierPath3.fill();
			bezierPath3.unstroke();
			bezierPath3.setFillColor(new Color(240, 127, 0));
			page2.addPath(bezierPath3);
			
			Page page3 = document.createPage(842, 595);
			page3.addText(60, 100, 24, "Page 3...");
			
			document.write(out);
		}
	}
}
