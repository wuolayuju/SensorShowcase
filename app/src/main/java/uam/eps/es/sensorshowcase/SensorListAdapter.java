package uam.eps.es.sensorshowcase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Ari on 15/05/2016.
 */
public class SensorListAdapter extends ArrayAdapter<SensorPOJO> {

    private final Context mContext;
    private final int mResourceId;
    private final SensorPOJO[] mData;

    public SensorListAdapter(Context context, int resource, SensorPOJO[] objects) {
        super(context, resource, objects);

        mContext = context;
        mResourceId = resource;
        mData = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(mResourceId, parent, false);
        }

        SensorPOJO sensorItem = mData[position];

        TextView sensorName = (TextView) convertView.findViewById(R.id.item_title_large);
        TextView sensorInfo = (TextView) convertView.findViewById(R.id.item_info_medium);

        sensorName.setText(sensorItem.name);
        sensorInfo.setText(sensorItem.vendor + " version " + sensorItem.version);

        return convertView;
    }
}
