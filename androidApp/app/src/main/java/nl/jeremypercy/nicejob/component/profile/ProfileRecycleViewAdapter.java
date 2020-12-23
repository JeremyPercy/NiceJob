package nl.jeremypercy.nicejob.component.profile;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import nl.jeremypercy.nicejob.ProfileQuery;
import nl.jeremypercy.nicejob.R;

public class ProfileRecycleViewAdapter extends RecyclerView.Adapter<ProfileRecycleViewAdapter.ProfileViewHolder> {
    private Context context;
    private List<ProfileQuery.Experience> experienceList = Collections.emptyList();
    private List<ProfileQuery.Education> educationList = Collections.emptyList();

    public ProfileRecycleViewAdapter(Context context) {
        this.context = context;
    }

    public void setExperience(List<ProfileQuery.Experience> experienceList) {
        this.experienceList = experienceList;
        this.notifyDataSetChanged();
    }

    public void setEducation(List<ProfileQuery.Education> educationList) {
        this.educationList = educationList;
        this.notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ProfileRecycleViewAdapter.ProfileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater
                .from(parent.getContext());

        final View itemView = layoutInflater.inflate(R.layout.profile_row,
                parent, false);
        return new ProfileViewHolder(itemView, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileRecycleViewAdapter.ProfileViewHolder holder, int position) {
        if (experienceList != null && !experienceList.isEmpty()) {
            final ProfileQuery.Experience experience = this.experienceList.get(position);
            holder.setExperience(experience);
        }
        if(educationList != null && !educationList.isEmpty()){
            final ProfileQuery.Education education = this.educationList.get(position);
            holder.setEducation(education);
        }
    }

    @Override
    public int getItemCount() {
        if (experienceList != null && !experienceList.isEmpty()) {
            return experienceList.size();
        }else {
            return educationList.size();
        }
    }

    public class ProfileViewHolder extends RecyclerView.ViewHolder {

        Context context;
        CardView profileCardView;
        TextView title;
        TextView subTitle;
        TextView periodeFrom;
        TextView periodeTo;
        TextView educationTitle;
        String Id;


        public ProfileViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;
            title = itemView.findViewById(R.id.titleName);
            educationTitle = itemView.findViewById(R.id.schoolName);
            subTitle = itemView.findViewById(R.id.subTitle);
            periodeFrom = itemView.findViewById(R.id.periodeFrom);
            periodeTo = itemView.findViewById(R.id.periodTo);
        }

        public void setExperience(final ProfileQuery.Experience experience) {
            title.setText(experience.companyName());
            subTitle.setText(experience.experience());
            periodeFrom.setText(experience.periodeFrom());
            periodeTo.setText(experience.periodeTo());
        }

        public void setEducation(final ProfileQuery.Education education) {
            title.setVisibility(View.GONE);
            educationTitle.setVisibility(View.VISIBLE);
            educationTitle.setText(education.school());
            subTitle.setText(education.diploma());
            periodeFrom.setText(education.periodeFrom());
            periodeTo.setText(education.periodeTo());
        }
    }

}
