package com.example.android.justjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import io.fabric.sdk.android.Fabric;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 0;
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

    public void increment(View view) {
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    public void decrement(View view) {
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * Calculate the price of the order
     *
     * @return total price
     */
    public int calculatePrice() {
        int priceOfCoffee = 5;
        int price = quantity * priceOfCoffee;
        return price;
    }

    /**
     *
     * @param price
     * @return return the Order Summary
     */
    public String createOrderSummary(String name, int price, Boolean hasWhippedCream, Boolean hasChocolate){
        String nameInput = name;
        String whippedCream = "\nAdd: Whipped Cream";
        String chocolate = "\nAdd: Chocalate";
        String qnt = "\nQuantity: " + quantity;
        String total = "\nTotal: $" + price;
        String thank = "\nThank You!";
        String full = name;

        if (hasWhippedCream == true) {
            full += whippedCream;
        }
        if (hasChocolate == true) {
            full += chocolate;
        }
            full += qnt + total + thank;
            return full;
    }

    public void submitOrder(View view) {
        int price = calculatePrice();
        CheckBox whippedCream = (CheckBox) findViewById(R.id.checkbox_whippedCream_textview);
        Boolean hasWhippedCream = whippedCream.isChecked();
        CheckBox chocalate = (CheckBox) findViewById(R.id.checkbox_chocalate_textview);
        Boolean hasChocolate = chocalate.isChecked();

        EditText nameInput = (EditText) findViewById(R.id.name_input);
        String name = nameInput.getText().toString();

        displayMessage(createOrderSummary(name, price, hasWhippedCream, hasChocolate));
    }

    private void displayQuantity(int quantity) {
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
