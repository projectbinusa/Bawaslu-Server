package com.Binusa.BawasluServer.DTO;

public class MenuRegulasiDTO {
    private long id;
    private Long idJenisRegulasi;
    private String menuRegulasi;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Long getIdJenisRegulasi() {
        return idJenisRegulasi;
    }

    public void setIdJenisRegulasi(Long idJenisRegulasi) {
        this.idJenisRegulasi = idJenisRegulasi;
    }

    public String getMenuRegulasi() {
        return menuRegulasi;
    }

    public void setMenuRegulasi(String menuRegulasi) {
        this.menuRegulasi = menuRegulasi;
    }
}
