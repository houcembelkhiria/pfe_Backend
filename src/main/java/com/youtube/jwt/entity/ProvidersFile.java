package com.youtube.jwt.entity;


import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Document(collection = "providerList")
public class ProvidersFile {

    @MongoId
    private String _id;

    private String prod;

    private String url;

    private String provider;

    private String name;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getProd() {
        return prod;
    }

    public void setProd(String prod) {
        this.prod = prod;
    }

    public String getURL() {
        return url;
    }

    public void setURL(String url) {
        this.url = url;
    }

    public String getProvider() {
        return provider;
    }

    public void setProvider(String provider) {
        provider = provider;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        name = name;
    }

}
