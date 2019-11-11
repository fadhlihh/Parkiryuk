package com.example.fadhli.parkiryuk;

public class Pemesan {
    private String nama,idParkir,durasi;
    boolean statusSampai;
    int harga;

    public Pemesan(String nama,String idParkir,String durasi,boolean statusSampai,int harga){
        this.nama = nama;
        this.idParkir = idParkir;
        this.durasi = durasi;
        this.statusSampai = statusSampai;
        this.harga = harga;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setIdParkir(String idParkir) {
        this.idParkir = idParkir;
    }

    public void setDurasi(String durasi) {
        this.durasi = durasi;
    }

    public void setStatusSampai(boolean statusSampai) {
        this.statusSampai = statusSampai;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public String getNama() {
        return nama;
    }

    public String getIdParkir() {
        return idParkir;
    }

    public String getDurasi() {
        return durasi;
    }

    public boolean getStatusSampai(){
        return statusSampai;
    }

    public int getHarga() {
        return harga;
    }
}
