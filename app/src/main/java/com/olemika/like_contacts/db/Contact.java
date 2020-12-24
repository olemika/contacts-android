package com.olemika.like_contacts.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity
public class Contact {
    @PrimaryKey
    public long id;
    public String contactName;
    public Boolean starred;

    public Contact(long id, String contactName) {
        this.id = id;
        this.contactName = contactName;
        this.starred = false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getContactName() {
        return contactName;
    }

    public void setContactName(String contactName) {
        this.contactName = contactName;
    }

    public Boolean getStarred() {
        return starred;
    }

    public void setStarred(Boolean starred) {
        this.starred = starred;
    }
}
