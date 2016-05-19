package uam.eps.es.sensorshowcase.actuators;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uam.eps.es.sensorshowcase.R;

/**
 * Created by Ari on 19/05/2016.
 */
public class ActuatorListFragment extends ListFragment implements AdapterView.OnItemClickListener{


    private String[] actuatorLabels = {"actuator_name", "actuator_info"};
    private int[] listItemIds = {R.id.item_title_large, R.id.item_info_medium};
    private SimpleAdapter mSimpleAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        List<Map<String, String>> actuatorList = loadActuatorList();
        mSimpleAdapter = new SimpleAdapter(
                getActivity(), actuatorList, R.layout.list_item_large,
                actuatorLabels, listItemIds);
        setListAdapter(mSimpleAdapter);

        getListView().setOnItemClickListener(this);
    }

    private List<Map<String, String>> loadActuatorList() {
        ArrayList<Map<String, String>> actuatorList = new ArrayList<>();
        actuatorList.add(putActuatorData("Screen", "Color and brightness"));
        actuatorList.add(putActuatorData("Vibration", "Frequency and duration"));
        actuatorList.add(putActuatorData("Frontal LEDs", "Notification color indicator"));
        actuatorList.add(putActuatorData("Flash", "Back Camera Flash"));
        actuatorList.add(putActuatorData("Speakers", "Audio playback"));
        return actuatorList;
    }

    private Map<String, String> putActuatorData(String actuatorName, String actuatorInfo) {
        HashMap<String, String> actuatorItem = new HashMap<>();
        actuatorItem.put("actuator_name", actuatorName);
        actuatorItem.put("actuator_info", actuatorInfo);
        return actuatorItem;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Map<String, String> actuator = (Map<String, String>)
                mSimpleAdapter.getItem(position);
        if (actuator.get("actuator_name") == "Screen") {
            Intent screenIntent = new Intent(getActivity(), ScreenActivity.class);
            startActivity(screenIntent);
        }
    }
}
