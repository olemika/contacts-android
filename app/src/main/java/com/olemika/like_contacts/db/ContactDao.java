package com.olemika.like_contacts.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ContactDao {

    @Query("SELECT * FROM contact ORDER BY contactName")
    List<Contact> getAll();

    @Query("SELECT * FROM contact WHERE starred=1")
    List<Contact> getStarred();

    @Query("SELECT * FROM contact WHERE id=:id")
    List<Contact> getById(long id);

    @Query("UPDATE contact SET starred=:starred WHERE id=:id")
    void setStar(Boolean starred, long id);

    @Update
    void update(Contact contact);

    @Insert
    void insert(Contact contact);

    @Query("DELETE FROM contact")
    public void clearTable();

}
