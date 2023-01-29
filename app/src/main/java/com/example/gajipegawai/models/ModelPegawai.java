package com.example.gajipegawai.models;

public class ModelPegawai {
    private String id;
     String nama;
    private String gelarDepan;
    private String gelarBelakang;
     String nip;
    private String pts;
    private String jabatan;
    private String statusPegawai;
    private String unitKerja;
    private Skkp[] skkp;

//    public ModelPegawai(String id, String nama, String nip, String jabatan) {
//        this.id = id;
//        this.nama = nama;
//
//        this.nip = nip;
//        this.jabatan = jabatan;
////        this.skkp = skkp;
//    }


    public ModelPegawai(String id, String nama, String nip, String jabatan, String gelar_depan, String gelar_belakang, String pts, String status_pegawai, String unit_kerja) {
        this.id = id;
        this.nama = nama;
        this.gelarDepan = gelar_depan;
        this.gelarBelakang = gelar_belakang;
        this.nip = nip;
        this.pts = pts;
        this.jabatan = jabatan;
        this.statusPegawai = status_pegawai;
        this.unitKerja = unit_kerja;
//        this.skkp = skkp;
    }

    public void ModelPegawaiToLogin(String nama, String nip) {
        this.nama = nama;
        this.nip = nip;
    }


    public String getId() {
        return id;
    }

    public void setId(String value) {
        this.id = value;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String value) {
        this.nama = value;
    }

    public String getGelarDepan() {
        return gelarDepan;
    }

    public void setGelarDepan(String value) {
        this.gelarDepan = value;
    }

    public String getGelarBelakang() {
        return gelarBelakang;
    }

    public void setGelarBelakang(String value) {
        this.gelarBelakang = value;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String value) {
        this.nip = value;
    }

    public String getPts() {
        return pts;
    }

    public void setPts(String value) {
        this.pts = value;
    }

    public String getJabatan() {
        return jabatan;
    }

    public void setJabatan(String value) {
        this.jabatan = value;
    }

    public String getStatusPegawai() {
        return statusPegawai;
    }

    public void setStatusPegawai(String value) {
        this.statusPegawai = value;
    }

    public String getUnitKerja() {
        return unitKerja;
    }

    public void setUnitKerja(String value) {
        this.unitKerja = value;
    }

    public Skkp[] getSkkp() {
        return skkp;
    }

    public void setSkkp(Skkp[] value) {
        this.skkp = value;
    }


}

