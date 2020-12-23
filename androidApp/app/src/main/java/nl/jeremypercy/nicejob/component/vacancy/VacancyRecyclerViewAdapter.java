package nl.jeremypercy.nicejob.component.vacancy;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import nl.jeremypercy.nicejob.GetAllVacanciesQuery;
import nl.jeremypercy.nicejob.R;
import nl.jeremypercy.nicejob.SearchQueryVacancyQuery;
import nl.jeremypercy.nicejob.util.AplClient;

public class VacancyRecyclerViewAdapter extends RecyclerView.Adapter<VacancyRecyclerViewAdapter.VacancyViewHolder> {
    private Context context;
    private List<GetAllVacanciesQuery.GetAllVacancy> vacancyList = Collections.emptyList();
    private List<SearchQueryVacancyQuery.SearchQueryVacancy> vacancySearchList = Collections.emptyList();

    public VacancyRecyclerViewAdapter(Context context) {
        this.context = context;
    }

    public void setVacancies(List<GetAllVacanciesQuery.GetAllVacancy> vacancyList) {
        this.vacancyList = vacancyList;
        this.notifyDataSetChanged();
    }

    public void setVacancySearch(List<SearchQueryVacancyQuery.SearchQueryVacancy> vacancySearchList) {
        this.vacancySearchList = vacancySearchList;
        this.notifyDataSetChanged();
    }
    @NonNull
    @Override
    public VacancyRecyclerViewAdapter.VacancyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater
                .from(parent.getContext());

        final View itemView = layoutInflater.inflate(R.layout.vacancy_row,
                parent, false);
        return new VacancyViewHolder(itemView, context);
    }

    @Override
    public void onBindViewHolder(@NonNull VacancyRecyclerViewAdapter.VacancyViewHolder holder, int position) {
        if (vacancyList != null && !vacancyList.isEmpty()) {
            final GetAllVacanciesQuery.GetAllVacancy vacancy = this.vacancyList.get(position);
            holder.setVacancy(vacancy);
        }
        if(vacancySearchList != null && !vacancySearchList.isEmpty()){
            final SearchQueryVacancyQuery.SearchQueryVacancy vacancySearch = this.vacancySearchList.get(position);
            holder.setSearchQuery(vacancySearch);
        }
    }

    @Override
    public int getItemCount() {
        if (vacancyList != null && !vacancyList.isEmpty()) {
            return vacancyList.size();
        }else {
            return vacancySearchList.size();
        }
    }

    public class VacancyViewHolder extends RecyclerView.ViewHolder {

        Context context;
        CardView cardView;
        TextView title;
        TextView description;
        TextView companyName;
        TextView city;
        TextView salary;
        String Id;


        VacancyViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;
            title = itemView.findViewById(R.id.titleVacancy);
            description = itemView.findViewById(R.id.descriptionVacancy);
            companyName = itemView.findViewById(R.id.companyName);
            city = itemView.findViewById(R.id.descriptionCity);
            salary = itemView.findViewById(R.id.descriptionSalary);
            cardView = itemView.findViewById(R.id.cardViewId);

        }

        void setVacancy(final GetAllVacanciesQuery.GetAllVacancy vacancy) {
            title.setText(vacancy.title());
            description.setText(vacancy.description());
            companyName.setText(vacancy.company().name());
            city.setText(vacancy.company().city());
            salary.setText(vacancy.salary().toString());
            Id = vacancy.id();

            cardView.setOnClickListener((View view) -> {
                Intent intent = new Intent(context, VacancyPage.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                intent.putExtra("id", Id);
                context.startActivity(intent);
            });
        }

        void setSearchQuery(final SearchQueryVacancyQuery.SearchQueryVacancy vacancySearch) {
            title.setText(vacancySearch.title());
            description.setText(vacancySearch.description());
            companyName.setText(vacancySearch.company().name());
            city.setText(vacancySearch.company().city());
            salary.setText(vacancySearch.salary().toString());
            Id = vacancySearch.id();
            cardView.setOnClickListener((View view) -> {
                Intent intent = new Intent(context, VacancyPage.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                intent.putExtra("id", Id);
                context.startActivity(intent);
            });
        }
    }
}
