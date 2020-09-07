package android.bignerdrach.com;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private int mCurrentPoints = 0;
    private int mCurrentIndex = 0;
    private static final String KEY_INDEX = "index";
    private static final String TAG = "QuizActivity";
    private Button mTrueButton;
    private Button mFalseButton;
    private Button mNextButton;
    private Button mPrevButton;
    private TextView mQuestionTextView;
    private TextView mPoints;
    private Question[] mQuestionBank = new Question[]
    {
         new Question(R.string.question_australia, true),
         new Question(R.string.question_oceans, true),
         new Question(R.string.question_mideast, false),
         new Question(R.string.question_africa, false),
         new Question(R.string.question_americas, true),
         new Question(R.string.question_asia, true),
    };
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FrameLayout frame = (FrameLayout) findViewById(R.id.rootLayout);
        AnimationDrawable animationDrawable = (AnimationDrawable) frame.getBackground();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(4000);
        animationDrawable.start();
        if (savedInstanceState != null)
        {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
            mCurrentPoints = savedInstanceState.getInt(KEY_INDEX, 0);
        }
        mPoints = (TextView) findViewById(R.id.points);
        mPoints.setText(String.valueOf(mCurrentPoints));
        mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
        mTrueButton = (Button) findViewById((R.id.true_button));
        mTrueButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mTrueButton.setEnabled(false);
                mFalseButton.setEnabled(false);
                checkAnswer(true);
            }
        });
        mFalseButton = (Button) findViewById(R.id.false_button);
        mFalseButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mTrueButton.setEnabled(false);
                mFalseButton.setEnabled(false);
                checkAnswer(false);
            }
        });
        mNextButton = (Button) findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
                updateQuestion();
                mTrueButton.setEnabled(true);
                mFalseButton.setEnabled(true);
            }
        });
        mPrevButton = (Button) findViewById(R.id.prev_button);
        mPrevButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public  void onClick(View v)
            {
                if(mCurrentIndex!=0) {
                    mCurrentIndex = (mCurrentIndex - 1) % mQuestionBank.length;
                    updateQuestion();
                }
            }
        });
        updateQuestion();
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState)
    {
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
    }
    private void updateQuestion()
    {
        int question = mQuestionBank[mCurrentIndex].getTextResId();
        mQuestionTextView.setText(question);
    }
    private void checkAnswer(boolean userPressedTrue)
    {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isAnswerTrue();
        int messageResId = 0;
        if (userPressedTrue == answerIsTrue)
        {
            messageResId = R.string.correct_toast;
            mCurrentPoints += 1;
            mPoints.setText(String.valueOf(mCurrentPoints));
        }
        else
            {
                messageResId = R.string.incorrect_toast;
            }
    Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }
}
