package com.mixi.ordrs.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.mixi.ordrs.R;

public class ShowBillFragment extends DialogFragment {

    private static final String ARG_BILL_AMOUNT = "bill_amount";

    private TextView mAmountTextView;

    public static ShowBillFragment newInstance (double amount) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_BILL_AMOUNT, amount);

        ShowBillFragment fragment = new ShowBillFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Double billAmount = (Double) getArguments().getSerializable(ARG_BILL_AMOUNT);
        billAmount = (double)Math.round(billAmount * 10d) / 10d;
        View v = LayoutInflater.from(getActivity())
                .inflate(R.layout.dialog_bill, null);

        mAmountTextView = (TextView) v.findViewById(R.id.bill_amount_text_view);
        mAmountTextView.setText(Double.toString(billAmount) + " €");

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setPositiveButton(android.R.string.ok, null)
                .create();
    }
}
