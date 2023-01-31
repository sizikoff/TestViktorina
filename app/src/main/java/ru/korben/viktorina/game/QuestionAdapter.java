package ru.korben.viktorina.game;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ru.korben.viktorina.R;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {

    private String checkedAnswer;
    private final LayoutInflater layoutInflater;
    private final List<Question> questions;
    private ViewHolder holder;
    private Question question;
    private int trueAnswers = 0;

    private final Activity mActivity;

    public QuestionAdapter(Context context, List<Question> questions) {
        this.questions = questions;
        this.layoutInflater = LayoutInflater.from(context);
        mActivity = (Activity) context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        this.holder = holder;
        question = questions.get(0);
        holder.btn1.setEnabled(true);
        checkedAnswer = "";
        setData();
        final RadioGroup.OnCheckedChangeListener onCheckedChangeListener = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = (RadioButton) group.findViewById(checkedId);
                checkedAnswer = radioButton.getText().toString();
            }
        };
        holder.radioGroup.setOnCheckedChangeListener(onCheckedChangeListener);



        holder.btnNextQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkedAnswer.equals("")) {
                    Toast.makeText(mActivity.getApplicationContext(), R.string.check, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (checkedAnswer.equals(question.getTrueAnswer())) {
                    trueAnswers = + ++trueAnswers;
                    holder.btn1.setBackgroundColor(Color.GREEN);
                } else {
                    holder.btn1.setBackgroundColor(Color.RED);
                }
                clearCheck();
                question = questions.get(1);
                holder.btn2.setEnabled(true);
                checkedAnswer = "";
                setData();

                holder.btnNextQ.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (checkedAnswer.equals("")) {
                            Toast.makeText(mActivity.getApplicationContext(), R.string.check, Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(checkedAnswer.equals(question.getTrueAnswer())) {
                            trueAnswers = + ++trueAnswers;
                            holder.btn2.setBackgroundColor(Color.GREEN);
                        } else {
                            holder.btn2.setBackgroundColor(Color.RED);
                        }
                        clearCheck();
                        question = questions.get(2);
                        holder.btn3.setEnabled(true);
                        checkedAnswer = "";
                        setData();

                        holder.btnNextQ.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (checkedAnswer.equals("")) {
                                    Toast.makeText(mActivity.getApplicationContext(), R.string.check, Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                if(checkedAnswer.equals(question.getTrueAnswer())) {
                                    trueAnswers = + ++trueAnswers;
                                    holder.btn3.setBackgroundColor(Color.GREEN);
                                } else {
                                    holder.btn3.setBackgroundColor(Color.RED);
                                }
                                clearCheck();
                                question = questions.get(3);
                                holder.btn4.setEnabled(true);
                                checkedAnswer = "";
                                setData();

                                holder.btnNextQ.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (checkedAnswer.equals("")) {
                                            Toast.makeText(mActivity.getApplicationContext(), R.string.check, Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                        if(checkedAnswer.equals(question.getTrueAnswer())) {
                                            trueAnswers = + ++trueAnswers;
                                            holder.btn4.setBackgroundColor(Color.GREEN);
                                        } else {
                                            holder.btn4.setBackgroundColor(Color.RED);
                                        }
                                        clearCheck();
                                        question = questions.get(4);
                                        holder.btn5.setEnabled(true);
                                        checkedAnswer = "";
                                        setData();

                                        holder.btnNextQ.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                if (checkedAnswer.equals("")) {
                                                    Toast.makeText(mActivity.getApplicationContext(), R.string.check, Toast.LENGTH_SHORT).show();
                                                    return;
                                                }
                                                if(checkedAnswer.equals(question.getTrueAnswer())) {
                                                    trueAnswers = + ++trueAnswers;
                                                    holder.btn5.setBackgroundColor(Color.GREEN);
                                                } else {
                                                    holder.btn5.setBackgroundColor(Color.RED);
                                                }
                                                clearCheck();
                                                question = questions.get(5);
                                                holder.btn6.setEnabled(true);
                                                checkedAnswer = "";
                                                setData();

                                                holder.btnNextQ.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        if (checkedAnswer.equals("")) {
                                                            Toast.makeText(mActivity.getApplicationContext(), R.string.check, Toast.LENGTH_SHORT).show();
                                                            return;
                                                        }
                                                        if(checkedAnswer.equals(question.getTrueAnswer())) {
                                                            trueAnswers = + ++trueAnswers;
                                                            holder.btn6.setBackgroundColor(Color.GREEN);
                                                        } else {
                                                            holder.btn6.setBackgroundColor(Color.RED);
                                                        }
                                                        clearCheck();
                                                        question = questions.get(6);
                                                        holder.btn7.setEnabled(true);
                                                        holder.btnNextQ.setText(R.string.finish);
                                                        checkedAnswer = "";
                                                        setData();



                                                holder.btnNextQ.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        if (checkedAnswer.equals("")) {
                                                            Toast.makeText(mActivity.getApplicationContext(), R.string.check, Toast.LENGTH_SHORT).show();
                                                            return;
                                                        }
                                                        if(checkedAnswer.equals(question.getTrueAnswer())) {
                                                            trueAnswers = + ++trueAnswers;
                                                            holder.btn7.setBackgroundColor(Color.GREEN);
                                                        } else {
                                                            holder.btn7.setBackgroundColor(Color.RED);
                                                        }
                                                        Intent intent = new Intent();
                                                        intent.putExtra("trueAnswers", trueAnswers);
                                                        mActivity.setResult(Activity.RESULT_OK, intent);
                                                        mActivity.finish();
                                                    }
                                                });
                                            }
                                        });
                                    }
                                });
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
    }
    private void setData() {
        holder.questionText.setText(question.getQuestionText());
        holder.answer1.setText(question.getAnswer1());
        holder.answer2.setText(question.getAnswer2());
        holder.answer3.setText(question.getAnswer3());
        holder.answer4.setText(question.getAnswer4());
    }
    private void clearCheck() {
        holder.answer1.setChecked(false);
        holder.answer2.setChecked(false);
        holder.answer3.setChecked(false);
        holder.answer4.setChecked(false);
    }

    @Override
    public int getItemCount() {
        return questions.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        final TextView questionText, trueAnswer;
        final RadioButton answer1, answer2, answer3, answer4;
        final Button btnNextQ, btn1, btn2, btn3, btn4, btn5, btn6,btn7;
        final RadioGroup radioGroup;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            btn1 = (Button) itemView.findViewById(R.id.btn1);
            btn2 = (Button) itemView.findViewById(R.id.btn2);
            btn3 = (Button) itemView.findViewById(R.id.btn3);
            btn4 = (Button) itemView.findViewById(R.id.btn4);
            btn5 = (Button) itemView.findViewById(R.id.btn5);
            btn6 = (Button) itemView.findViewById(R.id.btn6);
            btn7 = (Button) itemView.findViewById(R.id.btn7);


            questionText = (TextView) itemView.findViewById(R.id.questionText);
            answer1 = (RadioButton) itemView.findViewById(R.id.answer1);
            answer2 = (RadioButton) itemView.findViewById(R.id.answer2);
            answer3 = (RadioButton) itemView.findViewById(R.id.answer3);
            answer4 = (RadioButton) itemView.findViewById(R.id.answer4);

            trueAnswer = (TextView) itemView.findViewById(R.id.trueAnswer);

            radioGroup = (RadioGroup) itemView.findViewById(R.id.radioGroup);
            btnNextQ = (Button) itemView.findViewById(R.id.btnNextQ);
        }
    }
}


