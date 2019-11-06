package com.example.fadhli.parkiryuk;

public class History {
    private String bulan,tempatParkir,idParkir,durasi;
    private int tanggal,harga;

    public History(String bulan,String tempatParkir,String idParkir,String durasi,int tanggal,int harga){
        this.bulan = bulan;
        this.tempatParkir = tempatParkir;
        this.idParkir = idParkir;
        this.durasi = durasi;
        this.tanggal = tanggal;
        this.harga = harga;
    }

    public void setBulan(String bulan) {
        this.bulan = bulan;
    }

    public void setTempatParkir(String tempatParkir) {
        this.tempatParkir = tempatParkir;
    }

    public void setIdParkir(String idParkir) {
        this.idParkir = idParkir;
    }

    public void setDurasi(String durasi) {
        this.durasi = durasi;
    }

    public void setTanggal(int tanggal) {
        this.tanggal = tanggal;
    }

    public void setHarga(int harga) {
        this.harga = harga;
    }

    public String getBulan() {
        return bulan;
    }

    public String getTempatParkir() {
        return tempatParkir;
    }

    public String getIdParkir() {
        return idParkir;
    }

    public String getDurasi() {
        return durasi;
    }

    public int getTanggal() {
        return tanggal;
    }

    public int getHarga() {
        return harga;
    }
}
