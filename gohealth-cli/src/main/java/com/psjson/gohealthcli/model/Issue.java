package com.psjson.gohealthcli.model;

import com.opencsv.bean.CsvBindByPosition;

public class Issue {

    @CsvBindByPosition(position = 0, required = true)
    private String id;

    @CsvBindByPosition(position = 1)
    private String parentId;

    @CsvBindByPosition(position = 2)
    private String description;

    @CsvBindByPosition(position = 3)
    private String link;

    @CsvBindByPosition(position = 4)
    private IssueStatus status;

    public Issue() {

    }

    public Issue(String parentId, String description, String link) {
        this.parentId = parentId;
        this.description = description;
        this.link = link;
        this.status = IssueStatus.OPEN;
    }

    public Issue(String description, String link) {
        this.description = description;
        this.link = link;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public IssueStatus getStatus() {
        return status;
    }

    public void setStatus(IssueStatus status) {
        this.status = status;
    }

    public void close() {
        this.status = IssueStatus.CLOSED;
    }
}