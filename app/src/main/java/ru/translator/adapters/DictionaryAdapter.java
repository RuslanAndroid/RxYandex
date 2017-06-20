package ru.translator.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ru.translator.R;
import ru.translator.models.Mean;
import ru.translator.models.Syn;
import ru.translator.models.Tr;


//адаптер списка подробного перевода
public class DictionaryAdapter extends RecyclerView.Adapter<DictionaryAdapter.ViewHolder> {
    private List<Tr> rows;

    public void setInfo(List<Tr> rows) {
        this.rows = rows;
        notifyDataSetChanged();
    }
    @Override
    public DictionaryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_dict, parent, false);
        return new ViewHolder(v);
    }

    private Tr getItem(int position) {
        return rows.get(position);
    }

    @Override
    public void onBindViewHolder(DictionaryAdapter.ViewHolder holder, int position) {

        holder.number.setText(String.valueOf(position+1));
        holder.text.setText(getItem(position).synString);
        holder.info.setText(getItem(position).meanString);

    }

    @Override
    public int getItemCount() {
        if (rows == null) return 0;
        return rows.size();
    }
    class ViewHolder extends RecyclerView.ViewHolder  {
        @BindView(R.id.number)    TextView    number;
        @BindView(R.id.res_text)    TextView    text;
        @BindView(R.id.info)    TextView    info;

        ViewHolder(final View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }
}