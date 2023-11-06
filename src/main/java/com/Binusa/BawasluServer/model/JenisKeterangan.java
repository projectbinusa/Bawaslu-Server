package com.Binusa.BawasluServer.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "jenis_keterangan")
public class JenisKeterangan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nama_keterangan;

    @ManyToOne
    @JoinColumn(name = "jenis_informasi_id")
    private JenisInformasi jenisInformasi;

    @OneToMany(mappedBy = "jenisKeterangan", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<IsiInformasiKeterangan> isiInformasiKeteranganList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNama_keterangan() {
        return nama_keterangan;
    }

    public void setNama_keterangan(String nama_keterangan) {
        this.nama_keterangan = nama_keterangan;
    }

    public JenisInformasi getJenisInformasi() {
        return jenisInformasi;
    }

    public void setJenisInformasi(JenisInformasi jenisInformasi) {
        this.jenisInformasi = jenisInformasi;
    }

    public List<IsiInformasiKeterangan> getIsiInformasiKeteranganList() {
        return isiInformasiKeteranganList;
    }

    public void setIsiInformasiKeteranganList(List<IsiInformasiKeterangan> isiInformasiKeteranganList) {
        this.isiInformasiKeteranganList = isiInformasiKeteranganList;
    }
}
