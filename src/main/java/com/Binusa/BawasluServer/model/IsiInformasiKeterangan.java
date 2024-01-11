package com.Binusa.BawasluServer.model;

import com.Binusa.BawasluServer.auditing.DateConfig;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "isi_informasi_keterangan")
@SequenceGenerator(name = "reset-sequence", sequenceName = "sequence_name", allocationSize = 1, initialValue = 1)
public class IsiInformasiKeterangan{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reset-sequence")

    private Long id;
    private String dokumen;
    @Lob
    private String pdfDokumen;

    @ManyToOne
    @JoinColumn(name = "jenis_keterangan_id")
    private JenisKeterangan jenisKeterangan;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDokumen() {
        return dokumen;
    }

    public void setDokumen(String dokumen) {
        this.dokumen = dokumen;
    }

    public JenisKeterangan getJenisKeterangan() {
        return jenisKeterangan;
    }

    public void setJenisKeterangan(JenisKeterangan jenisKeterangan) {
        this.jenisKeterangan = jenisKeterangan;
    }

    public String getPdfDokumen() {
        return pdfDokumen;
    }

    public void setPdfDokumen(String pdfDokumen) {
        this.pdfDokumen = pdfDokumen;
    }

}

