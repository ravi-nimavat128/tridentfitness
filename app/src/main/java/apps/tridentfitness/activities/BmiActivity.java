package apps.tridentfitness.activities;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import apps.tridentfitness.R;

public class BmiActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edt_weight, edt_feet, edt_inche, edt_height;
    TextView btn_kg, btn_cm, btn_in, btn_lb, txt_bmians, btn_calculate;
    boolean isKg = true, isCm = true, isLb, isInche;
    LinearLayout lay_inches;
    private double heightValue, bmi, weightValue;
    private String weight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(Color.WHITE);
        setContentView(R.layout.activity_bmi);
        init();
    }

    private void init() {
        edt_weight = findViewById(R.id.edt_weight);
        edt_height = findViewById(R.id.edt_height);
        edt_feet = findViewById(R.id.edt_feet);
        edt_inche = findViewById(R.id.edt_inche);
        btn_kg = findViewById(R.id.btn_kg);
        btn_cm = findViewById(R.id.btn_cm);
        btn_in = findViewById(R.id.btn_in);
        btn_lb = findViewById(R.id.btn_lb);
        btn_calculate = findViewById(R.id.btn_calculate);
        txt_bmians = findViewById(R.id.txt_bmians);
        lay_inches = findViewById(R.id.lay_inches);

        btn_kg.setOnClickListener(this);
        btn_cm.setOnClickListener(this);
        btn_in.setOnClickListener(this);
        btn_lb.setOnClickListener(this);
        btn_calculate.setOnClickListener(this);

    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {
        int viewId = v.getId();
        switch (viewId) {
            case R.id.btn_kg:

                btn_kg.setBackground(ContextCompat.getDrawable(this, R.drawable.btn_selected_weight));
                btn_lb.setBackground(ContextCompat.getDrawable(this, R.drawable.btn_unselected_weight));
                btn_kg.setTextColor(getResources().getColor(R.color.white));
                btn_lb.setTextColor(getResources().getColor(R.color.black));
                if (isInche) {
                    edt_height.setVisibility(View.GONE);
                    lay_inches.setVisibility(View.VISIBLE);
                } else {
                    edt_height.setVisibility(View.VISIBLE);
                    lay_inches.setVisibility(View.GONE);
                }
                weight = edt_weight.getText().toString().trim();
                if (!weight.isEmpty() && isLb) {
                    double kg = Double.parseDouble(weight) / 2.205;
                    float value = (float) Math.round(kg * 100) / 100;
                    edt_weight.setText("" + value);
                }
                isKg = true;
                isLb = false;
                break;
            case R.id.btn_cm:

                btn_cm.setBackground(ContextCompat.getDrawable(this, R.drawable.btn_selected_weight));
                btn_in.setBackground(ContextCompat.getDrawable(this, R.drawable.btn_unselected_weight));
                btn_cm.setTextColor(getResources().getColor(R.color.white));
                btn_in.setTextColor(getResources().getColor(R.color.black));
                edt_height.setVisibility(View.VISIBLE);
                lay_inches.setVisibility(View.GONE);
                isCm = true;
                isInche = false;
                InchtoCmConvert();
                break;
            case R.id.btn_in:

                btn_in.setBackground(ContextCompat.getDrawable(this, R.drawable.btn_selected_weight));
                btn_cm.setBackground(ContextCompat.getDrawable(this, R.drawable.btn_unselected_weight));
                btn_in.setTextColor(getResources().getColor(R.color.white));
                btn_cm.setTextColor(getResources().getColor(R.color.black));
                edt_height.setVisibility(View.GONE);
                lay_inches.setVisibility(View.VISIBLE);
                isCm = false;
                isInche = true;
                cmToInchConvert();
                break;
            case R.id.btn_lb:

                btn_lb.setBackground(ContextCompat.getDrawable(this, R.drawable.btn_selected_weight));
                btn_kg.setBackground(ContextCompat.getDrawable(this, R.drawable.btn_unselected_weight));
                btn_lb.setTextColor(getResources().getColor(R.color.white));
                btn_kg.setTextColor(getResources().getColor(R.color.black));
                weight = edt_weight.getText().toString().trim();
                if (!weight.isEmpty() && isKg) {
                    double lbs = Double.parseDouble(weight) * 2.205;
                    float value = (float) Math.round(lbs * 100) / 100;
                    edt_weight.setText("" + value);
                }
                if (isInche) {
                    edt_height.setVisibility(View.GONE);
                    lay_inches.setVisibility(View.VISIBLE);
                } else {
                    edt_height.setVisibility(View.VISIBLE);
                    lay_inches.setVisibility(View.GONE);
                }
                isKg = false;
                isLb = true;
                break;
            case R.id.btn_calculate:
                BMICalculate();
                break;
        }
    }

    private void InchtoCmConvert() {

        String feet_ = edt_feet.getText().toString();
        String inch_ = edt_inche.getText().toString();
        if (!feet_.equals("") || !inch_.equals("")) {
            Double totalInches = (Double.parseDouble(feet_) * 12) + Double.parseDouble(inch_);

            double cm_ = totalInches * 2.54;
            float value = (float) Math.round(cm_ * 100) / 100;
            edt_height.setText("" + (int) value);
        }
    }

    public double Conversion(Double centi) {

        double feet = centi / 30.48;
        double inches = (centi / 2.54) - ((int) feet * 12);
        NumberFormat formatter = new DecimalFormat("###.000");
        String ansfeet = "" + formatter.format(feet);
        String[] separated = ansfeet.split("\\.");
        String feets = separated[0].trim();
        formatter = new DecimalFormat("##.00");
        edt_feet.setText("" + feets);
        edt_inche.setText("" + formatter.format(inches));
        return 0;
    }

    private void cmToInchConvert() {
        String cm = edt_height.getText().toString().trim();
        if (!cm.equals("")) {
            Conversion(Double.parseDouble(cm));
        }
    }

    private void BMICalculate() {
        NumberFormat formatter = new DecimalFormat("##.00");
        String height = edt_height.getText().toString().trim();
        String weight = edt_weight.getText().toString().trim();

        if (!weight.isEmpty()) {

            if (isKg && isCm) {
                heightValue = Double.parseDouble(edt_height.getText().toString().trim()) / 100;
                weightValue = Double.parseDouble(edt_weight.getText().toString().trim());
                bmi = weightValue / (heightValue * heightValue);

                displayBMI(Double.parseDouble(formatter.format(bmi)));
            } else if (isKg && isInche) {
                String feet = edt_feet.getText().toString().trim();
                String inches = edt_inche.getText().toString().trim();
                Double feetvalues = (Double.parseDouble(feet) * 12) + (Double.parseDouble(inches));
                heightValue = (feetvalues) / 39.37;
                formatter = new DecimalFormat("##.00");
                String heightValues = formatter.format(heightValue);

                weightValue = Double.parseDouble(edt_weight.getText().toString().trim());
                bmi = weightValue / (Double.parseDouble(heightValues) * Double.parseDouble(heightValues));
                displayBMI(Double.parseDouble(formatter.format(bmi)));
            } else if (isLb && isInche) {

                String feet = edt_feet.getText().toString().trim();
                String inches = edt_inche.getText().toString().trim();
                Double feetvalues = (Double.parseDouble(feet) * 12) + (Double.parseDouble(inches));

                heightValue = (feetvalues) /*/ 3.281*/;
                weightValue = Double.parseDouble(edt_weight.getText().toString().trim());
                bmi = (weightValue * 703 / (heightValue * heightValue));
                displayBMI(Double.parseDouble(formatter.format(bmi)));
            } else if (isLb && isCm) {
                String cms = edt_height.getText().toString().trim();
                double cms_value = Double.parseDouble(cms) / 2.54;
                heightValue = cms_value;
                weightValue = Double.parseDouble(edt_weight.getText().toString().trim());
                bmi = weightValue * 703 / (heightValue * heightValue);

                displayBMI(Double.parseDouble(formatter.format(bmi)));
            }
        }
    }

    private void displayBMI(double bmi) {
        String bmiLabel = "";

        if (Double.compare(bmi, 18.5f) <= 0) {
            bmiLabel = getString(R.string.underweight);
            txt_bmians.setTextColor(getResources().getColor(R.color.blues));
        } else if (Double.compare(bmi, 18.5f) > 0 && Double.compare(bmi, 25f) <= 0) {
            bmiLabel = getString(R.string.normal);
            txt_bmians.setTextColor(getResources().getColor(R.color.green));
        } else if (Double.compare(bmi, 25f) > 0 && Double.compare(bmi, 30f) <= 0) {
            bmiLabel = getString(R.string.overweight);
            txt_bmians.setTextColor(getResources().getColor(R.color.yellow));
        } else if (Double.compare(bmi, 30f) > 0 && Double.compare(bmi, 35f) <= 0) {
            bmiLabel = getString(R.string.obese_class_i);
            txt_bmians.setTextColor(getResources().getColor(R.color.orange));
        } else if (Double.compare(bmi, 35f) > 0 && Double.compare(bmi, 40f) <= 0) {
            bmiLabel = getString(R.string.obese_class_ii);
            txt_bmians.setTextColor(getResources().getColor(R.color.colorAccent));
        } else {
            bmiLabel = getString(R.string.obese_class_iii);
        }

        bmiLabel = bmi + " " + bmiLabel;
        NumberFormat formatter = new DecimalFormat("##.00");
        txt_bmians.setText(/*formatter.format*/(bmiLabel));
    }
}
