package uam.eps.es.sensorshowcase.input_devices;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import uam.eps.es.sensorshowcase.input_devices.CameraActivity;

import static android.hardware.Camera.*;

/**
 * Created by Ari on 18/05/2016.
 */
@SuppressWarnings("deprecation")
public class InputDevicesListFragment extends ListFragment implements AdapterView.OnItemClickListener {

    private final int CAMERA = 0;
    private final int MICROPHONE = 1;

    private final String[] inputItemTags = {"input_name", "input_info"};
    private final int[] inputItemLayoutIds = {R.id.item_title_large, R.id.item_info_medium};
    private SimpleAdapter mInputListAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        List<Map<String, String>> inputList = null;
        inputList = loadInputList();

        mInputListAdapter = new SimpleAdapter(
                getActivity(),
                inputList,
                R.layout.list_item_large,
                inputItemTags,
                inputItemLayoutIds);
        setListAdapter(mInputListAdapter);

        getListView().setOnItemClickListener(this);
    }

    private List<Map<String, String>> loadInputList() {
        ArrayList<Map<String, String>> inputList = new ArrayList<>();

        int numberOfCameras = getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            CameraInfo info = new CameraInfo();
            getCameraInfo(i, info);
            switch (info.facing) {
                case CameraInfo.CAMERA_FACING_FRONT:
                    inputList.add(putInputData("Camera " + i, "Front Camera", CAMERA, i));
                    break;
                case CameraInfo.CAMERA_FACING_BACK:
                    inputList.add(putInputData("Camera " + i, "Back Camera", CAMERA, i));
                    break;
                default:
                    break;
            }
        }

        return inputList;
    }

    private Map<String, String> putInputData(String inputName, String inputInfo, int inputType, int inputId) {
        HashMap<String, String> inputMap = new HashMap<>();
        inputMap.put(inputItemTags[0], inputName);
        inputMap.put(inputItemTags[1], inputInfo);
        inputMap.put("input_type", String.valueOf(inputType));
        inputMap.put("input_id", String.valueOf(inputId));
        return inputMap;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Map<String, String> input = (Map<String, String>) mInputListAdapter.getItem(position);
        int inputType = Integer.parseInt(input.get("input_type"));
        if (inputType == CAMERA) {
            int cameraId = Integer.parseInt(input.get("input_id"));
            String cameraName = input.get("input_info");
            Intent cameraActivityIntent = new Intent(getActivity(), CameraActivity.class);
            cameraActivityIntent.putExtra("CAMERA_NAME", cameraName);
            cameraActivityIntent.putExtra("CAMERA_ID", cameraId);
            startActivity(cameraActivityIntent);
        }
    }
}
