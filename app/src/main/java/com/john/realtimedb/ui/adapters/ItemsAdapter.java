package com.john.realtimedb.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
/**
 * Created by john on 12/11/17.
 */

public class ItemsAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    /*private List<ModelItemListResponse.ResponseBean> itemList;
    private Activity context;
    private Picasso picasso;
    private int viewType;
    private Callback callback;

    public ItemsAdapter(List<ModelItemListResponse.ResponseBean> itemList, Activity context, Picasso picasso) {
        this.itemList = itemList;
        this.context = context;
        this.picasso = picasso;
    }*/

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            /*case AppConstants.VIEW_TYPE_LOADING:
                return new LoadingViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.row_loading,parent,false));
            case AppConstants.VIEW_TYPE_FAILED:
                return new FailedViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.row_failed,parent,false));
            default:
                return new ItemsViewHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.row_items,parent,false));*/
        }
        return null;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }


    @Override
    public int getItemCount() {
       return 0;
        /* if(itemList ==null|| itemList.size()==0){
            return 1;
        }else {
            return itemList.size();
        }*/
    }

    @Override
    public int getItemViewType(int position) {
        return /*viewType*/0;
    }


    class LoadingViewHolder extends BaseViewHolder {


        LoadingViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void clear() {

        }
    }


    class FailedViewHolder extends BaseViewHolder {

        FailedViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void clear() {

        }
    }

    class ItemsViewHolder extends BaseViewHolder {



        ItemsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);


        }

        @Override
        protected void clear() {

        }

        @Override
        public void onBind(int position) {
            super.onBind(position);


        }
    }

    public interface Callback{
        void onItemSelected(String title, String itemId);
    }
}
