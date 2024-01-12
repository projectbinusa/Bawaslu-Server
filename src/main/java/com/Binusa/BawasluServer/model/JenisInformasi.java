package com.Binusa.BawasluServer.model;


import com.Binusa.BawasluServer.auditing.DateConfig;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "jenis_informasi")
@SequenceGenerator(name = "reset-sequence", sequenceName = "sequence_name", allocationSize = 1, initialValue = 1)
public class JenisInformasi {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reset-sequence")
    private Long id;
    private String namaInformasi;
//    @OneToMany(mappedBy = "jenisInformasi", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<JenisKeterangan> jenisKeteranganList;


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

//    public List<JenisKeterangan> getJenisKeteranganList() {
//        return jenisKeteranganList;
//    }
//
//    public void setJenisKeteranganList(List<JenisKeterangan> jenisKeteranganList) {
//        this.jenisKeteranganList = jenisKeteranganList;
//    }
}

