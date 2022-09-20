package com.example.gymapplication;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class Utils {
    private static final String ALL_TRAININGS_KEY = "ALL_TRAININGS_KEY";
    private static final String ALL_PLANS_KEY = "ALL_PLANS_KEY";
    private final SharedPreferences sharedPreferences;

    private static Utils instance;

    private Utils(Context context) {
        sharedPreferences = context.getSharedPreferences("my_DB", Context.MODE_PRIVATE);

        if (null == getTrainings()) {
            initData();
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();

        if (null == getPlans()) {
            editor.putString(ALL_PLANS_KEY, gson.toJson(new ArrayList<Plan>()));
            editor.apply();
        }
    }

    public static synchronized Utils getInstance(Context context) {
        if (null == instance) {
            instance = new Utils(context);
        }
        return instance;
    }

    private void initData() {
        ArrayList<Training> initTrainings = new ArrayList<>();
        initTrainings.add(new Training(1,
                "Push Up",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                "Pretium fusce id velit ut tortor pretium viverra suspendisse potenti." +
                        " Sit amet consectetur adipiscing elit duis tristique sollicitudin nibh. " +
                        "Nulla aliquet enim tortor at auctor urna nunc id cursus. Fringilla est ullamcorper eget nulla facilisi etiam dignissim diam.",
                "https://gymhomies.com/wp-content/uploads/2021/10/loi-ich-cua-bai-tap-chong-day-pushup-voi-co-the.jpg"));

        initTrainings.add(new Training(2,
                "Yoga",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit.",
                "Pretium fusce id velit ut tortor pretium viverra suspendisse potenti." +
                        " Sit amet consectetur adipiscing elit duis tristique sollicitudin nibh. " +
                        "Nulla aliquet enim tortor at auctor urna nunc id cursus. Fringilla est ullamcorper eget nulla facilisi etiam dignissim diam.",
                "https://leep.imgix.net/2021/06/tap-yoga-tai-nha-nhu-the-nao-de-co-hieu-qua.JPG?auto=compress&fm=pjpg&ixlib=php-1.2.1"));

        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        editor.putString(ALL_TRAININGS_KEY, gson.toJson(initTrainings));
        editor.apply();

    }

    public ArrayList<Training> getTrainings() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Training>>() {
        }.getType();
        return gson.fromJson(sharedPreferences.getString(ALL_TRAININGS_KEY, null), type);
    }

    public ArrayList<Plan> getPlans() {
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Plan>>() {
        }.getType();
        return gson.fromJson(sharedPreferences.getString(ALL_PLANS_KEY, null), type);

    }

    public ArrayList<Plan> getPlansByDay(String day) {
        ArrayList<Plan> plans = getPlans();
        if (plans != null){
            ArrayList<Plan> plansByDay = new ArrayList<>();
            for (Plan p : plans) {
                if (p.getDay().equals(day)) {
                    plansByDay.add(p);
                }
            }
            return plansByDay;
        }
        return null;
    }

    public void updatePlan(ArrayList<Plan> plans){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        editor.remove(ALL_PLANS_KEY);
        editor.putString(ALL_PLANS_KEY, gson.toJson(plans));
        editor.apply();
    }

    public boolean addPlan(Plan plan) {
        ArrayList<Plan> plans = getPlans();
        if (null != plans){
            if (plans.add(plan)) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gson = new Gson();
                editor.remove(ALL_PLANS_KEY);
                editor.putString(ALL_PLANS_KEY, gson.toJson(plans));
                editor.apply();
                return true;
            }
        }
        return false;
    }

    public boolean removePlan(Plan plan) {
        ArrayList<Plan> plans = getPlans();
        if (null != plans){
            for (Plan p : plans){
                if (p.getTraining().getId()==plan.getTraining().getId()&&p.getMinutes()==plan.getMinutes()){
                    if (plans.remove(p)) {
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        Gson gson = new Gson();
                        editor.remove(ALL_PLANS_KEY);
                        editor.putString(ALL_PLANS_KEY, gson.toJson(plans));
                        editor.apply();
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
