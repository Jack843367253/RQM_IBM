package com.ibm.rqm.xmlparser.Model;

/**
 * Created by Administrator on 2015/4/6 0006.
 */
public class Project {
    private String identifier;
    private String title;
    private String description;
    private String alias;

    @Override
    public String toString() {
        return "Title: " + title;
    }

    public Project(){}

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
