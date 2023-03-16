package com.example.miplanilla;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Planilla_table")
public class Planilla {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private Integer idPlanilla;
    @NonNull
    @ColumnInfo(name = "concepto")
    private String concepto;
    @NonNull
    @ColumnInfo(name = "fecha")
    private String fecha;
    @NonNull
    @ColumnInfo(name = "totalS")
    private String  totalS;

    public Planilla(@NonNull String concepto, @NonNull String fecha, @NonNull String totalS){
        this.concepto = concepto;
        this.fecha = fecha;
        this.totalS = totalS;
    }

    @NonNull
    public Integer getIdPlanilla() {
        return idPlanilla;
    }

    public void setIdPlanilla(@NonNull Integer idPlanilla) {
        this.idPlanilla = idPlanilla;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getTotalS() {
        return totalS;
    }

    public void setTotalS(String totalS) {
        this.totalS = totalS;
    }
}
