package com.kdapps.piggybank;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;


public class PickerDialog extends DialogFragment {
    Context context;
    String sender_number, receiver_number, amt;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        context = getActivity();
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.dialog_picker, null);
        TextView nameView = (TextView) view.findViewById(R.id.nameText);
        TextView numberView = (TextView) view.findViewById(R.id.numberText);
        final NumberPicker amtPicker= (NumberPicker) view.findViewById(R.id.amtPicker);
        amtPicker.setMaxValue(10000000);
        amtPicker.setMinValue(0);
        Bundle bundle = getArguments();
        sender_number = Utils.getAppParam(context, Utils.PHONE_NUMBER);
        receiver_number =  bundle.getString(Utils.PHONE_NUMBER);
        nameView.setText(bundle.getString(Utils.DISPLAY_NAME));
        numberView.setText(receiver_number);
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(view)
               .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {

                       amt = String.valueOf(amtPicker.getValue());
                       Switch choiceSwitch = (Switch) view.findViewById(R.id.choiceSwitch);
                       if(!choiceSwitch.isChecked()){
                           amt = "-"+amt;
                       }
                       new HttpRequestTask().execute();
                   }
               })
               .setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialogInterface, int i) {

                   }
               });

        return builder.create();
    }

    public class HttpRequestTask extends AsyncTask<Void, Void, String> {
        ProgressDialog progressDialog = ProgressDialog.show(context,
                "Sending", "Please wait..");
        int responseCode;
        @Override
        protected String doInBackground(Void... params) {
            String regUrl = null ;

            regUrl = Utils.WEB_URL + "sendnotification.php?sender_number=" + sender_number + "&=receiver_number" + receiver_number + "&amt=" + amt;

            Log.i(Utils.TAG, regUrl);
            RestClient httpClient = new RestClient(regUrl);
            try {
                httpClient.Execute(RestClient.RequestMethod.GET);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            Log.d(Utils.TAG,  httpClient.getResponse());
            responseCode = httpClient.getResponseCode();
            return httpClient.getResponse();
        }

        protected void onPreExecute() {
            progressDialog.show();
        }
        protected void onPostExecute(String result) {
            progressDialog.cancel();

            Log.d(Utils.TAG, result);

        }
    }
}
