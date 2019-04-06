package com.gretel.scrapknot.frontend.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.gretel.scrapknot.R;
import com.gretel.scrapknot.backend.Chat.Chat;
import com.gretel.scrapknot.backend.Chat.StringText;
import com.gretel.scrapknot.backend.User.User;
import com.gretel.scrapknot.frontend.adapters.ChatAdapter;
import com.gretel.scrapknot.util.FirebaseManager;
import com.gretel.scrapknot.util.LocalStorage;

import java.util.ArrayList;
import java.util.Calendar;

public class ChatFragment extends Fragment {

    private static final int USER = 0;

    private Chat myChat;
    private SparseArray<String> myIDs;

    private ArrayList<TextView> myChatTextViews;
    private Button mySendButton;
    private EditText myTextMessage;
    private RecyclerView myChatRecyclerView;
    private ChatAdapter myChatAdapter;


    private FirebaseManager myFirebaseManager;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_chat,container,false);

        LocalStorage localStorage = new LocalStorage(getContext());
        User u = localStorage.loadUser();

        myIDs = new SparseArray<>();
        myIDs.put(USER,u.getID());

        myChat = new Chat(u.getID());
        //myFirebaseManager = new FirebaseManager(myChat.getChatID(),getContext());

        myChatTextViews = new ArrayList<>();

        mySendButton = view.findViewById(R.id.send_button);
        myTextMessage = view.findViewById(R.id.text_message);

        initRecyclerView(view);

        mySendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleSendButton();
            }
        });

        return view;

    }

    private void handleSendButton() {
        StringText text = new StringText(myTextMessage.getText().toString(),Calendar.getInstance().getTime(),myIDs.get(USER));
        myChat.addText(text);
        myChatAdapter.updateChat(text);

    }

    /**
     * This method initializes the Recycler View for the app
     * @param v specifies the view to which the recycler view for the list will be attached
     */
    private void initRecyclerView(View v)
    {
        //call RecyclerView
        myChatRecyclerView = v.findViewById(R.id.recycler_view_chat);
        myChatAdapter = new ChatAdapter(myChat.getChat(),getActivity().getApplicationContext());
        myChatRecyclerView.setAdapter(myChatAdapter);

        //check this
        myChatRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
    }
}
