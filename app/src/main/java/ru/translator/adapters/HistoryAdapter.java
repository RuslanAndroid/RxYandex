package ru.translator.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.translator.R;

import ru.translator.interfaces.ClickListener;
import ru.translator.util.items.DataBean;

//адаптер списка истории и избранного
public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private List<DataBean> filteredList;
    private List<DataBean> rows;
    private Context mContext;
    private ClickListener favClick;
    private ClickListener mClickListener;
    private HistoryAdapter.UserFilter userFilter;

    public Filter getFilter() {
        if(userFilter == null)
            userFilter = new HistoryAdapter.UserFilter(this, rows);
        return userFilter;
    }

    public void setInfo(List<DataBean> rows) {
        if(rows==null){
            this.rows=new ArrayList<>();
            this.filteredList=this.rows;
        }else{
            this.rows = rows;
            this.filteredList = rows;
        }
        notifyDataSetChanged();
    }


    private static class UserFilter extends Filter {

        private final HistoryAdapter adapter;

        private final List<DataBean> originalList;

        private final List<DataBean> filteredList;

        private UserFilter(HistoryAdapter adapter, List<DataBean> originalList) {
            super();
            this.adapter = adapter;
            this.originalList = new LinkedList<>(originalList);
            this.filteredList = new ArrayList<>();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            filteredList.clear();
            final FilterResults results = new FilterResults();

            if (constraint.length() == 0) {
                filteredList.addAll(originalList);
            } else {
                final String filterPattern = constraint.toString().toLowerCase().trim();

                for (DataBean dataBean : originalList) {
                    if (dataBean.getTextFrom().toLowerCase().contains(filterPattern)||dataBean.getTextTo().toLowerCase().contains(filterPattern)) {
                        filteredList.add(dataBean);
                    }
                }
            }
            results.values = filteredList;
            results.count = filteredList.size();
            return results;
        }
        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            adapter.filteredList.clear();
            adapter.filteredList.addAll((List<DataBean>) results.values);
            adapter.notifyDataSetChanged();
        }
    }
    public void setonClickListener(ClickListener clickListener){
        this.mClickListener = clickListener;
    }
    public HistoryAdapter(Context context) {
        mContext = context;
    }

    public void setFavClick(ClickListener favClick){
        this.favClick = favClick;
    }
    public void makeItemFav(int position){
        getItem(position).setFav(!getItem(position).isFav());
        notifyItemChanged(position);
    }
    public boolean isItemFav(int position){
        return  getItem(position).isFav();
    }

    public void removeItem(int position){
        rows.remove(position);
        notifyDataSetChanged();
    }
    public int getPositionById(int id){
        int position = -1;
        for(int i = 0;i<rows.size();i++){
            if(id==rows.get(i).getId()){
                position = i;
            }
        }
        return position;
    }

    public int getId(int position){
        return getItem(position).getId();
    }

    public void addItem(DataBean dataBean){
        rows.add(dataBean);
        notifyItemInserted(rows.size()+1);
    }
    public void ramoveAllFavs(){
        for(int i = 0;i<rows.size();i++){
            if(rows.get(i).isFav()){
                rows.get(i).setFav(false);
                notifyItemChanged(i);
            }
        }
    }
    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_history, parent, false);
        HistoryAdapter.ViewHolder vh = new HistoryAdapter.ViewHolder(v);
        return vh;
    }
    public DataBean getItem(int position) {
        return rows.get(position);
    }
    @Override
    public void onBindViewHolder(HistoryAdapter.ViewHolder holder, final int position) {
        holder.from     .setText(getItem(position).getTextFrom());
        holder.to       .setText(getItem(position).getTextTo());
        holder.langFrom .setText(getItem(position).getLangFrom());
        holder.langTo   .setText(getItem(position).getLangTo());
        int favImg = isItemFav(position) ?  R.drawable.fav_icon_yep : R.drawable.fav_icon_no;

        Picasso.with(mContext).load(favImg).into(holder.fav);
        holder.fav.setOnClickListener(v -> favClick.onClick(v,position));
        holder.setClickListener(mClickListener);

    }

    @Override
    public int getItemCount() {
        if (rows == null) return 0;
        return rows.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{

        @BindView(R.id.textFrom)    TextView    from;
        @BindView(R.id.textTo)      TextView    to;
        @BindView(R.id.lang_from)   TextView    langFrom;
        @BindView(R.id.lang_to)     TextView    langTo;
        @BindView(R.id.fav_img)     ImageView   fav;
        ClickListener clickListener;
        public ViewHolder(final View v) {
            super(v);
            ButterKnife.bind(this, v);
            v.setOnClickListener(this);
        }

        void setClickListener(ClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }
        @Override
        public void onClick(View view) {
            clickListener.onClick(view, getAdapterPosition());
        }
    }
}