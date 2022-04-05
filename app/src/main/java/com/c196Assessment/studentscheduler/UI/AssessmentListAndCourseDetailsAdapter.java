package com.c196Assessment.studentscheduler.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.c196Assessment.studentscheduler.Entities.Assessment;
import com.c196Assessment.studentscheduler.Entities.Instructor;
import com.c196Assessment.studentscheduler.R;

import java.util.List;

//This class is the adapter for the assessment list recycler view.

public class AssessmentListAndCourseDetailsAdapter extends RecyclerView.Adapter<AssessmentListAndCourseDetailsAdapter.AssessmentListViewHolder> {

    class AssessmentListViewHolder extends RecyclerView.ViewHolder{
        private final TextView assessmentTitleItemView;
        private final TextView assessmentStartDateItemView;

        //This class is the view holder for the assessment list.
        //It also listens for clicks and stores the information in
        //intents to give to the next activity.
        public AssessmentListViewHolder(@NonNull View itemView){
            super(itemView);
            assessmentTitleItemView = itemView.findViewById(R.id.assessmentTitle);
            assessmentStartDateItemView = itemView.findViewById(R.id.assessmentStartDate);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Assessment current = mAssessment.get(position);
                    Intent intent = new Intent(context, AssessmentDetails.class); //Goes to assessment details after on click
                    //Attributes get stored so when going to next activity, the selected items
                    //attributes are showing.
                    intent.putExtra("assessmentId", current.getAssessmentId());
                    intent.putExtra("assessmentTitle", current.getAssessmentTitle());
                    intent.putExtra("assessmentStartDate", current.getAssessmentStartDate());
                    intent.putExtra("assessmentEndDate", current.getAssessmentEndDate());
                    intent.putExtra("assessmentType", current.getAssessmentType());
                    context.startActivity(intent);
                }
            });
        }
    }

    private List<Assessment> mAssessment;
    private Context context;
    private final LayoutInflater mInflater;

    //Constructor method for adapter
    public AssessmentListAndCourseDetailsAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    //Creation of the view holder is made with the list item .xml and returned
    @NonNull
    @Override
    public AssessmentListAndCourseDetailsAdapter.AssessmentListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.assessment_list_item, parent, false);
        return new AssessmentListViewHolder(itemView);
    }

    //This class sets the text for the recycler view list that will be seen by the user
    @Override
    public void onBindViewHolder(@NonNull AssessmentListViewHolder holder, int position) {
        if(mAssessment != null){
            final Assessment current = mAssessment.get(position);
            holder.assessmentTitleItemView.setText(current.getAssessmentTitle());
            holder.assessmentStartDateItemView.setText(current.getAssessmentStartDate());
        }
        else{
            holder.assessmentTitleItemView.setText("No assessment title");
            holder.assessmentStartDateItemView.setText("No assessment start date");
        }
    }

    //Sets the assessment list to be used.
    public void setAssessment(List<Assessment> assessments){
            mAssessment = assessments;
            notifyDataSetChanged();
    }

    //Gets the total item count of the passed in list.
    @Override
    public int getItemCount() {
        if(mAssessment != null){
            return mAssessment.size();
        }
        else{
            return 0;
        }

    }
}
