package com.example.myapplication;

import android.app.Activity;
import android.Manifest;
import android.content.pm.PackageManager;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends Activity {
    DatabaseHelper dbHelper; // Initialize DatabaseHelper
    Button btnSend;
    EditText tvMessage, tvNumber, tvDeadline, tvIncentive, tvTimeTaken;
    IntentFilter intentFilter;
    ListView chatListView;
    ArrayList<String> messagesList;
    ChatAdapter chatAdapter;
    //Declare genetic algorithm
    GeneticAlgorithm geneticAlgorithm;
    ArrayList<Message> messages;
    // BroadcastReceiver to handle received SMS messages
    private BroadcastReceiver intentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals("android.provider.Telephony.SMS_RECEIVED")) {
                Bundle bundle = intent.getExtras();

                if (bundle != null) {
                    // Retrieve the SMS message received
                    Object[] pdus = (Object[]) bundle.get("pdus");
                    StringBuilder fullMessage = new StringBuilder();
                    if (pdus != null) {
                        for (Object pdu : pdus) {
                            SmsMessage smsMessage;
                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                                String format = bundle.getString("format");
                                smsMessage = SmsMessage.createFromPdu((byte[]) pdu, format);
                            } else {
                                smsMessage = SmsMessage.createFromPdu((byte[]) pdu);
                            }
                            fullMessage.append(smsMessage.getMessageBody());
                        }

                        // Parse the received message into individual fields
                        String message = fullMessage.toString();
                        String[] messageParts = message.split("\n");
                        String request = messageParts[0].split(": ")[1];
                        String deadline = messageParts[1].split(": ")[1];
                        String incentive = messageParts[2].split(": ")[1];
                        String timeTaken = messageParts[3].split(": ")[1];

                        // Add the received message to the chat history
                        messagesList.add("Received: " + message);
                        chatAdapter.notifyDataSetChanged();  // Refresh ListView

                        // Insert the received message into the database
                        insertDataIntoDatabase(request, deadline, incentive, timeTaken);
                    }
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Request SMS permissions if needed
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.SEND_SMS, Manifest.permission.RECEIVE_SMS}, 1);
        }

        // Initialize DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        // Set up intent filter to listen for SMS_RECEIVED actions
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.provider.Telephony.SMS_RECEIVED");

        // Initialize Views and Adapter
        btnSend = findViewById(R.id.btnSend);
        tvMessage = findViewById(R.id.tvMessage);
        tvNumber = findViewById(R.id.tvNumber);
        tvDeadline = findViewById(R.id.tvDeadline);
        tvIncentive = findViewById(R.id.tvIncentive);
        tvTimeTaken = findViewById(R.id.tvTimeTaken);
        chatListView = findViewById(R.id.chatListView);
        geneticAlgorithm = new GeneticAlgorithm();
        messages = new ArrayList<>();
        messagesList = new ArrayList<>();
        chatAdapter = new ChatAdapter(this, messagesList);
        chatListView.setAdapter(chatAdapter);

        // Send Message
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String request = tvMessage.getText().toString();
                String number = tvNumber.getText().toString();
                String deadline = tvDeadline.getText().toString();
                String incentive = tvIncentive.getText().toString();
                String timeTaken = tvTimeTaken.getText().toString();

                String fullMessage = "Request: " + request + "\n" +
                        "Deadline: " + deadline + "\n" +
                        "Incentive: " + incentive + "\n" +
                        "Time Taken: " + timeTaken;

                sendMsg(number, fullMessage);

                // Add the sent message to the chat history
                messagesList.add("Me: " + fullMessage);
                chatAdapter.notifyDataSetChanged();  // Refresh ListView
                insertDataIntoDatabase(request,deadline,incentive,timeTaken);
                geneticAlgorithm.initializePopulation(messages);
                geneticAlgorithm.runAlgorithm();
                geneticAlgorithm.sendMessages(MainActivity.this);
            }
        });
    }

    // Method to insert data into the database
    private void insertDataIntoDatabase(String request, String deadline, String incentive, String timeTaken) {
        // Get writable database instance
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a ContentValues object to store the data
        ContentValues values = new ContentValues();
        values.put(DatabaseHelper.COLUMN_REQUEST, request);
        values.put(DatabaseHelper.COLUMN_DEADLINE, deadline);
        values.put(DatabaseHelper.COLUMN_INCENTIVE, incentive);
        values.put(DatabaseHelper.COLUMN_TIME_TAKEN, timeTaken);

        // Insert the data into the database
        long newRowId = db.insert(DatabaseHelper.TABLE_NAME, null, values);

        if (newRowId != -1) {
            Toast.makeText(this, "Data inserted successfully!", Toast.LENGTH_SHORT).show();
            messages.add(new Message(request,deadline,incentive,timeTaken));
        } else {
            Toast.makeText(this, "Error inserting data", Toast.LENGTH_SHORT).show();
        }
    }


    // Method to send an SMS message
    protected void sendMsg(String theNumber, String fullMessage) {
        String SENT = "message sent";
        String DELIVERED = "message delivered";
        PendingIntent sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        PendingIntent deliverPI = PendingIntent.getBroadcast(this, 0, new Intent(DELIVERED), PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        SmsManager sms = SmsManager.getDefault();

        Toast.makeText(getApplicationContext(), "Message Sent!", Toast.LENGTH_SHORT).show();
        sms.sendTextMessage(theNumber, null, fullMessage, sentPI, deliverPI);
    }

    @Override
    protected void onResume() {
        registerReceiver(intentReceiver, intentFilter);
        super.onResume();
    }

    @Override
    protected void onPause() {
        unregisterReceiver(intentReceiver);
        super.onPause();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted
                Toast.makeText(this, "Permission granted", Toast.LENGTH_SHORT).show();
            } else {
                // Permission denied
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
