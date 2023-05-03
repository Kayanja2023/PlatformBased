package com.example.apparchitects;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

 class ComplaintsAdapter extends RecyclerView.Adapter<ComplaintsAdapter.ComplaintViewHolder> {
     Spinner Spin_status;

    private List<Complaint> complaints;
     private boolean isAdmin;


     public ComplaintsAdapter(List<Complaint> complaints, boolean isAdmin) {

         this.complaints = complaints;
         this.isAdmin= isAdmin;
    }

    @NonNull
    @Override
    public ComplaintViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.complaint_item, parent, false);
        return new ComplaintViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComplaintViewHolder holder, int position) {
        Complaint complaint = complaints.get(position);
        holder.roomNumber.setText(String.valueOf(complaint.getRoomNumber()));
        holder.description.setText(complaint.getDescription());
        holder.status.setText(complaint.getStatus());

        if (isAdmin) {
            holder.statusSpinner.setVisibility(View.VISIBLE);
            // Set up the spinner and listener for status updates here
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                    holder.statusSpinner.getContext(),
                    R.array.complaint_status_array,
                    android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            holder.statusSpinner.setAdapter(adapter);

            // Set the spinner's selection to the current status
            int spinnerPosition = adapter.getPosition(complaint.getStatus());
            holder.statusSpinner.setSelection(spinnerPosition);



            // Set up the listener for status updates
            holder.statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String selectedStatus = (String) parent.getItemAtPosition(position);
                    Toast.makeText(adapter.getContext(), selectedStatus, Toast.LENGTH_SHORT).show();

                        // Update the complaint status in your database here
                        ComplaintDatabaseHelper databaseHelper = new ComplaintDatabaseHelper(holder.statusSpinner.getContext());
                        databaseHelper.updateComplaintStatus(complaint.getid(), selectedStatus);

                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {
                    // Do nothing
                }
            });



        } else {
            holder.statusSpinner.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return complaints.size();
    }

    public static class ComplaintViewHolder extends RecyclerView.ViewHolder {
        TextView roomNumber;
        TextView description;
        TextView status;
        Spinner statusSpinner;

        public ComplaintViewHolder(@NonNull View itemView) {
            super(itemView);
            roomNumber = itemView.findViewById(R.id.roomnumber);
            description = itemView.findViewById(R.id.description);
            status = itemView.findViewById(R.id.status);
            statusSpinner = itemView.findViewById(R.id.status_spinner);
        }
    }
}

