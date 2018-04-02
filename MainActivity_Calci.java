package com.stenstudios.calci;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.regex.*;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    Button but0, but1, but2, but3, but4, but5, but6, but7, but8, but9;
    Button butEq, butAdd, butSub, butMul, butDiv;
    Button butSin, butCos, butSqrt, butDel;
    Button butDec;
    EditText edt;
    TextView tv;

    double res, subRes;
    int opd1, opd2;
    String prevOpn = "", opn, beforeSubResOpn = "";
    boolean isEqualPressed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        but0 = (Button) findViewById(R.id.button0);
        but1 = (Button) findViewById(R.id.button1);
        but2 = (Button) findViewById(R.id.button2);
        but3 = (Button) findViewById(R.id.button3);
        but4 = (Button) findViewById(R.id.button4);
        but5 = (Button) findViewById(R.id.button5);
        but6 = (Button) findViewById(R.id.button6);
        but7 = (Button) findViewById(R.id.button7);
        but8 = (Button) findViewById(R.id.button8);
        but9 = (Button) findViewById(R.id.button9);
        butEq = (Button) findViewById(R.id.buttonEq);
        butAdd = (Button) findViewById(R.id.buttonAdd);
        butSub = (Button) findViewById(R.id.buttonSub);
        butMul = (Button) findViewById(R.id.buttonMul);
        butDiv = (Button) findViewById(R.id.buttonDiv);
        butSin = (Button) findViewById(R.id.buttonSin);
        butCos = (Button) findViewById(R.id.buttonCos);
        butSqrt = (Button) findViewById(R.id.buttonSqrt);
        butDel = (Button) findViewById(R.id.buttonDel);
        butDec = (Button) findViewById(R.id.buttonDot);

        edt = (EditText) findViewById(R.id.editText);
        tv = (TextView) findViewById(R.id.textViewResult);

        edt.setFocusable(false);
        edt.setFocusableInTouchMode(false);

        but0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt.append("0");
            }
        });

        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt.append("1");

            }
        });

        but2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt.append("2");
            }
        });

        but3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt.append("3");
            }
        });

        but4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt.append("4");
            }
        });

        but5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt.append("5");
            }
        });

        but6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt.append("6");
            }
        });

        but7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt.append("7");
            }
        });

        but8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt.append("8");
            }
        });

        but9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt.append("9");
            }
        });

        butAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt.append("+");

            }
        });

        butSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt.append("-");
            }
        });

        butMul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt.append("*");
            }
        });

        butDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt.append("/");

            }
        });

        butEq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isEqualPressed = true;
                if(isValidInput()){
                    String inputString = edt.getText().toString();
                    String postfixString;

                    if(inputString.length()==2){
                        tv.setText(String.valueOf(inputString));
                    }
                    else {
                        postfixString = toPostfix(inputString);

                        Log.d("DEBUG", inputString);
                        Log.d("DEBUG", postfixString);

                        float result = evaluatePostfix(postfixString);

                        Log.d("DEBUG", "" + result);

                        tv.setText(String.valueOf(result));
                    }
                }
                else
                    edt.setError("Invalid Input");
            }
        });

        butDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isValidInput()) {
                    StringBuilder sb = new StringBuilder(edt.getText().toString());
                    sb.deleteCharAt(sb.length() - 1);
                    edt.setText(sb.toString());
                }
            }
        });

        butSin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isValidInput()) {
                    float val = Float.parseFloat(edt.getText().toString());
                    tv.setText("" + Math.sin(Math.toRadians(val)));
                }
                else
                    edt.setError("Invalid Input");
            }
        });

        butCos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isValidInput()) {
                    float val = Float.parseFloat(edt.getText().toString());
                    tv.setText("" + Math.cos(Math.toRadians(val)));
                }
                else
                    edt.setError("Invalid Input");
            }
        });

        butSqrt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isValidInput()) {
                    float val = Float.parseFloat(edt.getText().toString());
                    tv.setText("" + Math.sqrt(val));
                }
                else
                    edt.setError("Invalid Input");
            }
        });

        butDec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                edt.append(".");
            }
        });
    }

    boolean isValidInput(){
        if(edt.getText().toString().trim().length()==0){
            Log.d("DEBUG","input 0");
            return false;
        }
        if(isEqualPressed) {
            isEqualPressed = false;
            String regexTwoOp = "((\\d+[\\+\\-\\*\\/]\\d+){1}([\\+\\-\\*\\/]\\d+){0,})|((\\d+.?\\d+[\\+\\-\\*\\/]\\d+.?\\d+){1}([\\+\\-\\*\\/]\\d+.?\\d+){0,})";
            String regexOneOp = "([\\+\\-]\\d+)|([\\+\\-]\\d+.?\\d+)";
            String regexOp = "(\\d+)|(\\d+.?\\d+)";

            String input = edt.getText().toString();
            Log.d("DEBUG","input "+input);
            Pattern pOneOp = Pattern.compile(regexOneOp);
            Pattern pTwoOp = Pattern.compile(regexTwoOp);
            Pattern pOp = Pattern.compile(regexOp);

            Matcher mOneOp = pOneOp.matcher(input);
            Matcher mTwoOp = pTwoOp.matcher(input);
            Matcher mOp = pOp.matcher(input);

            if (mOneOp.matches())
                Log.d("DEBUG","Matches one op");
            else if (mTwoOp.matches())
                Log.d("DEBUG","Matches two op");
            else if (mOp.matches())
                Log.d("DEBUG","Matches op");
            else {
                Log.d("DEBUG","No Match");
                return false;
            }
        }
        return true;
    }

    String toPostfix(String inputString){
        String postfixString = "";
        Stack <String> opStack = new Stack<>();

        for(int i=0;i<inputString.length();i++){
            if(inputString.charAt(i) == '+'){
                postfixString += "=";
                if(opStack.isEmpty()){
                    opStack.push("+");
                }
                else{
                    String c;
                    while(!opStack.isEmpty()){
                        c = opStack.pop();
                        postfixString += c;
                    }
                    opStack.push("+");
                }
            }
            else if(inputString.charAt(i) == '-'){
                postfixString += "=";
                if(opStack.isEmpty()){
                    opStack.push("-");
                }
                else{
                    String c;
                    while(!opStack.isEmpty()){
                        c = opStack.pop();
                        postfixString += c;
                    }
                    opStack.push("-");
                }
            }
            else if(inputString.charAt(i) == '*'){
                postfixString += "=";
                if(opStack.isEmpty()){
                    opStack.push("*");
                }
                else{
                    String c = opStack.pop();
                    while(c.equals("*") || c.equals("/")){
                        postfixString += c;
                        if(!opStack.isEmpty())
                            c = opStack.pop();
                        else
                            break;
                    }
                    if(c.equals("+") || c.equals("-")){
                        opStack.push(c);
                    }
                    opStack.push("*");
                }
            }
            else if(inputString.charAt(i) == '/'){
                postfixString += "=";
                if(opStack.isEmpty()){
                    opStack.push("/");
                }
                else{
                    String c = opStack.pop();
                    while(c.equals("*") || c.equals("/")){
                        postfixString += c;
                        if(!opStack.isEmpty())
                            c = opStack.pop();
                        else
                            break;
                    }
                    if(c.equals("+") || c.equals("-")){
                        opStack.push(c);
                    }
                    opStack.push("/");
                }
            }
            else{
                postfixString += inputString.charAt(i);
            }
        }

        postfixString += "=";

        while(!opStack.isEmpty()){
            postfixString += opStack.pop();
        }

        return postfixString;
    }

    float evaluatePostfix(String postfixString){
        float result = 0;
        char[] operators = {'+','-','*','/'};
        Stack<Float> resStack = new Stack<>();

        for(int i=0;i<postfixString.length();i++){
            char c = postfixString.charAt(i);
            if(new String(operators).indexOf(c)>-1){
                float opd2 = resStack.pop();
                float opd1 = resStack.pop();

                switch(c){
                    case '+':
                        resStack.push(opd1+opd2);
                        break;
                    case '-':
                        resStack.push(opd1-opd2);
                        break;
                    case '*':
                        resStack.push(opd1*opd2);
                        break;
                    case '/':
                        resStack.push(opd1/opd2);
                        break;
                }
            }
            else{
                String floatNum = "";
                while( (c = postfixString.charAt(i)) != '=') {
                    i++;
                    floatNum += c;
                }
                resStack.push(Float.parseFloat(floatNum));
            }
        }

        result = resStack.pop();

        return result;
    }
}
