package com.Binusa.BawasluServer.model;

import com.Binusa.BawasluServer.auditing.DateConfig;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "jenis_keterangan")
public class JenisKeterangan {
    @Id
    @GenericGenerator(
            name = "reset-sequence",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(
                            name = "sequence_name",
                            value = "your_sequence_name"
                    ),
                    @org.hibernate.annotations.Parameter(
                            name = "initial_value",
                            value = "1"
                    )
            }
    )
    @GeneratedValue(generator = "reset-sequence")
    private Long id;

    private String keterangan;

    @ManyToOne
    @JoinColumn(name = "jenis_informasi_id")
    private JenisInformasi jenisInformasi;

    @OneToMany(mappedBy = "jenisKeterangan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<IsiInformasiKeterangan> isiInformasiKeteranganList;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
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