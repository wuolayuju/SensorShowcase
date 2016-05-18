package uam.eps.es.sensorshowcase;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ari on 15/05/2016.
 */
public class SensorActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager mSensorManager;
    private Sensor mSensor;
    private TextView mSensorValuesText;
    private TextView mSensorInfoText;
    private ListView mSensorInfoListView;
    private TextView mSensorAccuracyText;
    private SimpleAdapter mAttributeListAdapter;

    private final String[] from = { "attributeName", "attributeValue"};
    private final int[] to = { R.id.item_title_medium, R.id.item_info_small};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sensor_activity);

        Intent intent = getIntent();
        int sensor_type = intent.getIntExtra("SENSOR_TYPE", 1);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensor = mSensorManager.getDefaultSensor(sensor_type);
        setTitle(mSensor.getName());

        mSensorValuesText = (TextView) findViewById(R.id.valuesSensorText);
        mSensorAccuracyText = (TextView) findViewById(R.id.valuesSensorAccuracyText);
        mSensorInfoListView = (ListView) findViewById(R.id.sensorInfoListView);

        List<Map<String, String>> attributeList = loadSensorAttributeData();
        mAttributeListAdapter = new SimpleAdapter(this, attributeList, R.layout.sensor_info_list_item, from, to);
        mSensorInfoListView.setAdapter(mAttributeListAdapter);
    }

    private List<Map<String, String>> loadSensorAttributeData() {
        ArrayList<Map<String, String>> attributeList = new ArrayList<>();
        attributeList.add(putAttributeData("Vendor", mSensor.getVendor()));
        attributeList.add(putAttributeData("Version", Integer.toString(mSensor.getVersion())));
        attributeList.add(putAttributeData("Minimum Delay", Integer.toString(mSensor.getMinDelay())));
        attributeList.add(putAttributeData("Maximum Range", Float.toString(mSensor.getMaximumRange())));
        attributeList.add(putAttributeData("Resolution", Float.toString(mSensor.getResolution())));
        attributeList.add(putAttributeData("Power", Float.toString(mSensor.getPower())));

        return attributeList;
    }

    private Map<String, String> putAttributeData(String attributeName, String attributeValue) {
        HashMap<String, String> attributeItem = new HashMap<>();
        attributeItem.put(from[0], attributeName);
        attributeItem.put(from[1], attributeValue);
        return attributeItem;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        mSensorValuesText.setText("");
        float[] sensorValues = event.values;
        for (float value : sensorValues) {
            mSensorValuesText.append(String.format("%.4f", value) + ", ");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        mSensorAccuracyText.setText("Sensor Accuracy: " + Integer.toString(accuracy));
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mSensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }
}
