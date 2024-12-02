package com.plantze.app;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;



public class annualConsumption extends AppCompatActivity {

    private Button[] answers = new Button[6]; //buttons for answers
    private Button Next; //Next button
    private TextView question; //Text view to display question
    public int CurrentQuestion = 0; //Keeps track of current question
    public AnnualConsumptionModel Data = new AnnualConsumptionModel(); //Model Containing Questions
    public UserData User_Data = new UserData(); // Class for containing User data and computing Emissions

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_annual_consumption);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            User_Data = (UserData) getIntent().getSerializableExtra("Data"); //Obtaining data

            // Initilising the questions and answers to first question
            answers[0] = (android.widget.Button) findViewById(R.id.choice_a);
            answers[0].setText(Data.answerChoices[0][0]);

            answers[1] = (android.widget.Button) findViewById(R.id.choice_b);
            answers[1].setText(Data.answerChoices[0][1]);

            answers[2] = (android.widget.Button) findViewById(R.id.choice_c);
            answers[2].setVisibility(View.INVISIBLE);

            answers[3] = (android.widget.Button) findViewById(R.id.choice_d);
            answers[3].setVisibility(View.INVISIBLE);

            answers[4] = (android.widget.Button) findViewById(R.id.choice_e);
            answers[4].setVisibility(View.INVISIBLE);

            answers[5] = (android.widget.Button) findViewById(R.id.choice_f);
            answers[5].setVisibility(View.INVISIBLE);

            question = (android.widget.TextView) findViewById(R.id.question);
            question.setText(Data.getQuestion(0));

            //Setting On click listner for all the buttons combined
            answers[0].setOnClickListener((View.OnClickListener) this);
            answers[1].setOnClickListener((View.OnClickListener) this);
            answers[2].setOnClickListener((View.OnClickListener) this);
            answers[3].setOnClickListener((View.OnClickListener) this);
            answers[4].setOnClickListener((View.OnClickListener) this);
            answers[5].setOnClickListener((View.OnClickListener) this);
            Next = (android.widget.Button) findViewById(R.id.next);
            Next.setOnClickListener((View.OnClickListener) this);

            return insets;
        });
    }


    // When a button is clicked this function will be called
    @Override
    public void onClick(View v) {
        answers[0].setBackgroundColor(Color.GRAY);
        answers[1].setBackgroundColor(Color.GRAY);
        answers[2].setBackgroundColor(Color.GRAY);
        answers[3].setBackgroundColor(Color.GRAY);
        answers[4].setBackgroundColor(Color.GRAY);
        answers[5].setBackgroundColor(Color.GRAY);
        Button ClickedButton = (Button) v;
        if (!(ClickedButton.getId() == R.id.next)) {
            // If the button is not next update the answer
            ClickedButton.setBackgroundColor(Color.GREEN);
            if (ClickedButton.getId() == R.id.choice_a) {
                User_Data.UserAnswers[CurrentQuestion] = 1;
            }
            if (ClickedButton.getId() == R.id.choice_b){
                User_Data.UserAnswers[CurrentQuestion] = 2;
            }
            if (ClickedButton.getId() == R.id.choice_c){
                User_Data.UserAnswers[CurrentQuestion] = 3;
            }
            if (ClickedButton.getId() == R.id.choice_d) {
                User_Data.UserAnswers[CurrentQuestion] = 4;
            }
            if (ClickedButton.getId() == R.id.choice_e){
                if(CurrentQuestion == 1)
                    User_Data.UserAnswers[CurrentQuestion] = 2;
                else if(CurrentQuestion == 13)
                    User_Data.UserAnswers[CurrentQuestion] = 3;
                else
                    User_Data.UserAnswers[CurrentQuestion] = 5;

            }
            if (ClickedButton.getId() == R.id.choice_f) {
                if(CurrentQuestion == 16 || CurrentQuestion == 18)
                    User_Data.UserAnswers[CurrentQuestion] = 1;
                else
                    User_Data.UserAnswers[CurrentQuestion] = 6;
            }
        }
        //if the button is next update the question based on the flow requirments or call the next activity
        if (ClickedButton.getId() == R.id.next) {
            //if it is the last question call the next activity
            if (CurrentQuestion == 23) {
                Intent intent = new Intent(annualConsumption.this, Global_emission_result.class);
                intent.putExtra("Data", User_Data);
                startActivity(intent);
            }
            //Update the question based on the flow
            else if(CurrentQuestion == 0 && User_Data.UserAnswers[0]==2)
            {CurrentQuestion = CurrentQuestion+3;}
            else if (CurrentQuestion == 7 && (User_Data.UserAnswers[7] == 1 || User_Data.UserAnswers[7] ==2 || User_Data.UserAnswers[7] == 3))
            {CurrentQuestion = CurrentQuestion+5;}

            else {
                CurrentQuestion++;
            }
            answers[0].setVisibility(View.INVISIBLE);
            answers[1].setVisibility(View.INVISIBLE);
            answers[2].setVisibility(View.INVISIBLE);
            answers[3].setVisibility(View.INVISIBLE);
            answers[4].setVisibility(View.INVISIBLE);
            answers[5].setVisibility(View.INVISIBLE);
            question.setText(Data.getQuestion(CurrentQuestion));
            if (1 <= Data.getAnswerChoices(CurrentQuestion).length) {
                answers[0].setText(Data.getAnswerChoices(CurrentQuestion)[0]);
                answers[0].setVisibility(View.VISIBLE);
            }
            if (2 <= Data.getAnswerChoices(CurrentQuestion).length) {
                answers[1].setText(Data.getAnswerChoices(CurrentQuestion)[1]);
                answers[1].setVisibility(View.VISIBLE);
            }
            if (3 <= Data.getAnswerChoices(CurrentQuestion).length) {
                answers[2].setText(Data.getAnswerChoices(CurrentQuestion)[2]);
                answers[2].setVisibility(View.VISIBLE);
            }
            if (4 <= Data.getAnswerChoices(CurrentQuestion).length) {
                answers[3].setText(Data.getAnswerChoices(CurrentQuestion)[3]);
                answers[3].setVisibility(View.VISIBLE);
            }
            if (5 <= Data.getAnswerChoices(CurrentQuestion).length) {
                answers[4].setText(Data.getAnswerChoices(CurrentQuestion)[4]);
                answers[4].setVisibility(View.VISIBLE);
            }
            if (6 <= Data.getAnswerChoices(CurrentQuestion).length) {
                answers[5].setText(Data.getAnswerChoices(CurrentQuestion)[5]);
                answers[5].setVisibility(View.VISIBLE);
            }

        }
    }

}
