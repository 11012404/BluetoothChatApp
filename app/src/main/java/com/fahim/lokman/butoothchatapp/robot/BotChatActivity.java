package com.fahim.lokman.bluetoothchatapp.robot;

import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.fahim.lokman.bluetoothchatapp.R;
import com.fahim.lokman.bluetoothchatapp.adapter.BotMessageAdapter;
import com.fahim.lokman.bluetoothchatapp.contents.Constant;
import com.fahim.lokman.bluetoothchatapp.contents.MessageContents;
import com.fahim.lokman.bluetoothchatapp.dbHelper.DBHandler;

import java.util.ArrayList;


public class BotChatActivity extends AppCompatActivity {


    private static final String TAG = "BluetoothChatFragment";
    public static final String ME = "ME";
    public static final String BOT = "BOT";

    public static final String VOICE = "ITISAVOICEMESSAGE";
    public static final String PHOTO = "ITISAPHOTOMESSAGE";
    public static final String FILE = "ITISAFILEMESSAGE";
    public static String EXTENSION;
    // Layout Views
    private ListView mConversationView;
    private EditText mOutEditText;
    public static ImageButton mSendButton;
    public static RelativeLayout statusBar;
    private ImageButton addImage, addFile;
    public static ImageButton recordingButton;
    public static String CONNECTED_DEVICE_ADDRESS;
    public static String lastSentImage = "";
    public static String FLAG;
    private static ArrayAdapter<String> mConversationArrayAdapter;
    private static BotMessageAdapter messageAdapter;
    public static ArrayList<MessageContents> messageContentses = new ArrayList<>();
    public static DBHandler dbHandler;
    /**
     * Local Bluetooth adapter
     */
    private BluetoothAdapter mBluetoothAdapter = null;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_bluetooth_chat);

        dbHandler = new DBHandler(this);

        addImage = (ImageButton) findViewById(R.id.addImage);
        addFile = (ImageButton) findViewById(R.id.addFile);
        mConversationView = (ListView) findViewById(R.id.in);
        mOutEditText = (EditText) findViewById(R.id.edit_text_out);
        mSendButton = (ImageButton) findViewById(R.id.button_send);
        recordingButton = (ImageButton) findViewById(R.id.record);

        addFile.setVisibility(View.GONE);
        addImage.setVisibility(View.GONE);
        recordingButton.setVisibility(View.GONE);

        setupChat();

    }

    private void setupChat() {

        //Load data into arraylist
        messageContentses = dbHandler.getAllMessages(ME , BOT);

        // Initialize the array adapter for the conversation thread
        messageAdapter = new BotMessageAdapter(this, messageContentses);
        mConversationView.setAdapter(messageAdapter);


        // Initialize the send button with a listener that for click events
        mSendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String message = mOutEditText.getText().toString();
                mOutEditText.setText("");

                dbHandler.saveMessage(message,ME,BOT,Constant.TEXTS);

                MessageContents messageContents = new MessageContents();
                messageContents.message = message;
                messageContents.receiver = BOT;
                messageContents.sender = ME;
                messageContents.type = Constant.TEXTS;

                messageContentses.add(messageContents);
                messageAdapter.notifyDataSetChanged();

                ArrayList<MessageContents> tempList  = dbHandler.getBotReply(message);


                for(int i=0;i<tempList.size();i++){
                    messageContentses.add(tempList.get(i));
                    messageAdapter.notifyDataSetChanged();
                    dbHandler.saveMessage(tempList.get(i).message,BOT,ME,Constant.TEXTS);
                }
            }
        });


    }




}
