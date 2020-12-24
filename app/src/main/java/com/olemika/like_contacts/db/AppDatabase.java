package com.olemika.like_contacts.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.olemika.like_contacts.db.Contact;
import com.olemika.like_contacts.db.ContactDao;

@Database(entities = {Contact.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ContactDao contactDao();
}
