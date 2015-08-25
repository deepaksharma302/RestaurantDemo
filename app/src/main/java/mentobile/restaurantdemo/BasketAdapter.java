package mentobile.restaurantdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Administrator on 6/8/2015.
 */

public class BasketAdapter extends ArrayAdapter<ItemDetail> {

    private Context context;
    private int resourceID;
    private ArrayList<ItemDetail> arrayList = new ArrayList<>();

    public BasketAdapter(Context context, int resourceID, ArrayList<ItemDetail> arrayList) {
        super(context, resourceID, arrayList);
        this.context = context;
        this.resourceID = resourceID;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public ItemDetail getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    RecordHolder holder = null;

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View gridView = convertView;
        if (gridView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridView = inflater.inflate(resourceID, viewGroup, false);
            holder = new RecordHolder();
            holder.tvItemName = (TextView) gridView.findViewById(R.id.basket_tv_name);
            holder.tvItemQuantity = (TextView) gridView.findViewById(R.id.basket_tv_quantity);
            holder.tvItemPrice = (TextView) gridView.findViewById(R.id.basket_tv_price);
            holder.imgViewPlus = (ImageButton) gridView.findViewById(R.id.basket_imgbtn_plus);
            holder.imgViewMinus = (ImageButton) gridView.findViewById(R.id.basket_imgbtn_minus);
            gridView.setTag(holder);
        } else {
            holder = (RecordHolder) gridView.getTag();
        }
        final ItemDetail itemDetail = arrayList.get(position);
        holder.tvItemName.setText(itemDetail.getName());
        if (itemDetail.getQuantity() > 1) {
            holder.tvItemQuantity.setText(itemDetail.getQuantity() + " x ");
          //  holder.imgViewMinus.setVisibility(View.VISIBLE);
        } else {
            holder.tvItemQuantity.setText("");
            holder.imgViewMinus.setVisibility(View.INVISIBLE);
        }
        holder.tvItemPrice.setText("$" + itemDetail.getPrice());
        holder.imgViewPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils.loadAnimation(context, R.anim.aplha);
                view.startAnimation(animation);
                itemDetail.setQuantity(itemDetail.getQuantity() + 1);
                notifyDataSetChanged();
            }
        });
        holder.imgViewMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Animation animation = AnimationUtils.loadAnimation(context, R.anim.aplha);
                view.startAnimation(animation);
                if (itemDetail.getQuantity() > 1) {
                    itemDetail.setQuantity(itemDetail.getQuantity() - 1);
                }
                notifyDataSetChanged();
            }
        });

        return gridView;
    }

    static class RecordHolder {
        ImageButton imgViewPlus;
        ImageButton imgViewMinus;
        TextView tvItemName;
        TextView tvItemQuantity;
        TextView tvItemPrice;
    }
}
