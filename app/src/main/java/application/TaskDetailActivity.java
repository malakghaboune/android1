package application;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ass1.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

public class TaskDetailActivity extends AppCompatActivity {

    private EditText editTextTaskTitle, editTextTaskDescription, editTextTaskDueDate;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

    private Button buttonSaveTask;
    private boolean isEditMode = false;
    private Task currentTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        editTextTaskTitle = findViewById(R.id.editTextTaskTitle);
        editTextTaskDescription = findViewById(R.id.editTextTaskDescription);
        editTextTaskDueDate = findViewById(R.id.editTextTaskDueDate);
        editTextTaskDueDate.setOnClickListener(v -> showDatePicker());
        buttonSaveTask = findViewById(R.id.buttonSaveTask);

        Intent intent = getIntent();
        if (intent.hasExtra("TASK_ID")) {
            String taskId = intent.getStringExtra("TASK_ID");
            currentTask = findTaskById(taskId);
            isEditMode = true;
            populateFields(currentTask);
        }

        buttonSaveTask.setOnClickListener(view -> saveTask());
    }
    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            editTextTaskDueDate.setText(dateFormat.format(calendar.getTime()));
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
    }
    private void populateFields(Task task) {
        if (task != null) {
            editTextTaskTitle.setText(task.getTitle());
            editTextTaskDescription.setText(task.getDescription());
            editTextTaskDueDate.setText(task.getDueDate());
        }
    }

    private Task findTaskById(String taskId) {
        List<Task> tasks = SharedPreferencesUtil.loadTasks(this);
        for (Task task : tasks) {
            if (task.getId().equals(taskId)) {
                return task;
            }
        }
        return null;
    }

    private void saveTask() {
        String title = editTextTaskTitle.getText().toString();
        String description = editTextTaskDescription.getText().toString();
        String dueDate = editTextTaskDueDate.getText().toString();

        if (!isEditMode) {
            currentTask = new Task();
            currentTask.setId(UUID.randomUUID().toString());
        }

        currentTask.setTitle(title);
        currentTask.setDescription(description);
        currentTask.setDueDate(dueDate);

        List<Task> tasks = SharedPreferencesUtil.loadTasks(this);
        if (isEditMode) {
            for (int i = 0; i < tasks.size(); i++) {
                if (tasks.get(i).getId().equals(currentTask.getId())) {
                    tasks.set(i, currentTask);
                    break;
                }
            }
        } else {
            tasks.add(currentTask);
        }

        SharedPreferencesUtil.saveTasks(this, tasks);
        finish();
    }
}
