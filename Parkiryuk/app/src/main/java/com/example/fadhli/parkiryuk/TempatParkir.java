package com.example.fadhli.parkiryuk;

import android.widget.Button;

public class TempatParkir {
    private String nama,alamat,jamBuka;
    private int sisaSlot,hargaParkir;
    private boolean statusOnline;

    public TempatParkir(String nama,String alamat,String jamBuka,int sisaSlot,int hargaParkir,boolean statusOnline){
        this.nama = nama;
        this.alamat = alamat;
        this.jamBuka = jamBuka;
        this.sisaSlot = sisaSlot;
        this.hargaParkir = hargaParkir;
        this.statusOnline = statusOnline;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public void setJamBuka(String jamBuka) {
        this.jamBuka = jamBuka;
    }

    public void setSisaSlot(int sisaSlot) {
        this.sisaSlot = sisaSlot;
    }

    public void setHargaParkir(int hargaParkir) {
        this.hargaParkir = hargaParkir;
    }

    public void setStatusOnline(boolean statusOnline) {
        this.statusOnline = statusOnline;
    }

    public String getNama() {
        return nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getJamBuka() {
        return jamBuka;
    }

    public int getSisaSlot() {
        return sisaSlot;
    }

    public int getHargaParkir() {
        return hargaParkir;
    }

    public boolean getStatusOnline(){
        return statusOnline;
    }
}
