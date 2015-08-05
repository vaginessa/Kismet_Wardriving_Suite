package com.example.mark.kismetwardrivingsuite;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by mark on 01/08/15.
 */
public class Output extends Fragment {
    static TextView mTextView;

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.output, container, false);
        mTextView = (TextView) view.findViewById(R.id.textView);
        mTextView.setMovementMethod(new ScrollingMovementMethod());
        Button outputup = (Button) view.findViewById(R.id.button3);
        outputup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String baostripped=ControlPanel.baos.toString();
                if (baostripped.length()>=40000)
                baostripped= ControlPanel.baos.toString().substring(ControlPanel.baos.toString().length()-40000,ControlPanel.baos.toString().length());
                mTextView.setText(baostripped);
            }
        });
       return view;

    }

    public static Output newInstance(String text) {

        Output f = new Output();
        Bundle b = new Bundle();
        b.putString("msg", text);

        f.setArguments(b);

        return f;
    }

}