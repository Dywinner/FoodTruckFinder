package com.example.foodtruckfinder;

/**
 * Created by samprescott on 4/27/18.
 */

import android.app.Application;
import android.os.AsyncTask;

import com.example.foodtruckfinder.FoodTruckFinderDatabase;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class DatabaseGenerator {

    private FoodTruckDao foodTruckDao;
    private ReviewDao reviewDao;

    private List<FoodTruckEntity> foodTruckEntityList;
    private List<ReviewEntity> reviewEntityList;

    DatabaseGenerator(Application app) {
        FoodTruckFinderDatabase db = FoodTruckFinderDatabase.getDatabase(app);
        foodTruckDao = db.getFoodTruckDao();
        reviewDao = db.getReviewDao();

//        foodTruckEntityList = foodTruckDao.getLocalFoodTrucks();
//        reviewEntityList = db.getReviewDao().getRecentReviews();

    }

    public void clearDatabase() {
        new clearDatabaseAsyncTask(foodTruckDao, reviewDao).execute();
    }

    private static class clearDatabaseAsyncTask extends AsyncTask<Void, Void, Void> {
        private FoodTruckDao foodTruckDao;
        private ReviewDao reviewDao;

        clearDatabaseAsyncTask(FoodTruckDao fDao, ReviewDao rDao) {
            foodTruckDao = fDao;
            reviewDao = rDao;
        }

        @Override
        protected Void doInBackground(final Void... params) {
            foodTruckDao.deleteAll();
            reviewDao.deleteAll();
            return null;
        }

    }

    public void insertReviewEntity(ReviewEntity reviewEntity) {
        new insertReviewAsyncTask(reviewDao).execute(reviewEntity);
    }

    public void insertFoodTruckEntity(FoodTruckEntity foodTruckEntity) {
        new insertTruckAsyncTask(foodTruckDao).execute(foodTruckEntity);
    }

    public List<ReviewEntity> getReviewEntityList() throws InterruptedException, ExecutionException, TimeoutException {
        getReviewsAsyncTask getReviewsAsyncTask = new getReviewsAsyncTask(reviewDao);
        getReviewsAsyncTask.execute();
        return getReviewsAsyncTask.get(30, TimeUnit.SECONDS);
    }

    public List<FoodTruckEntity> getFoodTruckEntityList() throws InterruptedException, ExecutionException, TimeoutException {
        getFoodTrucksAsyncTask getFoodTrucksAsyncTask = new getFoodTrucksAsyncTask(foodTruckDao);
        getFoodTrucksAsyncTask.execute();
        return getFoodTrucksAsyncTask.get(30, TimeUnit.SECONDS);
    }

    private static class getFoodTrucksAsyncTask extends AsyncTask<Void, Void, List<FoodTruckEntity>> {
        private FoodTruckDao mAsyncTaskDao;

        getFoodTrucksAsyncTask(FoodTruckDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected List<FoodTruckEntity> doInBackground(final Void... params) {
            return mAsyncTaskDao.getLocalFoodTrucks();
        }
    }

    private static class getReviewsAsyncTask extends AsyncTask<Void, Void, List<ReviewEntity>> {
        private ReviewDao mAsyncTaskDao;

        getReviewsAsyncTask(ReviewDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected List<ReviewEntity> doInBackground(final Void... params) {
            return mAsyncTaskDao.getRecentReviews();
        }

//        protected void onPostExecute(Long result) {
//            showDialog("Downloaded " + result + " bytes");
//        }

    }

    private static class insertReviewAsyncTask extends AsyncTask<ReviewEntity, Void, Void> {
        private ReviewDao mAsyncTaskDao;

        insertReviewAsyncTask(ReviewDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final ReviewEntity... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }

    }

    private static class insertTruckAsyncTask extends AsyncTask<FoodTruckEntity, Void, Void> {
        private FoodTruckDao mAsyncTaskDao;

        insertTruckAsyncTask(FoodTruckDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final FoodTruckEntity... params) {
            mAsyncTaskDao.insert(params[0]);
            System.out.println("inside dbg: " + params[0]);
            return null;
        }
    }

}
