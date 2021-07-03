package com.example.retutorial;

import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MyActivity";

    private final String PLUS = "+";
    private final String MINUS = "-";
    private final String MULTIPLY = "×";
    private final String DIVIDE = "÷";
    private final String EQUAL = "=";
    private final String PRIOD = ".";
    private final String ZERO = "0";
    private final String DOUBLE_ZERO = "00";
    private final String ONE = "1";
    private final String TWO = "2";
    private final String TRHEE = "3";
    private final String FOUR = "4";
    private final String FIVE = "5";
    private final String SIX = "6";
    private final String SEVEN = "7";
    private final String EIGHT = "8";
    private final String NINE = "9";

    private final int NEXT_PLACE = 1;

    private TextView cal_result;
    private List<Double> num_list = new ArrayList<Double>();
    private List<String> action_list = new ArrayList<String>();
    private String last_mark;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        List<Button> buttonList = new ArrayList<>();
        buttonList.add(setButtonId(R.id.button_0));
        buttonList.add(setButtonId(R.id.button_00));
        buttonList.add(setButtonId(R.id.button_1));
        buttonList.add(setButtonId(R.id.button_2));
        buttonList.add(setButtonId(R.id.button_3));
        buttonList.add(setButtonId(R.id.button_4));
        buttonList.add(setButtonId(R.id.button_5));
        buttonList.add(setButtonId(R.id.button_6));
        buttonList.add(setButtonId(R.id.button_7));
        buttonList.add(setButtonId(R.id.button_8));
        buttonList.add(setButtonId(R.id.button_9));
        buttonList.add(setButtonId(R.id.button_c));
        buttonList.add(setButtonId(R.id.button_ce));
        buttonList.add(setButtonId(R.id.button_priod));
        buttonList.add(setButtonId(R.id.button_plus));
        buttonList.add(setButtonId(R.id.button_minus));
        buttonList.add(setButtonId(R.id.button_multi));
        buttonList.add(setButtonId(R.id.button_dvide));
        buttonList.add(setButtonId(R.id.button_equal));

        cal_result = findViewById(R.id.cul_result);

        ButtonListener listener = new ButtonListener();
        for (Button button : buttonList){
            button.setOnClickListener(listener);
        }
    }

    private Button setButtonId(@IdRes int id){
        return (Button) findViewById(id);
    }

    private void addCalText(String add_string){
        if (cal_result.getText().toString() != null){
            cal_result.setText(cal_result.getText().toString() + add_string);
        }else {
            switch (add_string){
                case ZERO:
                case DOUBLE_ZERO:
                case PLUS:
                case MINUS:
                case MULTIPLY:
                case DIVIDE:
                    break;
                default:
                    cal_result.setText(cal_result.getText().toString() + add_string);
                    break;
            }
        }
    }

    private Double stringToDouble(TextView text, String before_mark){
        String inText = text.getText().toString();
        String cut_result = new String();

        if (before_mark != null){
            int last_num = inText.lastIndexOf(before_mark);
            if (last_num != -1){
                cut_result = inText.substring(last_num + NEXT_PLACE);
            }else {
                cut_result = inText;
            }
        }else {
            cut_result = inText;
        }

        return Double.parseDouble(cut_result);
    }

    private String calculate(){
        boolean cannot_cal = false;
        Log.d(TAG, "Enter calculate");
        Log.d(TAG, Integer.toString(num_list.size()));
        while (num_list.size() > 1){
            AtomicReference<Double> stack = new AtomicReference<Double>();
            if (action_list.contains(MULTIPLY) || action_list.contains(DIVIDE)){
                Log.d(TAG, "Find Multi or Divide");
                int multiply_place = action_list.indexOf(MULTIPLY);
                int divide_place = action_list.indexOf(DIVIDE);
                if (multiply_place != -1){
                    stack.set(num_list.get(multiply_place) * num_list.get(multiply_place + NEXT_PLACE));
                    listControl(num_list, action_list, stack.get(), multiply_place);
                }
                else if (divide_place != -1){
                    if ((num_list.get(divide_place + NEXT_PLACE) == 0) || (num_list.get(divide_place + NEXT_PLACE) == 00)){
                        Toast toast = Toast.makeText(getApplicationContext(), "Can't divided by 0", Toast.LENGTH_SHORT);
                        toast.show();
                        cannot_cal = true;
                        break;
                    }
                    stack.set(num_list.get(divide_place) / num_list.get(divide_place + NEXT_PLACE));
                    listControl(num_list, action_list, stack.get(), divide_place);
                }
            }else if (action_list.contains(PLUS) || action_list.contains(MINUS)){
                Log.d(TAG, "Find Plus or Minus");
                int plus_place = action_list.indexOf(PLUS);
                int minus_place = action_list.indexOf(MINUS);
                if (plus_place != -1){
                    stack.set(num_list.get(plus_place) + num_list.get(plus_place + NEXT_PLACE));
                    listControl(num_list, action_list, stack.get(), plus_place);
                }
                else if (minus_place != -1) {
                    stack.set(num_list.get(minus_place) + num_list.get(minus_place + NEXT_PLACE));
                    listControl(num_list, action_list, stack.get(), minus_place);
                }
            }

            if (cannot_cal){
                break;
            }
        }

        return cannot_cal ? "Can't divided by 0!" : num_list.get(0).toString();
    }

    private void listControl(List<Double> num_list, List<String> act_list, Double result_val, int place){
        num_list.set(place, result_val);
        Log.d(TAG, num_list.get(place).toString());
        num_list.remove(place + NEXT_PLACE);
        act_list.remove(place);
    }

    private class ButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.button_0:
                    addCalText(ZERO);
                    break;
                case R.id.button_00:
                    addCalText(DOUBLE_ZERO);
                    break;
                case R.id.button_1:
                    addCalText(ONE);
                    break;
                case R.id.button_2:
                    addCalText(TWO);
                    break;
                case R.id.button_3:
                    addCalText(TRHEE);
                    break;
                case R.id.button_4:
                    addCalText(FOUR);
                    break;
                case R.id.button_5:
                    addCalText(FIVE);
                    break;
                case R.id.button_6:
                    addCalText(SIX);
                    break;
                case R.id.button_7:
                    addCalText(SEVEN);
                    break;
                case R.id.button_8:
                    addCalText(EIGHT);
                    break;
                case R.id.button_9:
                    addCalText(NINE);
                    break;
                case R.id.button_c:
                    num_list.clear();
                    action_list.clear();
                    cal_result.setText("");
                    break;
                case R.id.button_ce:
                    break;
                case R.id.button_priod:
                    addCalText(PRIOD);
                    break;
                case R.id.button_plus:
                    num_list.add(stringToDouble(cal_result, last_mark));
                    action_list.add(PLUS);
                    addCalText(PLUS);
                    last_mark = PLUS;
                    break;
                case R.id.button_minus:
                    num_list.add(stringToDouble(cal_result, last_mark));
                    action_list.add(MINUS);
                    addCalText(MINUS);
                    last_mark = MINUS;
                    break;
                case R.id.button_multi:
                    num_list.add(stringToDouble(cal_result, last_mark));
                    action_list.add(MULTIPLY);
                    addCalText(MULTIPLY);
                    last_mark = MULTIPLY;
                    break;
                case R.id.button_dvide:
                    num_list.add(stringToDouble(cal_result, last_mark));
                    action_list.add(DIVIDE);
                    addCalText(DIVIDE);
                    last_mark = DIVIDE;
                    break;
                case R.id.button_equal:
                    num_list.add(stringToDouble(cal_result, last_mark));
                    addCalText(EQUAL);
                    addCalText(calculate());
                    last_mark = EQUAL;
                    break;
            }
        }
    }

}