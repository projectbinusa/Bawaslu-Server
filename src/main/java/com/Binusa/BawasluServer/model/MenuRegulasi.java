package com.Binusa.BawasluServer.model;

import com.Binusa.BawasluServer.auditing.DateConfig;

import javax.persistence.*;

@Entity
@Table(name = "menu_regulasi")
public class MenuRegulasi extends DateConfig {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String menuRegulasi;
    @ManyToOne
    @JoinColumn(name = "jenis_regulasi_id")
    private JenisRegulasi jenisRegulasiId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMenuRegulasi() {
        return menuRegulasi;
    }

    public void setMenuRegulasi(String menuRegulasi) {
        this.menuRegulasi = menuRegulasi;
    }

    public JenisRegulasi getJenisRegulasiId() {
        return jenisRegulasiId;
    }

    public void setJenisRegulasiId(JenisRegulasi jenisRegulasiId) {
        this.jenisRegulasiId = jenisRegulasiId;
    }
}
