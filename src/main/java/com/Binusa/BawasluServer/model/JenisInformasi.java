package com.Binusa.BawasluServer.model;


import com.Binusa.BawasluServer.auditing.DateConfig;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "jenis_informasi")
public class JenisInformasi {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String namaInformasi;
    @OneToMany(mappedBy = "jenisInformasi")
    private List<JenisKeterangan> jenisKeteranganList;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNamaInformasi() {
        return namaInformasi;
    }

    public void setNamaInformasi(String namaInformasi) {
        this.namaInformasi = namaInformasi;
    }

    public List<JenisKeterangan> getJenisKeteranganList() {
        return jenisKeteranganList;
    }

    public void setJenisKeteranganList(List<JenisKeterangan> jenisKeteranganList) {
        this.jenisKeteranganList = jenisKeteranganList;
    }
}

