package uam.eps.es.sensorshowcase.input_devices;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;

import uam.eps.es.sensorshowcase.R;

/**
 * Created by Ari on 19/05/2016.
 */
public class AudioActivity extends AppCompatActivity{

    private static final String TAG = AudioActivity.class.getSimpleName();

    private int mAudioDeviceId;
    private Button mRecordingToggleButton;
    private Button mPlayRecordingButton;
    private boolean mIsRecording = false;
    private MediaRecorder mMediaRecorder;
    private String mRecordedFileName;
    private boolean mIsPlaying = false;
    private MediaPlayer mMediaPlayer;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);

        mRecordingToggleButton = (Button) findViewById(R.id.button_toggle_recording);
        mPlayRecordingButton = (Button) findViewById(R.id.button_play_recording);

        Intent intent = getIntent();
        setTitle(intent.getStringExtra("MIC_NAME"));
        mAudioDeviceId = getIntent().getIntExtra("MIC_ID", 0);

        mRecordedFileName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/audiorecordtext.3gp";
    }

    public void onRecordButtonClicked(View view) {
        goRecord();
        if (mIsRecording) {
            mRecordingToggleButton.setText(R.string.stop_recording);
        }
        else {
            mRecordingToggleButton.setText(R.string.start_recording);
        }
        mIsRecording = !mIsRecording;
    }

    private void goRecord() {
        if (!mIsRecording) {
            mMediaRecorder = new MediaRecorder();
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mMediaRecorder.setOutputFile(mRecordedFileName);
            mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            try {
                mMediaRecorder.prepare();
            } catch (IOException e) {
                Log.e(TAG, "recorder prepare() failed");
            }

            mMediaRecorder.start();
        }
        else {
            mMediaRecorder.stop();
            mMediaRecorder.release();
            mMediaRecorder = null;
        }
    }

    public void onPlayButtonClicked(View view) {
        goPlay();
        if (mIsPlaying) {
            mPlayRecordingButton.setText(R.string.play_recording);
        }
        else {
            mPlayRecordingButton.setText(R.string.stop_recording);
        }
        mIsPlaying = !mIsPlaying;
    }

    private void goPlay() {
        if (!mIsPlaying) {
            mMediaPlayer = new MediaPlayer();
            try {
                mMediaPlayer.setDataSource(mRecordedFileName);
                mMediaPlayer.prepare();
                mMediaPlayer.start();
            } catch (IOException e) {
                Log.e(TAG, "player prepare() failed");
            }
        }
        else {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }
}
