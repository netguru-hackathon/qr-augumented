package co.qraugmented;

import android.app.Activity;
import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.dlazaro66.qrcodereaderview.QRCodeReaderView;

public class MainActivity extends Activity implements QRCodeReaderView.OnQRCodeReadListener {

    private final static String TAG = MainActivity.class.getSimpleName();
    private ImageView mImageView;
    private QRCodeReaderView mydecoderview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mydecoderview = (QRCodeReaderView) findViewById(R.id.qrdecoderview);
        mImageView = (ImageView) findViewById(R.id.augmentedImage);
        mydecoderview.setOnQRCodeReadListener(this);

    }


    // Called when a QR is decoded
    // "text" : the text encoded in QR
    // "points" : points where QR control points are placed
    @Override
    public void onQRCodeRead(String text, PointF[] points) {

        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        params1.leftMargin = getDip(points[0].x);
        params1.topMargin = getDip(points[0].y);
        params1.rightMargin = getDip(points[3].x);
        params1.bottomMargin = getDip(points[3].y);

        mImageView.setVisibility(View.VISIBLE);
        mImageView.setLayoutParams(params1);

        for(int i = 0; i < points.length; i++) {
            Log.d(TAG, "  Point " + i + " =  " + points[i]);
        }
    }

    private int getDip(float pixels) {
        float density = getResources().getDisplayMetrics().density;
       return ((int) (pixels / density));
    }

    // Called when your device have no camera
    @Override
    public void cameraNotFound() {

    }

    // Called when there's no QR codes in the camera preview image
    @Override
    public void QRCodeNotFoundOnCamImage() {
        mImageView.setVisibility(View.INVISIBLE);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mydecoderview.getCameraManager().startPreview();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mydecoderview.getCameraManager().stopPreview();
    }
}
