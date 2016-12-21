package com.example.android.justjava;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import io.fabric.sdk.android.Fabric;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void toast(View view) {
        Context context = getApplicationContext();
        CharSequence text = "You cannot have more then " + quantity;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void increment(View view) {
        if (quantity == 100) {
            toast(getCurrentFocus());
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    public void decrement(View view) {
        if (quantity <= 1) {
            toast(getCurrentFocus());
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * Calculate the price of the order
     *
     * @return total price
     */
    public int calculatePrice(Boolean hasWhippedCream, Boolean hasChocolate) {
        int priceOfCoffee = 5;

        if (hasWhippedCream){
            priceOfCoffee = priceOfCoffee + 1;
        }

        if (hasChocolate) {
            priceOfCoffee = priceOfCoffee + 2;
        }

        int price = quantity * priceOfCoffee;
        return price;
    }

    /**
     *
     * @param price
     * @return return the Order Summary
     */
    public String createOrderSummary(String name, int price, Boolean hasWhippedCream, Boolean hasChocolate){
        String nameInput = "Name: " + name;
        String whippedCream = "\nAdd: Whipped Cream";
        String chocolate = "\nAdd: Chocalate";
        String qnt = "\nQuantity: " + quantity;
        String total = "\nTotal: $" + price;
        String thank = "\nThank You!";
        String full = nameInput;

        if (hasWhippedCream == true) {
            full += whippedCream;
        }
        if (hasChocolate == true) {
            full += chocolate;
        }
            full += qnt + total + thank;
            return full;
    }

    public void submitOrderEmail(String full, String name) {
        String email = new String("dcca12@gmail.com");
        Intent createEmail = new Intent(Intent.ACTION_SENDTO);
        createEmail.setData(Uri.parse("mailto:"));
        createEmail.putExtra(Intent.EXTRA_SUBJECT, "JustJava order for " + name);
        createEmail.putExtra(Intent.EXTRA_EMAIL, "dcca12@gmail.com");
        createEmail.putExtra(Intent.EXTRA_TEXT, full);
        if (createEmail.resolveActivity(getPackageManager()) != null) {
            startActivity(createEmail);
        }


    }

    public void submitOrder(View view) {
        //Check if the Whipped Cream is selected
        CheckBox whippedCream = (CheckBox) findViewById(R.id.checkbox_whippedCream_textview);
        Boolean hasWhippedCream = whippedCream.isChecked();

        //Check if the Chocalate is selected
        CheckBox chocalate = (CheckBox) findViewById(R.id.checkbox_chocalate_textview);
        Boolean hasChocolate = chocalate.isChecked();

        // Get the name input in the EditText field
        EditText nameInput = (EditText) findViewById(R.id.name_input);
        String name = nameInput.getText().toString();

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        displayMessage(createOrderSummary(name, price, hasWhippedCream, hasChocolate));
        submitOrderEmail(createOrderSummary(name, price, hasWhippedCream, hasChocolate), name);
    }

    private void displayQuantity(int quantity) {
        //Find the view that display the quantity
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + quantity);


    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

}
