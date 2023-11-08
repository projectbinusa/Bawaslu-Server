package com.Binusa.BawasluServer.model;

import com.Binusa.BawasluServer.auditing.DateConfig;

import javax.persistence.*;

@Entity
@Table(name = "jenis_regulasi")
public class JenisRegulasi extends DateConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String jenisRegulasi;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJenisRegulasi() {
        return jenisRegulasi;
    }

    public void setJenisRegulasi(String jenisRegulasi) {
        this.jenisRegulasi = jenisRegulasi;
    }
}
