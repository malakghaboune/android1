package application;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.ass1.R;

import java.util.ArrayList;
import java.util.List;

public class TaskAdapter extends ArrayAdapter<Task> {
    public TaskAdapter(Context context, List<Task> tasks) {
        super(context, 0, tasks);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.task_item, parent, false);
        }

        Task task = getItem(position);

        TextView textViewTitle = convertView.findViewById(R.id.textViewTitle);
        TextView textViewDescription = convertView.findViewById(R.id.textViewDescription);
        TextView textViewDueDate = convertView.findViewById(R.id.textViewDueDate);
        CheckBox checkBoxDone = convertView.findViewById(R.id.checkBoxDone);

        textViewTitle.setText(task.getTitle());
        textViewDescription.setText(task.getDescription());
        textViewDueDate.setText(task.getDueDate());

        checkBoxDone.setOnCheckedChangeListener(null);
        checkBoxDone.setChecked(task.isDone());

        checkBoxDone.setOnCheckedChangeListener((buttonView, isChecked) -> {
            task.setDone(isChecked);
            SharedPreferencesUtil.saveTasks(getContext(), getTasks());
        });

        convertView.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), TaskDetailActivity.class);
            intent.putExtra("TASK_ID", task.getId());
            getContext().startActivity(intent);
        });

        return convertView;
    }

    private List<Task> getTasks() {
        List<Task> currentTasks = new ArrayList<>();
        for (int i = 0; i < getCount(); i++) {
            currentTasks.add(getItem(i));
        }
        return currentTasks;
    }
}

