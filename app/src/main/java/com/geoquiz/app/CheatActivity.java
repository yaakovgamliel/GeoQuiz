package com.geoquiz.app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class CheatActivity extends Activity {

    private TextView mTextViewWarning;
    private TextView mTextViewAnswer;
    private Button mButtonShowCheat;
    private boolean mAnswerITrue;

    public static  final String EXTRA_ANSWER_IS_TRUE = "com.geoquiz.app.geoquiz.answer_is_true";
    public static  final String EXTRA_ANSWER_SHOWN = "com.geoquiz.app.geoquiz.answer_shown";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheat);

        setExtraAnswerShown(false);

        mAnswerITrue = getIntent().getBooleanExtra(CheatActivity.EXTRA_ANSWER_IS_TRUE,false);


        mTextViewAnswer = (TextView)findViewById(R.id.answerTextView);
        mTextViewWarning = (TextView)findViewById(R.id.warning_text);
        mButtonShowCheat = (Button)findViewById(R.id.show_answer_button);

        mButtonShowCheat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAnswerITrue) {
                    mTextViewAnswer.setText(R.string.true_button);
                } else {
                    mTextViewAnswer.setText(R.string.false_button);
                }
                setExtraAnswerShown(true);
            }
        });
    }


    private void setExtraAnswerShown(boolean isAnswerShown) {
        Intent data  = new Intent();
        data.putExtra(EXTRA_ANSWER_SHOWN,isAnswerShown);
        setResult(RESULT_OK,data);
    }

}
