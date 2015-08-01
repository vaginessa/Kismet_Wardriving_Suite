package com.example.mark.kismetwardrivingsuite;

import android.media.tv.TvInputService;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.jcraft.jsch.ChannelShell;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Properties;

/**
 * Created by mark on 01/08/15.
 */
public class ControlPanel extends Fragment {
    Switch kismet;
    Switch gpsd;
    Button giskismet;
    Button exit;
    public static ByteArrayOutputStream baos= new ByteArrayOutputStream();
    public static Session session;
    public static int Setupdone=0;
    public static PrintStream commander;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.control_panel, container, false);
        Switch kismet = (Switch) v.findViewById(R.id.switch2);
        Switch gpsd = (Switch) v.findViewById(R.id.switch1);
        Button giskismet = (Button) v.findViewById(R.id.button);
        Button exit = (Button) v.findViewById(R.id.button2);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commander.close();
                session.disconnect();
                getActivity().getSupportFragmentManager().popBackStack();
                System.exit(0);
            }
        });
        giskismet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                commander.println("/scripts/Giskismet.sh");
            }
        });
        gpsd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    new AsyncTask<Integer, Void, Void>() {
                        @Override
                        protected Void doInBackground(Integer... params) {
                            try {
                                if (Setupdone == 0)
                                    executeRemoteCommand("root", "changeme", "127.0.0.1", 22);
                                Setupdone = 1;
                                commander.println("gpsd -n -N -D5 tcp://localhost:4352 &");


                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            return null;
                        }
                    }.execute(1);
                } else {
                    commander.println("killall gpsd");

                }

            }
        });
        kismet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    new AsyncTask<Integer, Void, Void>() {
                        @Override
                        protected Void doInBackground(Integer... params) {
                            try {

                            commander.println("kismet_server &");
                            } catch (Exception e) {
                                e.printStackTrace();

                            }
                            return null;
                        }
                    }.execute(1);
                } else {
                    commander.println("killall kismet_server");
                }

            }
        });
        return v;
    }
    public String executeRemoteCommand(String username, String password, String hostname, int port)
            throws Exception {
        JSch jsch = new JSch();
        session = jsch.getSession(username, hostname, port);
        session.setPassword(password);

        // Avoid asking for key confirmation
        Properties prop = new Properties();
        prop.put("StrictHostKeyChecking", "no");
        session.setConfig(prop);

        session.connect();

        // SSH Channel
        ChannelShell channelssh = (ChannelShell)
                session.openChannel("shell");
        OutputStream inputstream_for_the_channel=channelssh.getOutputStream();
        commander = new PrintStream(inputstream_for_the_channel, true);
        channelssh.setOutputStream(baos);
        //channelssh.setOutputStream(System.out, true);
        channelssh.connect();

        //commander.close();
        //session.disconnect();

        return "Connection Enstablished!";
    }

    public static ControlPanel newInstance(String text) {
        ControlPanel f = new ControlPanel();
        Bundle b = new Bundle();
        b.putString("msg", text);
        f.setArguments(b);

        return f;
    }
}
