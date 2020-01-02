package id.ac.poliban.mi.nia.sqlitedemo02.dao;

import java.util.List;

import id.ac.poliban.mi.nia.sqlitedemo02.domain.Friend;

public interface FriendDao {
    void insert(Friend f);
    void update(Friend f);
    void delete(int id);
    Friend getFriendById(int id);
    List<Friend> getAllFriends();
}
