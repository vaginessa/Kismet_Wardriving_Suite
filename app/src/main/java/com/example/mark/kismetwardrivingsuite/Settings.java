package com.example.mark.kismetwardrivingsuite;

import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.ByteArrayOutputStream;
import java.util.Properties;

/**
 * Created by mark on 05/08/15.
 */
public class Settings extends Fragment {
    public static EditText username;
    public static EditText password;
    public static EditText storage;
    public static EditText address;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings, container, false);
        username = (EditText) view.findViewById(R.id.editText4);
        password = (EditText) view.findViewById(R.id.editText2);
        storage = (EditText) view.findViewById(R.id.editText);
        address = (EditText) view.findViewById(R.id.editText3);
        username.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        password.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        storage.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        address.setInputType(InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        username.getBackground().setColorFilter(getResources().getColor(R.color.customred), PorterDuff.Mode.SRC_ATOP);
        password.getBackground().setColorFilter(getResources().getColor(R.color.customred), PorterDuff.Mode.SRC_ATOP);
        address.getBackground().setColorFilter(getResources().getColor(R.color.customred), PorterDuff.Mode.SRC_ATOP);
        storage.getBackground().setColorFilter(getResources().getColor(R.color.customred), PorterDuff.Mode.SRC_ATOP);
        Button captures = (Button) view.findViewById(R.id.button4);
        captures.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncTask<Integer, Void, Void>() {
                    @Override
                    protected Void doInBackground(Integer... params) {
                        try {
                                //executeRemoteCommand("root", "changeme", "127.0.0.1", 22);
                                executeRemoteCommand(Settings.username.getText().toString(),Settings.password.getText().toString(),Settings.address.getText().toString(),22);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                }.execute(1);
            }
        });
        return view;

    }

    public static Settings newInstance(String text) {

        Settings f = new Settings();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }
    public static String executeRemoteCommand(String username,String password,String hostname,int port)
            throws Exception {
        JSch jsch = new JSch();
        Session session = jsch.getSession(username, hostname, port);
        session.setPassword(password);

        // Avoid asking for key confirmation
        Properties prop = new Properties();
        prop.put("StrictHostKeyChecking", "no");
        session.setConfig(prop);

        session.connect();

        // SSH Channel
        ChannelExec channelssh = (ChannelExec)
                session.openChannel("exec");
        channelssh.setOutputStream(ControlPanel.baos);

        // Execute command
        channelssh.setCommand("/scripts/KismetWardrivingSuite.sh captures");
        channelssh.connect();
        Thread.sleep(1000);
        channelssh.disconnect();
     return ControlPanel.baos.toString();
    }

}