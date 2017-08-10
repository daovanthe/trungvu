package th.com.th;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import th.com.serverBrigde.THServerBridge;

public class MainActivity extends AppCompatActivity {

    private THServerBridge socket;
    private EditText textMessage;
    private TextView IP_ServerText;
    private String PORT_DATA = "1901";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // LinearLayout friendList = (LinearLayout) findViewById(R.id.linear_friend_list);

        IP_ServerText = (TextView) findViewById(R.id.IPServer);
        textMessage = (EditText) findViewById(R.id.editText2);

    }


    public void submit(View view) {
        String input;
        Log.d("the", "TEXT: " + textMessage.getText().toString());

        if (!(input = textMessage.getText().toString()).equals("")) {
            socket = new THServerBridge();
            String[] inputs = {IP_ServerText.getText().toString(), PORT_DATA, input};
            socket.execute(inputs);
        }
        textMessage.setText("");
    }


}
