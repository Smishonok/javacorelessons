package com.valentin_nikolaev.javacore.finalWork.models;

public class Region {
    private long id;
    private String name;

    private static long groupId = 0;

    public Region(String name) {
        this.id = ++ groupId;
        this.name = name;
    }

    public Region(long id, String name) {
        this.id = id;
        this.name = name;
    }


    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return this.name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        if (this.hashCode() != obj.hashCode()) {
            return false;
        }

        if (! (obj instanceof Region)) {
            return false;
        }

        Region comparingObj = (Region) obj;
        return this.name.equals(comparingObj.name);
    }
}
