package com.Plantze.tracker;

public class MainActivity extends AppCompatActivity {
    StoredData stored_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Initialize essential components
        initializeCore();

        // 2. Setup UI
        setupUI();

        // 3. Load data
        loadData();

        // 4. Start background tasks
        startBackgroundTasks();
    }

    private void initializeCore() {
        //needs more work on stored data
        stored_data=new StoredData("123456");
        // Essential initialization
    }

    private void setupUI() {
        //we create mainaddactivityadapter first
        // UI setup
    }

    private void loadData() {
        // Data loading
    }

    private void startBackgroundTasks() {
        // Background operations
    }
}