package com.spacedancer.globalandromathick.adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.spacedancer.globalandromathick.R;
import com.spacedancer.globalandromathick.components.RecyclerQuestionItem;
import com.spacedancer.globalandromathick.questions.Question;

public class QuestionResultsAdapter extends RecyclerView.Adapter<QuestionResultsAdapter.QuestionsViewHolder> {

    //this context we will use to inflate the layout
    private Context mCtx;

    //we are storing all the products in a list
    private Question[] questionsList;

    private String language;

    //getting the context and product list with constructor
    public QuestionResultsAdapter(Context mCtx, Question[] questionsList, String language) {
        this.mCtx = mCtx;
        this.questionsList = questionsList;
        this.language = language;
    }

    @Override
    public QuestionsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //inflating and returning our view holder
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.layout_question_result_items, null);
        return new QuestionsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(QuestionsViewHolder holder, int position) {

        //getting the question of the specified position and create recycler item
        Question question = questionsList[position];

        RecyclerQuestionItem rqi = new RecyclerQuestionItem(language, question);

        //binding the data with the viewholder views
        holder.questionResultsImageView.setImageDrawable(
                mCtx.getResources().getDrawable(rqi.getImageCW()));

        holder.questionNumberTextView.setText(rqi.getQuestionNumber());
        holder.questionTitleTextView.setText(rqi.getQuestionTitle());
        holder.questionExpressionTextView.setText(rqi.getExpression());

        holder.choicesTitleTextView.setText(rqi.getChoicesTitle());
        holder.choice1TextView.setText(rqi.getChoice1());
        holder.choice2TextView.setText(rqi.getChoice2());
        holder.choice3TextView.setText(rqi.getChoice3());

        holder.timeTextView.setText(rqi.getTime());
        holder.scoreTextView.setText(rqi.getScore());
        holder.answeredTitleTextView.setText(rqi.getAnsweredTitle());
        holder.choiceAnsweredTextView.setText(rqi.getChoiceAnswered());

        holder.solutionTitleTextView.setText(rqi.getSolutionTitle());
        holder.solutionLine1TextView.setText(rqi.getSolutionLine1());
        holder.solutionLine2TextView.setText(rqi.getSolutionLine2());
        holder.solutionLine3TextView.setText(rqi.getSolutionLine3());
    }

    @Override
    public int getItemCount() {
        return questionsList.length;
    }

    class QuestionsViewHolder extends RecyclerView.ViewHolder {

        TextView questionNumberTextView, questionTitleTextView, questionExpressionTextView;
        TextView choicesTitleTextView, choice1TextView, choice2TextView, choice3TextView;
        TextView timeTextView, scoreTextView, answeredTitleTextView, choiceAnsweredTextView;

        TextView solutionTitleTextView;
        TextView solutionLine1TextView, solutionLine2TextView, solutionLine3TextView;

        ImageView questionResultsImageView;

        public QuestionsViewHolder(View itemView) {
            super(itemView);

            questionNumberTextView = itemView.findViewById(R.id.questionNumberTextView);
            questionTitleTextView = itemView.findViewById(R.id.questionTitleTextView);
            questionExpressionTextView = itemView.findViewById(R.id.questionExpressionTextView);

            choicesTitleTextView = itemView.findViewById(R.id.choicesTitleTextView);
            choice1TextView = itemView.findViewById(R.id.choice1TextView);
            choice2TextView = itemView.findViewById(R.id.choice2TextView);
            choice3TextView = itemView.findViewById(R.id.choice3TextView);

            timeTextView = itemView.findViewById(R.id.timeTextView);

            scoreTextView = itemView.findViewById(R.id.scoreTextView);

            answeredTitleTextView = itemView.findViewById(R.id.answeredTitleTextView);
            choiceAnsweredTextView = itemView.findViewById(R.id.choiceAnsweredTextView);

            solutionTitleTextView = itemView.findViewById(R.id.solutionTitleTextView);
            solutionLine1TextView = itemView.findViewById(R.id.solutionLine1TextView);
            solutionLine2TextView = itemView.findViewById(R.id.solutionLine2TextView);
            solutionLine3TextView = itemView.findViewById(R.id.solutionLine3TextView);

            questionResultsImageView = itemView.findViewById(R.id.questionResultsImageView);
        }
    }
}
