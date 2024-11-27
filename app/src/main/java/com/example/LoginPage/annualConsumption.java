package com.example.LoginPage;

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

import com.example.b07demosummer2024.R;

public class annualConsumption extends AppCompatActivity implements View.OnClickListener{

    private Button[] answers = new Button[6];
    private Button Next;
    private TextView question;
    public int CurrentQuestion = 0;
    public AnnualConsumptionModel Data = new AnnualConsumptionModel();
    public UserData User_Data = new UserData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_annual_consumption);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);

            User_Data = (UserData) getIntent().getSerializableExtra("Data"); //Obtaining data


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
        if (ClickedButton.getId() == R.id.next) {
            // Toast.makeText(this,User_Data.UserAnswers[CurrentQuestion],Toast.LENGTH_LONG).show();
            if (CurrentQuestion == 23) {
                Intent intent = new Intent(annualConsumption.this, Global_emission_result.class);
                intent.putExtra("Data", User_Data);
                startActivity(intent);
            }
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
