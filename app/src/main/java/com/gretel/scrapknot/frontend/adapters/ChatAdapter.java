package com.gretel.scrapknot.frontend.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gretel.scrapknot.R;
import com.gretel.scrapknot.backend.Chat.StringText;
import com.gretel.scrapknot.backend.Chat.Text;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.gretel.scrapknot.frontend.adapters.ChatAdapter.PrevTextType.FINAL;
import static com.gretel.scrapknot.frontend.adapters.ChatAdapter.PrevTextType.INITIAL;
import static com.gretel.scrapknot.frontend.adapters.ChatAdapter.PrevTextType.NONE;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder>{

    enum PrevTextType {
        NONE,INITIAL,FINAL
    }

    private HashMap<String,String> myProfilePhotos = new HashMap<>();

    private ArrayList<Date> myDates = new ArrayList<>();
    private Context myContext;
    private String myCurrSender;
    private PrevTextType myPrevTextType;
    private LinearLayout myCurrLinearLayout;
    private TextView myPrevTextView;
    private Text myCurrentText;

    public ChatAdapter(ArrayList<ArrayList<Text>> textSets, Context context){
        myCurrSender = "";
        myPrevTextType = FINAL;
        for(ArrayList<Text> textSet: textSets){
            for(Text text: textSet){
                updateChat(text);
            }
        }
        myContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater mInflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = mInflater.inflate(R.layout.holder_chat,viewGroup,false);
        return new ChatAdapter.ViewHolder(row);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Picasso.get()
                .load(myProfilePhotos.get(myCurrSender))
                .into(viewHolder.profilePhoto);

        myCurrLinearLayout = viewHolder.linearLayout;

        if(myCurrentText instanceof StringText){
            addTextView(myCurrentText);
        }
        //viewHolder.textDate.setText(myDates.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return myDates.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        CircleImageView profilePhoto;
        //TextView textDate;
        LinearLayout linearLayout;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            linearLayout=itemView.findViewById(R.id.linear_layout_text);
            profilePhoto=itemView.findViewById(R.id.profile_photo_sender);
        }
    }

    public void updateChat(Text text) {
        if(myCurrSender.equals(text.getSender())){
            if(text instanceof StringText){
                addTextView(text);
            }
        } else {
            System.out.println("bruhhh it is making a new row");
            addTextSetToList(text);
            this.notifyDataSetChanged();
        }

    }

    private void addTextSetToList(Text newText) {
        myPrevTextType = NONE;
        myCurrentText = newText;
        String newSender = newText.getSender();
        myCurrSender = newSender;
        if(!myProfilePhotos.containsKey(newSender)){
            myProfilePhotos.put(newSender,"https://cdn.shopify.com/s/files/1/1061/1924/products/Woman_Saying_Hi_Emoji_Icon_ios10_grande.png?v=1542436004");
        }
        myDates.add(newText.getDate());
    }

    private void addTextView(Text text) {

        TextView toAdd = new TextView(myContext);
        toAdd.setText((String) text.getMessage());
        toAdd.setPadding(24,8,24,8);
        toAdd.setTextSize(16);

        switch (myPrevTextType) {
            case NONE:
                toAdd.setBackgroundResource(R.drawable.text_other);
                myPrevTextType = INITIAL;
                break;
            case INITIAL:
                myPrevTextView.setBackgroundResource(R.drawable.text_other_initial);
                toAdd.setBackgroundResource(R.drawable.text_other_last);
                myPrevTextType = FINAL;
                break;
            case FINAL:
                myPrevTextView.setBackgroundResource(R.drawable.text_other_middle);
                toAdd.setBackgroundResource(R.drawable.text_other_last);
        }

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(2,2,2,2);
        toAdd.setLayoutParams(params);
        myCurrLinearLayout.addView(toAdd);
        myPrevTextView = toAdd;
    }
}
