import org.bytedeco.javacpp.DoublePointer;

import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_highgui.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;

///////////////////////////////////////////////////////////////////
//*                                                             *//
//* As the author of this code, I place all of this code into   *//
//* the public domain. Users can use it for any legal purpose.  *//
//*                                                             *//
//*             - Dave Grossman                                 *//
//*                                                             *//
///////////////////////////////////////////////////////////////////
public class Match_template
{
    public static void main(String[] args)
    {
        System.out.println("STARTING...\n");
        demo();
        System.out.println("ALL DONE");
    }

    public static void demo()
    {
    	IplImage src = cvLoadImage("lena.jpg",1);
    	IplImage tmp = cvLoadImage("those_eyes.jpg",1);	
    		
    	IplImage result = cvCreateImage(cvSize(src.width()-tmp.width()+1, src.height()-tmp.height()+1), IPL_DEPTH_32F, 1);
    	cvZero(result);

    	//Match Template Function from OpenCV
    	cvMatchTemplate(src, tmp, result, CV_TM_CCORR_NORMED);
    			
    	DoublePointer min_val = new DoublePointer();
    	DoublePointer max_val = new DoublePointer();

    	CvPoint minLoc = new CvPoint();
    	CvPoint maxLoc = new CvPoint();

    	//Get the Max or Min Correlation Value
    	cvMinMaxLoc(result, min_val, max_val, minLoc, maxLoc, null);
    	
    	
    	System.out.println(min_val);
    	System.out.println(max_val.address());
    			
    	CvPoint point = new CvPoint();
    	point.x(maxLoc.x()+tmp.width());
    	point.y(maxLoc.y()+tmp.height());

    	cvRectangle(src, maxLoc, point, CvScalar.WHITE, 2, 8, 0);//Draw a Rectangle for Matched Region

    	cvShowImage("Lena Image", src);
    	cvWaitKey(0);
    	cvReleaseImage(src);
    	cvReleaseImage(tmp);
    	cvReleaseImage(result);
    }
    
}

