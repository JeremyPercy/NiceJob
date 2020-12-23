package nl.jeremypercy.nicejob.database.repository;

import android.content.Context;
import android.os.AsyncTask;

import java.util.concurrent.ExecutionException;

import nl.jeremypercy.nicejob.database.UserDatabase;
import nl.jeremypercy.nicejob.database.dao.UserDao;
import nl.jeremypercy.nicejob.database.entities.User;

public class UserRepository {

    private UserDao userDao;
    String profilePhoto;
    Integer id;

    public UserRepository(Context context) {
        UserDatabase db = UserDatabase.getDatabase(context);
        userDao = db.UserDao();
    }
    public UserRepository(Context context, String profilePhoto, int id) {
        UserDatabase db = UserDatabase.getDatabase(context);
        userDao = db.UserDao();

    }

    public void insert(User user) {
        new insertAsyncTask(userDao).execute(user);
    }

    public User getUser() throws ExecutionException, InterruptedException {
        return new getUserAsyncTask(userDao).execute().get();
    }

    public void delete() {
        new deleteAsyncTask(userDao).execute();
    }

    public void updateProfilePhoto(String profilePhoto, int id){
        new updateAsyncTask(userDao, profilePhoto, id).execute();
    }


    private static class insertAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao asyncTaskDao;

        insertAsyncTask(UserDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final User... params) {
            asyncTaskDao.delete();
            asyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class getUserAsyncTask extends AsyncTask<Void, Void, User> {
        private UserDao asyncTaskDao;

        getUserAsyncTask(UserDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected User doInBackground(Void... voids) {
            return asyncTaskDao.getUser();
        }
    }

    private static class deleteAsyncTask extends AsyncTask<Void, Void, Void> {
        private UserDao asyncTaskDao;

        deleteAsyncTask(UserDao dao) {
            asyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            asyncTaskDao.delete();
            return null;
        }
    }

    private static class updateAsyncTask extends AsyncTask<String, Void, Void> {
        private UserDao asyncTaskDao;
        String profilePhoto;
        Integer id;

        updateAsyncTask(UserDao dao, String profilePhoto, int id) {
            asyncTaskDao = dao;
            this.profilePhoto = profilePhoto;
            this.id = id;
        }

        @Override
        protected Void doInBackground(String... strings) {
            asyncTaskDao.updatePhoto(profilePhoto, id);
            return null;
        }
    }

}
