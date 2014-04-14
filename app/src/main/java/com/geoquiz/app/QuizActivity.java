package com.geoquiz.app;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class QuizActivity extends Activity {

    private Button mFalseButton;
    private Button mTrueButton;
    private TextView mQuestionTextView;
    private Button mNextButton;
    private Button mCheatButton;
    private boolean mIsCheater;
    private static final String KEY_INDEX = "index";
    private static  final String TAG = "QuixClass";


    private TrueFalse[] mQuestionBank = new TrueFalse[]{new TrueFalse(R.string.question_oceans, true),
            new TrueFalse(R.string.question_mideast, false),
            new TrueFalse(R.string.question_africa,false),
            new TrueFalse(R.string.question_americas,true),
            new TrueFalse(R.string.question_asia,true)
    };

    private int mCurrentIndex = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mTrueButton  = (Button)findViewById(R.id.true_button);
        mFalseButton = (Button)findViewById(R.id.false_button);
        mQuestionTextView = (TextView)findViewById(R.id.question_text);
        mCheatButton = (Button)findViewById(R.id.cheat_button);

        if (savedInstanceState != null) {
            mCurrentIndex = savedInstanceState.getInt(KEY_INDEX,0);
            Log.d(TAG,"just restored the recored value");
            Log.d(TAG,"recovering ..... "+ mCurrentIndex);

        }

        showQuestion();

        mTrueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(true);
            }
        });

        mFalseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAnswer(false);
            }
        });

        mNextButton = (Button)findViewById(R.id.next_button);
        mNextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               mIsCheater = false;
               updateQuestion();
            }
        });

        mCheatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean answerIsTrue = provideCheat();
                if (answerIsTrue) {
                    Log.d(TAG, "the answer is true!");
                }
                Intent i = new Intent(QuizActivity.this,CheatActivity.class);
                i.putExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE, answerIsTrue);
                startActivityForResult(i,0);
            }
        });
    }

    private boolean provideCheat() {
        return mQuestionBank[mCurrentIndex].isTrueQuestion();
    }

    private void showQuestion() {
       mQuestionTextView.setText(mQuestionBank[mCurrentIndex].getQuestion());
    }

    private void updateQuestion() {
        mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
        int question = mQuestionBank[mCurrentIndex].getQuestion();
        mQuestionTextView.setText(question);
    }

    private  void checkAnswer(boolean userPressedTrue) {
        boolean answerIsTrue = mQuestionBank[mCurrentIndex].isTrueQuestion();

        int messageResID = 0;
        if (userPressedTrue == answerIsTrue) {
            messageResID = R.string.correct_toast;
        } else {
            messageResID = R.string.incorrect_toast;
        }

        if (mIsCheater) {
            Toast.makeText(this,R.string.judment_toast,Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this,messageResID,Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data == null) {
            return;
        }
        mIsCheater = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false);
    }

    @Override

    public void onSaveInstanceState(Bundle savedInstaceState) {
        super.onSaveInstanceState(savedInstaceState);
        Log.d(TAG, "just saved some stuff");
        savedInstaceState.putInt(KEY_INDEX, mCurrentIndex);
        Log.d(TAG,"saving ..... "+ mCurrentIndex);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.quiz, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        Log.d(TAG,"just saved some stuff");

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG,"On start!");
    }

    @Override
    public  void onPause(){
        super.onPause();
        Log.d(TAG, "On pause");
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d(TAG,"on resume");
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d(TAG,"on destroy");
    }
    /**
     * A placeholder fragment containing a simple view.
     */
//    public static class PlaceholderFragment extends Fragment {
//
//        public PlaceholderFragment() {
//        }
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                Bundle savedInstanceState) {
//            View rootView = inflater.inflate(R.layout.fragment_quiz, container, false);
//            return rootView;
//        }
//    }

}
