package com.example.miplanilla;

import static android.app.Activity.RESULT_OK;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.miplanilla.databinding.FragmentFirstBinding;

import java.util.ArrayList;

public class FirstFragment extends Fragment implements OnItemClickListener<Planilla>{

    private FragmentFirstBinding binding;
    private PlanillaAdapter adaptador;

    private PlanillaApp app;
    private PlanillaViewModel planillaViewModel;


    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        binding = FragmentFirstBinding.inflate(inflater, container, false);
        app = PlanillaApp.getInstance();
        planillaViewModel = new ViewModelProvider(this).get(PlanillaViewModel.class);

        adaptador = new PlanillaAdapter(new ArrayList<>(),this);

        planillaViewModel.getPlanillaDataset().observe(this, planillas -> {
            adaptador.setItems(planillas);
            validarDataset();
        });

        setupReciclerView();
        return binding.getRoot();

    }
    private void validarDataset() {
        if (adaptador.getItemCount() == 0) {
            binding.tvWarning.setVisibility(View.VISIBLE);
            binding.ivWarning.setVisibility(View.VISIBLE);
            binding.rvPlanillas.setVisibility(View.INVISIBLE);
        } else {
            binding.tvWarning.setVisibility(View.INVISIBLE);
            binding.ivWarning.setVisibility(View.INVISIBLE);
            binding.rvPlanillas.setVisibility(View.VISIBLE);
        }
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
    @Override
    public void onResume() {
        super.onResume();
    }
    private void setupReciclerView(){
        LinearLayoutManager layoutLineal = new LinearLayoutManager(this.getContext());
        binding.rvPlanillas.setLayoutManager(layoutLineal);
        binding.rvPlanillas.setAdapter(adaptador);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemClick(Planilla data, int type) {

    }

    @Override
    public void onItemClick(Planilla data, String type) {
        Intent intent = new Intent(this.getContext(), CrearPlanillaActivity.class);
        intent.putExtra("PLANILLA_ID", data.getIdPlanilla());
        intent.putExtra("PLANILLA_CONCEPTO", data.getConcepto());
        intent.putExtra("PLANILLA_FECHA", data.getFecha());
        intent.putExtra("PLANILLA_TOTAL", data.getTotalS());
        startActivityForResult(intent, 6, ActivityOptions.makeSceneTransitionAnimation(this.getActivity()).toBundle());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == 6){
            //EDICIÓN DE UN VEHICULO EXISTENTE
            if(resultCode == RESULT_OK){
                int id = data.getIntExtra("ID", 0);//EL ID DEL VEHICULO SELECCIONADO
                    String concepto = data.getStringExtra("CONCEPTO");//EL MODELO NUEVO
                String fecha = data.getStringExtra("FECHA");
                String totalS = data.getStringExtra("TOTAL");
                Planilla actualizar = new Planilla(concepto, fecha, totalS);
                actualizar.setIdPlanilla(id);
                planillaViewModel.update(actualizar);

                //adaptador.notifyDataSetChanged();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


}