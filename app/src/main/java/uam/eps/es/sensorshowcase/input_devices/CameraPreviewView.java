package uam.eps.es.sensorshowcase.input_devices;

import android.content.Context;
import android.graphics.Rect;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

import java.io.IOException;

/**
 * Created by Ari on 18/05/2016.
 */
public class CameraPreviewView extends SurfaceView implements SurfaceHolder.Callback, Camera.FaceDetectionListener {

    private SurfaceHolder mHolder;
    private Camera mCamera;
    private FacesDrawEventListener mFacesDrawEventListener;

    public CameraPreviewView(Context context, Camera camera) {
        super(context);

        mCamera = camera;
        mCamera.setDisplayOrientation(90);

        mHolder = getHolder();
        mHolder.addCallback(this);
    }

    public interface FacesDrawEventListener {
        public void onFacesDetected(Camera.Face[] faces);
    }

    public void setFacesDrawEventListener(FacesDrawEventListener listener) {
        this.mFacesDrawEventListener = listener;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
            mCamera.setFaceDetectionListener(this);
            mCamera.startFaceDetection();
        } catch (IOException e) {
            Log.d("ERROR", "CameraPreviewView#surfaceCreated: " + e.getMessage());
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        if (mHolder.getSurface() == null) {
            return;
        }

        mCamera.stopPreview();

        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();
            mCamera.setFaceDetectionListener(this);
            mCamera.startFaceDetection();
        } catch (IOException e) {
            Log.d("ERROR", "CameraPreviewView#surfaceChanged: " + e.getMessage());
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        mCamera.stopPreview();
        mCamera.release();
    }

    @Override
    public void onFaceDetection(Camera.Face[] faces, Camera camera) {
        mFacesDrawEventListener.onFacesDetected(faces);
    }
}
