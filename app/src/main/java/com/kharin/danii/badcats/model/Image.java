package com.kharin.danii.badcats.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Image {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("width")
    @Expose
    private Object width;
    @SerializedName("height")
    @Expose
    private Object height;
    @SerializedName("mime_type")
    @Expose
    private String mimeType;
    @SerializedName("entities")
    @Expose
    private List<Object> entities = null;
    @SerializedName("breeds")
    @Expose
    private List<Breed> breeds = null;
    @SerializedName("animals")
    @Expose
    private List<Object> animals = null;
    @SerializedName("categories")
    @Expose
    private List<Object> categories = null;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Object getWidth() {
        return width;
    }

    public void setWidth(Object width) {
        this.width = width;
    }

    public Object getHeight() {
        return height;
    }

    public void setHeight(Object height) {
        this.height = height;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public List<Object> getEntities() {
        return entities;
    }

    public void setEntities(List<Object> entities) {
        this.entities = entities;
    }

    public List<Breed> getBreeds() {
        return breeds;
    }

    public void setBreeds(List<Breed> breeds) {
        this.breeds = breeds;
    }

    public List<Object> getAnimals() {
        return animals;
    }

    public void setAnimals(List<Object> animals) {
        this.animals = animals;
    }

    public List<Object> getCategories() {
        return categories;
    }

    public void setCategories(List<Object> categories) {
        this.categories = categories;
    }

}
