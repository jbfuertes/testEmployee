package com.john.realtimedb.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.john.realtimedb.R;
import com.john.realtimedb.model.ui_response.EmployeeUiModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by john on 3/9/18.
 */

public class EmployeeAdapter extends RecyclerView.Adapter<BaseViewHolder> {

    private List<EmployeeUiModel.Employee> employee;

    public EmployeeAdapter(List<EmployeeUiModel.Employee> employee){
        this.employee = employee;

    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MainViewHolder(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.row_employee,parent,false));
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return null== employee ?0: employee.size();
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    public void setItemList(List<EmployeeUiModel.Employee> employee){
        this.employee = employee;
    }



    private class FailedViewHolder extends BaseViewHolder {

        FailedViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        protected void clear() {

        }
    }

    class MainViewHolder extends BaseViewHolder {

        @BindView(R.id.firstNameText)
        TextView firstNameText;

        @BindView(R.id.lastNameText)
        TextView lastNameText;

        @BindView(R.id.emailText)
        TextView emailText;

        MainViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }

        @Override
        protected void clear() {

        }

        @Override
        public void onBind(int position) {
            super.onBind(position);

            EmployeeUiModel.Employee employee = EmployeeAdapter.this.employee.get(position);

            emailText.setText(employee.email);

            firstNameText.setText(employee.firstName);

            lastNameText.setText(employee.lastName);

        }
    }

}
