package uam.eps.es.sensorshowcase;

import android.support.v4.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Ari on 18/05/2016.
 */
public class SensorListFragment extends ListFragment implements AdapterView.OnItemClickListener {

    private SensorManager mSensorManager;
    private SimpleAdapter mSensorListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sensor_list, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);

        List<Map<String, String>> sensorList = loadSensorList();
        mSensorListAdapter = new SimpleAdapter(
                getActivity(),
                sensorList,
                R.layout.list_item_sensor,
                new String[]{"sensor_name", "sensor_vendor"},
                new int[]{R.id.item_sensor_name, R.id.item_sensor_info});
        setListAdapter(mSensorListAdapter);

        getListView().setOnItemClickListener(this);
    }

    private List<Map<String, String>> loadSensorList() {
        ArrayList<Map<String, String>> sensorList = new ArrayList<>();
        List<Sensor> detectedSensorsList = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        for (Sensor sensor : detectedSensorsList) {
            sensorList.add(putSensorData(sensor.getName(), sensor.getVendor(), sensor.getType()));
        }
        return sensorList;
    }

    private Map<String, String> putSensorData(String name, String vendor, int type) {
        HashMap<String, String> sensorItem = new HashMap<>();
        sensorItem.put("sensor_name", name);
        sensorItem.put("sensor_vendor", vendor);
        sensorItem.put("sensor_type", Integer.toString(type));
        return sensorItem;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Map<String, String> sensorMap = (Map<String, String>) mSensorListAdapter.getItem(position);
        int sensor_type = Integer.parseInt(sensorMap.get("sensor_type"));
        Intent sensorActivityIntent = new Intent(getActivity(), SensorActivity.class);
        sensorActivityIntent.putExtra("SENSOR_TYPE", sensor_type);
        startActivity(sensorActivityIntent);
    }
}
