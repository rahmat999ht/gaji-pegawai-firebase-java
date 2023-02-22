package com.example.gajipegawai.models;

public class Skkp {
    private String id;
    private String nomorSkkp;
    private String gajiPokok;
    private String masaKerjaSkkp;
    private String pangkatSkkp;
    private String golonganSkkp;
    private String tglSkkp;
    private String tmtSkkp;
    private String tmtKgbBerikutnya;
    private String komf;
    private String diSahkanOleh;

    public Skkp(
            String id,
            String nomor_skkp,
            String gaji_pokok,
            String pangkat_skkp,
            String golongan_skkp,
            String masa_kerja,
            String tgl_skkp,
            String tmt_skkp,
            String tmt_skkp_berikutnya,
            String komf,
            String diSahkanOleh
    ) {
        this.id = id;
        this.nomorSkkp = nomor_skkp;
        this.gajiPokok = gaji_pokok;
        this.pangkatSkkp = pangkat_skkp;
        this.golonganSkkp = golongan_skkp;
        this.masaKerjaSkkp = masa_kerja;
        this.tglSkkp = tgl_skkp;
        this.tmtSkkp = tmt_skkp;
        this.tmtKgbBerikutnya = tmt_skkp_berikutnya;
        this.komf = komf;
        this.diSahkanOleh = diSahkanOleh;
    }


    public String getId() {
        return id;
    }

    public void setId(String value) {
        this.id = value;
    }


    public String getKonf() {
        return komf;
    }

    public void setKonf(String value) {
        this.komf = value;
    }

    public String getDiSahkanOleh() {
        return diSahkanOleh;
    }

    public void setDiSahkanOleh(String value) {
        this.diSahkanOleh = value;
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
