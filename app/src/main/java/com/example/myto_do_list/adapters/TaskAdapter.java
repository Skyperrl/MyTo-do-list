package com.example.myto_do_list.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myto_do_list.entidade.Task;
import com.example.myto_do_list.R;
import com.example.myto_do_list.dao.TaskDao;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder> {
    private List<Task> tasks;
    private final TaskDao taskDao;

    public TaskAdapter(List<Task> tasks, TaskDao taskDao) {
        this.tasks = tasks;
        this.taskDao = taskDao;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.task_item, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = tasks.get(position);
        holder.textTitle.setText(task.getTitle());
        holder.textDescription.setText(task.getDescription());

        // Ação do botão "Excluir"
        holder.buttonDelete.setOnClickListener(v -> {
            taskDao.delete(task);
            tasks.remove(position);
            notifyItemRemoved(position);
        });
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    // Método para atualizar a lista
    public void updateTasks(List<Task> newTasks) {
        tasks = newTasks;
        notifyDataSetChanged();
    }

    // ViewHolder
    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle, textDescription;
        Button buttonDelete;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textDescription = itemView.findViewById(R.id.textDescription);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }
}