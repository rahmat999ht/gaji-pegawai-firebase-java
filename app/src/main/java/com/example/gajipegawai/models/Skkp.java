package com.example.gajipegawai.models;

public class Skkp {
    private String nomorSkkp;
    private String gajiPokok;
    private String masaKerjaSkkp;
    private String pangkatSkkp;
    private String golonganSkkp;
    private String tglSkkp;
    private String tmtSkkp;
    private String tmtKgbBerikutnya;

    public Skkp(
            String nomor_skkp,
            String gaji_pokok,
            String masa_kerja_skkp,
            String pangkat_skkp,
            String golongan_skkp,
            String tgl_skkp,
            String tmt_skkp,
            String tmt_kgb_berikutnya
    ){
        this.nomorSkkp = nomor_skkp;
        this.gajiPokok = gaji_pokok;
        this.masaKerjaSkkp = masa_kerja_skkp;
        this.pangkatSkkp = pangkat_skkp;
        this.golonganSkkp = golongan_skkp;
        this.tglSkkp = tgl_skkp;
        this.tmtSkkp = tmt_skkp;
        this.tmtKgbBerikutnya = tmt_kgb_berikutnya;
    }

    public String getNomorSkkp() {
        return nomorSkkp;
    }

    public void setNomorSkkp(String value) {
        this.nomorSkkp = value;
    }

    public String getGajiPokok() {
        return gajiPokok;
    }

    public void setGajiPokok(String value) {
        this.gajiPokok = value;
    }

    public String getMasaKerjaSkkp() {
        return masaKerjaSkkp;
    }

    public void setMasaKerjaSkkp(String value) {
        this.masaKerjaSkkp = value;
    }

    public String getPangkatSkkp() {
        return pangkatSkkp;
    }

    public void setPangkatSkkp(String value) {
        this.pangkatSkkp = value;
    }

    public String getGolonganSkkp() {
        return golonganSkkp;
    }

    public void setGolonganSkkp(String value) {
        this.golonganSkkp = value;
    }

    public String getTglSkkp() {
        return tglSkkp;
    }

    public void setTglSkkp(String value) {
        this.tglSkkp = value;
    }

    public String getTmtSkkp() {
        return tmtSkkp;
    }

    public void setTmtSkkp(String value) {
        this.tmtSkkp = value;
    }

    public String getTmtKgbBerikutnya() {
        return tmtKgbBerikutnya;
    }

    public void setTmtKgbBerikutnya(String value) {
        this.tmtKgbBerikutnya = value;
    }
}
