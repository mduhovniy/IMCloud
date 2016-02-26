package info.duhovniy.maxim.imcloud.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import info.duhovniy.maxim.imcloud.R;
import info.duhovniy.maxim.imcloud.data.Message;
import info.duhovniy.maxim.imcloud.network.SendService;

/**
 * Chat Fragment
 */
public class ChatFragment extends Fragment {

    private static ChatFragment instance = new ChatFragment();

    public static ChatFragment getInstance() {
        return instance;
    }

    //private RecyclerView recyclerView;
    private EditText to, msg;
    private String recipient = null;
    private View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_chat, container, false);

/*
        recyclerView = (RecyclerView) rootView.findViewById(R.id.chat_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
*/
        FloatingActionButton composeMessageButton = (FloatingActionButton) rootView.findViewById(R.id.new_chat_fab);

        to = (EditText) rootView.findViewById(R.id.input_recipient);
        msg = (EditText) rootView.findViewById(R.id.input_message);

        composeMessageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message m = new Message("", to.getText().toString(), msg.getText().toString());
                Intent intent = new Intent(getActivity(), SendService.class);
                intent.putExtra("Message", m);

                getContext().startService(intent);
                Snackbar.make(view, "Sending new message...", Snackbar.LENGTH_LONG)
                        .setAction("Ok", null).show();
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (to != null && recipient != null)
            to.setText(recipient);
    }

    public void setRecipient(String email) {
        recipient = email;
    }

}
