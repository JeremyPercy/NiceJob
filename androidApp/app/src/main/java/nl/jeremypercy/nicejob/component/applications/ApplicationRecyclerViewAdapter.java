package nl.jeremypercy.nicejob.component.applications;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import nl.jeremypercy.nicejob.GetAllUserApplicationsQuery;
import nl.jeremypercy.nicejob.R;
import nl.jeremypercy.nicejob.util.Helper;

public class ApplicationRecyclerViewAdapter extends RecyclerView.Adapter<ApplicationRecyclerViewAdapter.ApplicationViewHolder> {
    private Context context;
    private List<GetAllUserApplicationsQuery.GetAllUserApplication> userApplicationList = Collections.emptyList();

    public ApplicationRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    public void setUserApplicationList(List<GetAllUserApplicationsQuery.GetAllUserApplication> userApplicationList) {
        this.userApplicationList = userApplicationList;
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ApplicationRecyclerViewAdapter.ApplicationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater
                .from(parent.getContext());

        final View itemView = layoutInflater.inflate(R.layout.application_row,
                parent, false);
        return new ApplicationRecyclerViewAdapter.ApplicationViewHolder(itemView, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ApplicationRecyclerViewAdapter.ApplicationViewHolder holder, int position) {
        final GetAllUserApplicationsQuery.GetAllUserApplication application = this.userApplicationList.get(position);
        holder.setApplication(application);
    }

    @Override
    public int getItemCount() {
        return userApplicationList.size();
    }

    public class ApplicationViewHolder extends RecyclerView.ViewHolder {
        Context context;
        CardView applicationCardView;
        TextView titleVacancy;
        TextView date;
        TextView company;

        private ApplicationViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;
            applicationCardView = itemView.findViewById(R.id.applicationCardView);
            titleVacancy = itemView.findViewById(R.id.titleVacancy);
            date = itemView.findViewById(R.id.applicationDate);
            company = itemView.findViewById(R.id.applicationCompany);
        }

        public void setApplication(final GetAllUserApplicationsQuery.GetAllUserApplication application) {
            titleVacancy.setText(application.vacancy().title());
            String convertedDate = Helper.convertDate(application.date());
            date.setText(convertedDate);
            company.setText(application.vacancy().company().name());
        }
    }
}
