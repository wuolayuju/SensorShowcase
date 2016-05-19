package uam.eps.es.sensorshowcase.external_devices;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import uam.eps.es.sensorshowcase.R;

/**
 * Created by Ari on 19/05/2016.
 */
public class ExternalDevicesFragment extends Fragment {

    private TextView mInfoBTDevicesDiscovery;
    private Button mButtonRetryBTDiscovery;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mInfoBTDevicesDiscovery = (TextView) getActivity().findViewById(R.id.infoBluetoothDevicesFound);
        mButtonRetryBTDiscovery = (Button) getActivity().findViewById(R.id.retry_bluetooth_discovery_button);
    }
}
