package com.Binusa.BawasluServer.model;


import com.Binusa.BawasluServer.auditing.DateConfig;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "jenis_informasi")
public class JenisInformasi extends DateConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nama_jenis;

    @OneToMany(mappedBy = "jenisInformasi", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<JenisKeterangan> jenisKeteranganList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNama_jenis() {
        return nama_jenis;
    }

    public void setNama_jenis(String nama_jenis) {
        this.nama_jenis = nama_jenis;
    }

    public List<JenisKeterangan> getJenisKeteranganList() {
        return jenisKeteranganList;
    }

    public void setJenisKeteranganList(List<JenisKeterangan> jenisKeteranganList) {
        this.jenisKeteranganList = jenisKeteranganList;
    }
}
