package com.kharin.danii.badcats.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Index;

@Entity(indexes = {
        @Index(value = "id", unique = true)
})
public class Favorite {

    @Id
    private String id;
    private String url;

    public Favorite(String id) {
        this.id = id;
    }

    @Generated(hash = 323011040)
    public Favorite(String id, String url) {
        this.id = id;
        this.url = url;
    }

    @Generated(hash = 459811785)
    public Favorite() {
    }

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
}
