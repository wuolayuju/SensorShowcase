package uam.eps.es.sensorshowcase.input_devices;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.hardware.Camera;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import uam.eps.es.sensorshowcase.R;

public class CameraActivity extends AppCompatActivity {

    private Camera mCamera;
    private int mCameraId;
    private CameraPreviewView mCameraView;
    private boolean mMirrorCamera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);

        Intent intent = getIntent();
        setTitle(intent.getStringExtra("CAMERA_NAME"));
        mCameraId = getIntent().getIntExtra("CAMERA_ID", 0);

        try {
            mCamera = Camera.open(mCameraId);
            Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
            Camera.getCameraInfo(mCameraId, cameraInfo);
            mMirrorCamera = cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT;
        }catch (Exception e) {
            Log.d("ERROR", "Failed to open camera: " + e.getMessage());
        }

        if (mCamera != null) {
            mCameraView = new CameraPreviewView(this, mCamera);
            FrameLayout cameraViewLayout = (FrameLayout) findViewById(R.id.camera_view);
            cameraViewLayout.addView(mCameraView);
            FaceOverlayView faceOverlayView = new FaceOverlayView(this);
            mCameraView.setFacesDrawEventListener(faceOverlayView);
            cameraViewLayout.addView(faceOverlayView, 1, new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            //addContentView(faceOverlayView, new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        }
    }


    public class FaceOverlayView extends View implements CameraPreviewView.FacesDrawEventListener{

        public final String TAG = FaceOverlayView.class.getSimpleName();
        private Camera.Face[] mFaces;
        private Paint mPaintFaces;

        public FaceOverlayView(Context context) {
            super(context);
            mPaintFaces = new Paint();
            mPaintFaces.setColor(Color.GREEN);
            mPaintFaces.setAlpha(64);
            mPaintFaces.setStyle(Paint.Style.FILL_AND_STROKE);
        }

        @Override
        public void onFacesDetected(Camera.Face[] faces) {
            mFaces = faces;
            invalidate();
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);

            if (mFaces != null && mFaces.length > 0) {
                for (Camera.Face face : mFaces) {
                    RectF rectF = new RectF(face.rect);
                    convertToCanvasCoordinates(rectF);
                    canvas.drawRect(rectF, mPaintFaces);
                }
                canvas.restore();
            }
        }

        private void convertToCanvasCoordinates(RectF rectF) {
            Matrix matrix = new Matrix();
            matrix.setScale(-1, mMirrorCamera ? 1 : -1);
            matrix.postRotate(mMirrorCamera ? 90 : 270);
            matrix.postScale(getWidth() / 2000f, getHeight() / 2000f);
            matrix.postTranslate(getWidth() / 2f, getHeight() / 2f);
            matrix.mapRect(rectF);
        }
    }
}
