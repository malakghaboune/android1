package application;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class SharedPreferencesUtil {
    private static final String PREF_FILE = "com.example.taskmanagerapp.TASKS_PREF";
    private static final String TASKS_KEY = "tasks";

    public static void saveTasks(Context context, List<Task> tasks) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(tasks);
        editor.putString(TASKS_KEY, json);
        editor.apply();
    }

    public static List<Task> loadTasks(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREF_FILE, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString(TASKS_KEY, null);
        Type type = new TypeToken<ArrayList<Task>>() {}.getType();
        List<Task> tasks = gson.fromJson(json, type);
        return tasks == null ? new ArrayList<>() : tasks;
    }
}
