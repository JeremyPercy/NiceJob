package nl.jeremypercy.nicejob.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import nl.jeremypercy.nicejob.database.entities.User;

@Dao
public interface UserDao {

    @Insert
    void insert(User user);

    @Query("SELECT * FROM user_table LIMIT 1")
    User getUser();

    @Query("DELETE FROM user_table")
    void delete();

    @Query("UPDATE user_table SET profile_photo = :profilePhoto WHERE id = :id")
    void updatePhoto(String profilePhoto, int id);

}
