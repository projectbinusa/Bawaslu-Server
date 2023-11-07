package com.Binusa.BawasluServer.DTO;

public class TagsDTO {
    private long id;
    private String tags;
    private Long beritaId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public Long getBeritaId() {
        return beritaId;
    }

    public void setBeritaId(Long beritaId) {
        this.beritaId = beritaId;
    }
}
