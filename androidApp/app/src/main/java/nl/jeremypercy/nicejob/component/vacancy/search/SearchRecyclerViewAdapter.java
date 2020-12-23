//package nl.jeremypercy.nicejob.component.vacancy.search;
//
//import android.content.Context;
//import android.content.Intent;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import java.util.Collections;
//import java.util.List;
//
//import androidx.annotation.NonNull;
//import androidx.cardview.widget.CardView;
//import androidx.recyclerview.widget.RecyclerView;
//import nl.jeremypercy.nicejob.GetAllVacanciesQuery;
//import nl.jeremypercy.nicejob.R;
//import nl.jeremypercy.nicejob.SearchQueryVacancyQuery;
//import nl.jeremypercy.nicejob.component.vacancy.VacancyPage;
//import nl.jeremypercy.nicejob.component.vacancy.VacancyRecyclerViewAdapter;
//
//public class SearchRecyclerViewAdapter extends RecyclerView.Adapter<SearchRecyclerViewAdapter.SearchViewHolder>{
//    private Context context;
//    private List<GetAllVacanciesQuery.GetAllVacancy> vacancyList = Collections.emptyList();
//    private List<SearchQueryVacancyQuery.SearchQueryVacancy> vacancySearchList = Collections.emptyList();
//
//    public SearchRecyclerViewAdapter(Context context) {
//        this.context = context;
//    }
//
//    public void setVacancySearch(List<SearchQueryVacancyQuery.SearchQueryVacancy> vacancySearchList) {
//        this.vacancySearchList = vacancySearchList;
//        this.notifyDataSetChanged();
//    }
//    @NonNull
//    @Override
//    public SearchRecyclerViewAdapter.SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater layoutInflater = LayoutInflater
//                .from(parent.getContext());
//
//        final View itemView = layoutInflater.inflate(R.layout.vacancy_row,
//                parent, false);
//        return new SearchRecyclerViewAdapter.SearchViewHolder(itemView, context);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull SearchRecyclerViewAdapter.SearchViewHolder holder, int position) {
//        if(vacancySearchList != null && !vacancySearchList.isEmpty()){
//            final SearchQueryVacancyQuery.SearchQueryVacancy vacancySearch = this.vacancySearchList.get(position);
//            holder.setSearchQuery(vacancySearch);
//        }
//    }
//
//    @Override
//    public int getItemCount() {
//        return vacancyList.size();
//    }
//
//    public class SearchViewHolder extends RecyclerView.ViewHolder {
//
//        Context context;
//        CardView cardView;
//        TextView title;
//        TextView description;
//        TextView companyName;
//        TextView city;
//        TextView salary;
//        String Id;
//
//
//        public SearchViewHolder(@NonNull View itemView, Context context) {
//            super(itemView);
//            this.context = context;
//            title = itemView.findViewById(R.id.titleVacancy);
//            description = itemView.findViewById(R.id.descriptionVacancy);
//            companyName = itemView.findViewById(R.id.companyName);
//            city = itemView.findViewById(R.id.descriptionCity);
//            salary = itemView.findViewById(R.id.descriptionSalary);
//            cardView = itemView.findViewById(R.id.cardViewId);
//
//        }
//
//        public void setSearchQuery(final SearchQueryVacancyQuery.SearchQueryVacancy vacancySearch) {
//            title.setText(vacancySearch.title());
//            description.setText(vacancySearch.description());
//            companyName.setText(vacancySearch.company().name());
//            city.setText(vacancySearch.company().city());
//            salary.setText(vacancySearch.salary().toString());
//            Id = vacancySearch.id();
//            Toast.makeText(context, "Could not load the vacancy information", Toast.LENGTH_LONG).show();
//            cardView.setOnClickListener((View view) -> {
//                Intent intent = new Intent(context, VacancyPage.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
//                intent.putExtra("id", Id);
//                context.startActivity(intent);
//            });
//        }
//    }
//}
