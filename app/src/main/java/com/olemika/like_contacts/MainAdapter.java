package com.olemika.like_contacts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.olemika.like_contacts.db.AppDatabase;
import com.olemika.like_contacts.db.Contact;
import com.olemika.like_contacts.db.ContactDao;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainViewHolder> {
    AppDatabase db = App.getInstance().getDatabase();
    public ContactDao dao = db.contactDao();

    Context mContext;
    List<Contact> contactList;

    public MainAdapter(Context mContext, List<Contact> contactList) {
        this.mContext = mContext;
        this.contactList = contactList;
    }

    public class MainViewHolder extends RecyclerView.ViewHolder {
        TextView textName;
        ImageView star;
        public MainViewHolder(View itemView) {
            super(itemView);
            this.textName = itemView.findViewById(R.id.contact_name);
            this.star = itemView.findViewById(R.id.star_item);
        }
    }

    @Override
    public MainViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
//        MainViewHolder holder = new MainViewHolder(view);
        return new MainViewHolder(view);
    }

    @Override
    public void onBindViewHolder( MainViewHolder holder, int position) {
        holder.textName.setText(contactList.get(position).getContactName());
        long id = contactList.get(position).getId();
        Boolean starred = contactList.get(position).getStarred();
        if (starred){
            holder.star.setImageResource(R.drawable.ic_star_filled);
        } else  {
            holder.star.setImageResource(R.drawable.ic_star_dark);
        }
        holder.star.setOnClickListener(v-> {
            if(starred){
                dao.setStar(false, id);
                holder.star.setImageResource(R.drawable.ic_star_dark);
            } else {
                dao.setStar(true, id);
                holder.star.setImageResource(R.drawable.ic_star_filled);
            }
        });
    }

    @Override
    public int getItemCount() {
          return contactList.size();
    }
}
