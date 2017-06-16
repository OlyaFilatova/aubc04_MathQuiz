package com.miymayster.olya.mathquiz;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    // Contains value that is used in all math problems.
    // Must be even.
    int b;
    // Contains value that is used in all math problems.
    // Must be multiple to b.
    int a;
    // Shifts answer for task with options to wrong value.
    // Must be less or more than zero
    int shifter;
    // stores id of the option with right answer for the task with options
    private int rightAnswerOptionId;
    // storing user's input
    // stores user's answer for the addition problem
    private int answerAddition;
    // stores user's answer for the subtraction problem
    private int answerSubtraction;
    // stores user's answer for the multiplication problem
    private int answerMultiplication;
    // stores user's answer for the division problem
    private int answerDivision;
    // stores user's answer for the task with options
    private int answerOptionId;
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("a", a);
        outState.putInt("b", b);
        outState.putInt("shifter", shifter);
        outState.putInt("rightAnswerOptionId", rightAnswerOptionId);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // checking that application isn't reloading
        if (savedInstanceState == null) {
            // To spice things up I'm using Math.random
            // create b that can be >= 0 or <= 10
            b = (int) (Math.random() * 10);
            // making sure that b is even
            if (b % 2 == 1) {
                b -= 1;
            }
            // making sure that b isn't zero
            if (b == 0) {
                b = 2;
            }
            // creating value for a that is multiple to b
            a = ((int) (Math.random() * 10)) * b;
            // creating "shifter" for task with options
            shifter = ((int) (Math.random() * 10)) - 5;
            // making sure that shifter isn't equal to zero
            if (shifter == 0) {
                shifter = 9;
            }
            // setting options for task with options
            if (Math.random() > 0.5) {
                // if random is more than 0.5 we will keep righ answer in second option and
                // we will store wrong answer in first option
                rightAnswerOptionId = R.id.radio_button_option2;
            } else {
                // if random is less than 0.5 or equal to 0.5 we will keep wrong answer in second option and
                // we will store right answer in first option
                rightAnswerOptionId = R.id.radio_button_option1;
            }
        } else {
            a = savedInstanceState.getInt("a");
            b = savedInstanceState.getInt("b");
            shifter = savedInstanceState.getInt("shifter");
            rightAnswerOptionId = savedInstanceState.getInt("rightAnswerOptionId");
        }
        // setting right headers with problems' text
        ((TextView) findViewById(R.id.addition_header)).setText(getAdditionTask() + " = ?");
        ((TextView) findViewById(R.id.subtraction_header)).setText(getSubtractionTask() + " = ?");
        ((TextView) findViewById(R.id.multiplication_header)).setText(getMultiplicationTask() + " = ?");
        ((TextView) findViewById(R.id.division_header)).setText(getDivisionTask() + " = ?");
        ((TextView) findViewById(R.id.radio_header)).setText(getTaskWithOptions() + " = ?");
        // setting options for task with options
        if (rightAnswerOptionId == R.id.radio_button_option2) {
            // if random is more than 0.5 we will keep righ answer in second option and
            // we will store wrong answer in first option
            ((RadioButton) findViewById(R.id.radio_button_option2)).setText(String.valueOf(getTaskWithOptionsRightAnswer()));
            ((RadioButton) findViewById(R.id.radio_button_option1)).setText(String.valueOf(getTaskWithOptionsWrongAnswer()));
        } else {
            // if random is less than 0.5 or equal to 0.5 we will keep wrong answer in second option and
            // we will store right answer in first option
            ((RadioButton) findViewById(R.id.radio_button_option1)).setText(String.valueOf(getTaskWithOptionsRightAnswer()));
            ((RadioButton) findViewById(R.id.radio_button_option2)).setText(String.valueOf(getTaskWithOptionsWrongAnswer()));
        }
    }
    /**
     * multiply a to b
     *
     * @return answer for the multiplication task
     */
    private int multiply() {
        return a * b;
    }
    /**
     * add a to b
     *
     * @return answer for the addition task
     */
    private int add() {
        return a + b;
    }
    /**
     * subtract b from a
     *
     * @return answer for the subtraction task
     */
    private int subtract() {
        return a - b;
    }
    /**
     * divide a by b
     *
     * @return answer for the division task
     */
    private int divide() {
        return a / b;
    }
    /**
     * @return header for addition task
     */
    private String getAdditionTask() {
        return a + " + " + b;
    }
    /**
     * @return header for subtraction task
     */
    private String getSubtractionTask() {
        return a + " - " + b;
    }
    /**
     * @return header for multiplication task
     */
    private String getMultiplicationTask() {
        return a + " * " + b;
    }
    /**
     * @return header for division task
     */
    private String getDivisionTask() {
        return a + " / " + b;
    }
    /**
     * @return header for the task with options
     */
    public String getTaskWithOptions() {
        return a + " * " + b + " - " + (b / 2);
    }
    /**
     * get right answer for task with options
     *
     * @return right answer for the task with options
     */
    public int getTaskWithOptionsRightAnswer() {
        return a * b - (b / 2);
    }
    /**
     * get wrong answer for the task with options
     *
     * @return wrong answer for the task with options
     */
    public int getTaskWithOptionsWrongAnswer() {
        return a * b - (b / 2) - shifter;
    }
    /**
     * This method is called when user clicks button Check answers
     */
    public void checkAnswers(View view) {
        boolean gotErrors = false;
        ((TextView) findViewById(R.id.conclusion)).setText("");
        try {
            answerAddition = Integer.valueOf(((TextView) findViewById(R.id.addition_edit_text)).getText().toString());
        } catch (NumberFormatException ex) {
            Log.e("MainActivity", ex.getMessage());
            gotErrors = true;
            answerAddition = 0;
            Toast.makeText(this, getString(R.string.write_your_answer_for, getAdditionTask()), Toast.LENGTH_SHORT).show();
        }
        try {
            answerSubtraction = Integer.valueOf(((TextView) findViewById(R.id.subtraction_edit_text)).getText().toString());
        } catch (NumberFormatException ex) {
            Log.e("MainActivity", ex.getMessage());
            gotErrors = true;
            answerSubtraction = 0;
            Toast.makeText(this, getString(R.string.write_your_answer_for, getSubtractionTask()), Toast.LENGTH_SHORT).show();
        }
        try {
            answerMultiplication = Integer.valueOf(((TextView) findViewById(R.id.multiplication_edit_text)).getText().toString());
        } catch (NumberFormatException ex) {
            Log.e("MainActivity", ex.getMessage());
            gotErrors = true;
            answerMultiplication = 0;
            Toast.makeText(this, getString(R.string.write_your_answer_for, getMultiplicationTask()), Toast.LENGTH_SHORT).show();
        }
        try {
            answerDivision = Integer.valueOf(((TextView) findViewById(R.id.division_edit_text)).getText().toString());
        } catch (NumberFormatException ex) {
            Log.e("MainActivity", ex.getMessage());
            gotErrors = true;
            answerDivision = 0;
            Toast.makeText(this, getString(R.string.write_your_answer_for, getDivisionTask()), Toast.LENGTH_SHORT).show();
        }
        if (((RadioButton) findViewById(R.id.radio_button_option1)).isChecked()) {
            answerOptionId = R.id.radio_button_option1;
        } else if (((RadioButton) findViewById(R.id.radio_button_option2)).isChecked()) {
            answerOptionId = R.id.radio_button_option2;
        } else {
            gotErrors = true;
            Toast.makeText(this, getString(R.string.choose_your_option_for, getTaskWithOptions()), Toast.LENGTH_SHORT).show();
        }
        // if there are some unanswered questions go no further
        if (gotErrors) {
            return;
        }
        boolean checkedSendEmail = ((CheckBox) findViewById(R.id.email_result)).isChecked();
        if (checkedSendEmail) {
            sendEmail(createConclusion());
        } else {
            showResult();
        }
    }
    /**
     * This method shows result of a quiz on the screen in TextView with id conclusion
     */
    private void showResult() {
        Toast.makeText(this, createAdditionConclusion(), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, createSubtractionConclusion(), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, createMultiplicationConclusion(), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, createDivisionConclusion(), Toast.LENGTH_SHORT).show();
        Toast.makeText(this, createTaskWithOptionsConclusion(), Toast.LENGTH_SHORT).show();
        ((TextView) findViewById(R.id.conclusion)).setText(createConclusion());
    }
    /**
     * This method tries to call any app that can send email and gives it text of the email to send.
     *
     * @param body Text of the e-mail
     */
    private void sendEmail(String body) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "");
        intent.putExtra(Intent.EXTRA_TEXT, body);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
    /**
     * Creates text with conclusion to show when quiz is done.
     *
     * @return conclusion for answers for the quiz
     */
    private String createConclusion() {
        String c = "";
        c += "\n" + createAdditionConclusion();
        c += "\n" + createSubtractionConclusion();
        c += "\n" + createMultiplicationConclusion();
        c += "\n" + createDivisionConclusion();
        c += "\n" + createTaskWithOptionsConclusion();
        return c;
    }
    /**
     * Check the answer user wrote for adding two values and conclude result string
     *
     * @return String which says is answer for adding right or wrong
     */
    private String createAdditionConclusion() {
        return getAdditionTask() + " = " + answerAddition + ((answerAddition == add()) ?
                " " + getString(R.string.is_a_correct_answer) :
                " " + getString(R.string.is_a_wrong_answer));
    }
    /**
     * Check the answer user wrote for subtraction and conclude result string
     *
     * @return String which says is answer for subtraction right or wrong
     */
    private String createSubtractionConclusion() {
        return getSubtractionTask() + " = " + answerSubtraction +
                ((answerSubtraction == subtract()) ?
                " " + getString(R.string.is_a_correct_answer) :
                " " + getString(R.string.is_a_wrong_answer));
    }
    /**
     * Check the answer user wrote for multiplication problem and conclude result string
     *
     * @return String which says is answer for multiplication right or wrong
     */
    private String createMultiplicationConclusion() {
        return getMultiplicationTask() + " = " + answerMultiplication +
                ((answerMultiplication == multiply()) ?
                " " + getString(R.string.is_a_correct_answer) :
                " " + getString(R.string.is_a_wrong_answer));
    }
    /**
     * Check the answer user wrote for division problem and conclude result string
     *
     * @return String which says is answer for dividing right or wrong
     */
    private String createDivisionConclusion() {
        return getDivisionTask() + " = " + answerDivision + ((answerDivision == divide()) ?
                " " + getString(R.string.is_a_correct_answer) :
                " " + getString(R.string.is_a_wrong_answer));
    }
    /**
     * Check the answer user chose for task with options and conclude result string
     *
     * @return String which says is answer for task with options right or wrong
     */
    private String createTaskWithOptionsConclusion() {
        return getTaskWithOptions() + " = " +
                ((answerOptionId == rightAnswerOptionId) ?
                        getTaskWithOptionsRightAnswer() +
                                " " + getString(R.string.is_a_correct_answer) :
                        getTaskWithOptionsWrongAnswer() +
                                " " + getString(R.string.is_a_wrong_answer));
    }
}
