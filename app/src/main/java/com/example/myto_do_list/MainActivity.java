package com.example.myto_do_list;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.myto_do_list.entidade.Task;
import com.example.myto_do_list.adapters.TaskAdapter;
import com.example.myto_do_list.dao.TaskDao;
import com.example.myto_do_list.database.AppDatabase;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText editTitle, editDescription;
    private RecyclerView recyclerView;
    private TaskAdapter adapter;
    private TaskDao taskDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializa Room Database
        AppDatabase db = Room.databaseBuilder(
                getApplicationContext(),
                AppDatabase.class,
                "task_database"
        ).allowMainThreadQueries().build();
        taskDao = db.taskDao();

        // Vincula os componentes do layout
        editTitle = findViewById(R.id.editTitle);
        editDescription = findViewById(R.id.editDescription);
        Button buttonAdd = findViewById(R.id.buttonAddTask);
        recyclerView = findViewById(R.id.recyclerViewTasks);

        // Configura o RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TaskAdapter(taskDao.getAllTasks(), taskDao);
        recyclerView.setAdapter(adapter);

        // Ação do botão "Adicionar Tarefa"
        buttonAdd.setOnClickListener(v -> {
            String title = editTitle.getText().toString().trim();
            String description = editDescription.getText().toString().trim();

            if (!title.isEmpty()) {
                Task newTask = new Task(title, description);
                taskDao.insert(newTask);
                adapter.updateTasks(taskDao.getAllTasks()); // Atualiza a lista
                editTitle.setText("");
                editDescription.setText("");
                Toast.makeText(this, "Tarefa adicionada!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Digite um título!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}