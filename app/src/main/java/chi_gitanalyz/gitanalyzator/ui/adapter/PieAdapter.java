package chi_gitanalyz.gitanalyzator.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import chi_gitanalyz.gitanalyzator.R;

/**
 * Created by Papin on 11.10.2016.
 */

public class PieAdapter extends BaseAdapter {
    Context ctx;
    LayoutInflater lInflater;
    ArrayList<MyPieModel> objects;

    public PieAdapter(Context context, ArrayList<MyPieModel> products) {
        ctx = context;
        objects = products;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int i) {
        return objects.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        View view1 = convertView;
        if (view1 == null) {
            view1 = lInflater.inflate(R.layout.pie_list_layout, viewGroup, false);
        }
        MyPieModel p = getProduct(i);
        ((TextView)view1.findViewById(R.id.nameLeng)).setText(p.name);
        ((TextView)view1.findViewById(R.id.nameLeng)).setTextColor(p.color);
        ((TextView)view1.findViewById(R.id.procentLeng)).setText(p.value.toString()+" %");
        return view1;
    }

    MyPieModel getProduct(int position) {
        return ((MyPieModel) getItem(position));
    }
}