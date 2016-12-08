package com.fengyunjie.railway.model;

//@MappedSuperclass
public class BaseEntity {
    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isNew() {
        return this.id == null;
    }

}
