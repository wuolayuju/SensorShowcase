package uam.eps.es.sensorshowcase.actuators;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import uam.eps.es.sensorshowcase.R;

public class ScreenActivity extends AppCompatActivity {

    private Button mShowColorButton;
    private EditText mColorCodeEditText;
    private DrawerLayout mDrawerContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen);

        mShowColorButton = (Button) findViewById(R.id.show_color_screen_button);
        mColorCodeEditText = (EditText) findViewById(R.id.color_code_edit_text);
        mDrawerContainer = (DrawerLayout) findViewById(R.id.drawer_container);
    }

    public void drawSelectedColor(View view) {
        String colorCode = mColorCodeEditText.getText().toString();
        mDrawerContainer.setBackgroundColor(getIntFromColor(colorCode));
    }

    private int getIntFromColor(String colorCode) {

        return 0;
    }
}
