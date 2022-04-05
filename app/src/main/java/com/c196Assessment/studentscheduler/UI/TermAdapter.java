package com.c196Assessment.studentscheduler.UI;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.c196Assessment.studentscheduler.Entities.Term;
import com.c196Assessment.studentscheduler.R;

import java.util.List;

//This class is the adapter for the term list recycler view.

public class TermAdapter extends RecyclerView.Adapter<TermAdapter.TermViewHolder> {
    class TermViewHolder extends RecyclerView.ViewHolder{
        private final TextView termItemIdView;
        private final TextView termItemTitleView;
        private final TextView termStartDateView;
        private final TextView termEndDateView;


        //This class is the view holder for the term list.
        //It also listens for clicks and stores the information in
        //intents to give to the next activity.
        private TermViewHolder(View itemView){
            super(itemView);
            termItemIdView = itemView.findViewById(R.id.termId);
            termItemTitleView = itemView.findViewById(R.id.termTitle);
            termStartDateView = itemView.findViewById(R.id.termStartDate);
            termEndDateView = itemView.findViewById(R.id.termEndDate);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Term current = mTerm.get(position);
                    Intent intent = new Intent(context, CourseListAndTermDetails.class);
                    intent.putExtra("termId", current.getTermId());
                    intent.putExtra("termTitle", current.getTermTitle());
                    intent.putExtra("termStarDate", current.getTermStartDate());
                    intent.putExtra("termEndDate", current.getTermEndDate());
                    context.startActivity(intent);
                }
            });
        }
    }
    private List<Term> mTerm;
    private final Context context;
    private final LayoutInflater mInflater;

    //Constructor method for adapter
    public TermAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    //Creation of the view holder is made with the list item .xml and returned
    @NonNull
    @Override
    public TermAdapter.TermViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView = mInflater.inflate(R.layout.term_list_item, parent, false);
        return new TermViewHolder(itemView);
    }

    //This class sets the text for the recycler view list that will be seen by the user
    @Override
    public void onBindViewHolder(@NonNull TermViewHolder holder, int position) {
        if(mTerm != null){
            final Term current = mTerm.get(position);
            holder.termItemIdView.setText(Integer.toString(current.getTermId()));
            holder.termItemTitleView.setText(current.getTermTitle());
            holder.termStartDateView.setText(current.getTermStartDate());
            holder.termEndDateView.setText(current.getTermEndDate());
        }
        else{
            holder.termItemIdView.setText("No Term id");
            holder.termItemTitleView.setText("No term name");
            holder.termStartDateView.setText("No term start date");
            holder.termEndDateView.setText("No term end date");
        }
    }
    //Sets the terms list to be used.
    public void setTerms(List<Term> terms){
        mTerm = terms;
        notifyDataSetChanged();
    }
    //Gets the total item count of the passed in list.
    @Override
    public int getItemCount() {
        if(mTerm != null){
            return mTerm.size();
        }
        else {
            return 0;
        }
    }

}
