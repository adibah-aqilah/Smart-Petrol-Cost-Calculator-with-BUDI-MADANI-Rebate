package com.example.smartpetrolcostcalculatorwithbudimadanirebatemalaysia;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.textfield.TextInputEditText;
import java.util.Locale;

public class Home extends Fragment {

    //Declare component
    private AutoCompleteTextView spinnerPetrolType;
    private RadioGroup rgEligibility;
    private RadioButton rbYes, rbNo;
    private TextInputEditText etPetrolPrice, etFuelUsage;
    private Button btnCalculate, btnClear;
    private TextView tvTotalCost, tvBudiRebate, tvTotalSaving;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home, container, false);

        //Connect component with ID in XML
        spinnerPetrolType = view.findViewById(R.id.spinnerPetrolType);
        rgEligibility = view.findViewById(R.id.rgEligibility);
        rbYes = view.findViewById(R.id.rbYes);
        rbNo = view.findViewById(R.id.rbNo);
        etPetrolPrice = view.findViewById(R.id.etPetrolPrice);
        etFuelUsage = view.findViewById(R.id.etFuelUsage);
        btnCalculate = view.findViewById(R.id.btnCalculate);
        btnClear = view.findViewById(R.id.btnClear);
        tvTotalCost = view.findViewById(R.id.tvTotalCost);
        tvBudiRebate = view.findViewById(R.id.tvBudiRebate);
        tvTotalSaving = view.findViewById(R.id.tvTotalSaving);

        //dropdown for petrol
        setupDropdown();

        //event listener for calculate button
        btnCalculate.setOnClickListener(v -> performCalculation());

        //event listener for clear button
        btnClear.setOnClickListener(v -> clearAllInputs());

        return view;
    }

    private void setupDropdown() {
        //Type of petrol
        String[] petrol_types = {"RON95", "RON97", "Diesel"};
        ArrayAdapter<String> petrol_adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, petrol_types);
        spinnerPetrolType.setAdapter(petrol_adapter);
    }

    private void performCalculation() {
        //Call the data from the input
        String selected_petrol = spinnerPetrolType.getText().toString().trim();
        String petrol_price_str = etPetrolPrice.getText().toString().trim();
        String fuel_usage_str = etFuelUsage.getText().toString().trim();

        //if else for radio button
        String is_eligible = "NO";
        if (rgEligibility.getCheckedRadioButtonId() == R.id.rbYes) {
            is_eligible = "YES";
        }

        //if else for empty text validation
        if (selected_petrol.isEmpty() || petrol_price_str.isEmpty() || fuel_usage_str.isEmpty()) {
            Toast.makeText(requireContext(), "Please fill in all details", Toast.LENGTH_SHORT).show();
            return;
        }

        // if else for "." validation
        if (petrol_price_str.equals(".") || fuel_usage_str.equals(".")) {
            Toast.makeText(requireContext(), "Please enter a valid number, not just a decimal point", Toast.LENGTH_SHORT).show();
            return;
        }

        //change data type to double for number
        double petrol_price_per_liter = Double.parseDouble(petrol_price_str);
        double fuel_usage = Double.parseDouble(fuel_usage_str);

        //formula for total petrol cost
        double total_petrol_cost = fuel_usage * petrol_price_per_liter;
        double budi_rebate = 0.0;

        //if else for RON95 and eligible for BUDI MADANI
        if (selected_petrol.equals("RON95") && is_eligible.equals("YES")) {
            budi_rebate = fuel_usage * 1.99;
        }

        //formula for total saving cost
        double total_saving = total_petrol_cost - budi_rebate;

        //display final results
        tvTotalCost.setText(String.format(Locale.getDefault(), "RM %.2f", total_petrol_cost));
        tvBudiRebate.setText(String.format(Locale.getDefault(), "RM %.2f", budi_rebate));
        tvTotalSaving.setText(String.format(Locale.getDefault(), "RM %.2f", total_saving));
    }

    private void clearAllInputs() {
        etPetrolPrice.setText("");
        etFuelUsage.setText("");

        spinnerPetrolType.setText("", false);

        rbYes.setChecked(true);

        tvTotalCost.setText("RM 0.00");
        tvBudiRebate.setText("RM 0.00");
        tvTotalSaving.setText("RM 0.00");

        etPetrolPrice.clearFocus();
        etFuelUsage.clearFocus();

        Toast.makeText(requireContext(), "Inputs cleared", Toast.LENGTH_SHORT).show();
    }
}