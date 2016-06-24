package co.qraugmented;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
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
    private TextView mPositionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mydecoderview = (QRCodeReaderView) findViewById(R.id.qrdecoderview);
        mImageView = (ImageView) findViewById(R.id.augmentedImage);
        mPositionText = (TextView) findViewById(R.id.postitionText);
        mydecoderview.setOnQRCodeReadListener(this);

    }


    // Called when a QR is decoded
    // "text" : the text encoded in QR
    // "points" : points where QR control points are placed
    @Override
    public void onQRCodeRead(String text, PointF[] points) {

        int width = mydecoderview.getWidth();
        int height = mydecoderview.getHeight();

        RelativeLayout.LayoutParams params1 = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

        params1.setMargins((int) (points[0].x - 100), (int) (points[1].y - 100), (int) (width - points[2].x - 100), (int) (height - points[2].y - 300));
        mPositionText.setText(text);
        if(text.toLowerCase().contains("netguru")) {
            mImageView.setImageResource(R.drawable.netguru);
        } else if (text.toLowerCase().contains("mcdonalds")) {
            mImageView.setImageResource(R.drawable.mcdonalds);
        } else if (text.toLowerCase().contains("uefa")) {
            mImageView.setImageResource(R.drawable.euro);
        } else {
            mImageView.setImageResource(R.drawable.smiley);
        }


        mImageView.setVisibility(View.VISIBLE);
        mImageView.setLayoutParams(params1);

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
