package uam.eps.es.sensorshowcase;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private SensorManager mSensorManager;
    private ListView sensorListView;

    private String[] sensorAttributes = new String[]{"SENSOR_NAME", "SENSOR_TYPE", "SENSOR_VENDOR", "SENSOR_VERSION"};
    private SensorListAdapter mSensorListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensorListView = (ListView) findViewById(R.id.sensorListView);

        List<Sensor> detectedSensorsList = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        SensorPOJO[] sensorPojoList = new SensorPOJO[detectedSensorsList.size()];
        for (int i = 0; i < detectedSensorsList.size(); i++) {
            Sensor sensor = detectedSensorsList.get(i);
            SensorPOJO sensorPojo = new SensorPOJO(sensor.getName(), sensor.getVendor(), sensor.getType(), sensor.getVersion());
            sensorPojoList[i] = sensorPojo;
        }

        mSensorListAdapter = new SensorListAdapter(this, R.layout.sensor_list_item, sensorPojoList);

        sensorListView.setAdapter(mSensorListAdapter);
        sensorListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Context context = view.getContext();
                SensorPOJO sensorSelected = (SensorPOJO) parent.getAdapter().getItem(position);
                Intent intent = new Intent(context, SensorActivity.class);
                intent.putExtra("SENSOR_TYPE", sensorSelected.type);
                startActivity(intent);
            }
        });
    }
}
