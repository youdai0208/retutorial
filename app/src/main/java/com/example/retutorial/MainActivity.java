package com.example.retutorial;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String PLUS = "plus";
    private final String MINUS = "minus";
    private final String MULTIPLY = "multiply";
    private final String DIVIDE = "divide";

    private TextView cal_result;
    private List<Double> num_list = new ArrayList<Double>();
    private List<String> action_list = new ArrayList<String>();
    private String enter_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Button> buttonList = new ArrayList<>();
        buttonList.add((Button) findViewById(R.id.button_0));

        ButtonListener listener = new ButtonListener();
        for (Button button : buttonList){
            button.setOnClickListener(listener);
        }
    }

    private void addText(TextView text, String add_string){
        if (text.getText().toString() != null){
            text.setText(text.getText().toString() + add_string);
        }
    }

    private Double stringToDouble(TextView text){
        return Double.parseDouble(text.getText().toString());
    }

    private String calculate(){
        if (action_list.contains("multi"))
    }

    private class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.button_0:
                    addText(cal_result, "0");
                    break;
                case R.id.button_plus:
                    num_list.add(stringToDouble(cal_result));
                    action_list.add("plus");
                    break;
                case R.id.button_equal:
                    addText(cal_result, calculate());
                    break;
            }
        }
    }

}