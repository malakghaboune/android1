package application;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ass1.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listViewTasks;
    private List<Task> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listViewTasks = findViewById(R.id.listViewTasks);
        FloatingActionButton fabAddTask = findViewById(R.id.fabAddTask);

        tasks = SharedPreferencesUtil.loadTasks(this);
        TaskAdapter adapter = new TaskAdapter(this, tasks);
        listViewTasks.setAdapter(adapter);

        fabAddTask.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, TaskDetailActivity.class);
            startActivity(intent);
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateTasksList();
    }

    private void updateTasksList() {
        tasks.clear();
        tasks.addAll(SharedPreferencesUtil.loadTasks(this));
        ((TaskAdapter) listViewTasks.getAdapter()).notifyDataSetChanged();
    }
}
