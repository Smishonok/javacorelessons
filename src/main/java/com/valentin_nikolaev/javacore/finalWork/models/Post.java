package com.valentin_nikolaev.javacore.finalWork.models;

import java.time.LocalDateTime;

public class Post {
    private long           id;
    private String        content;
    private LocalDateTime created;
    private LocalDateTime updated;

    private static long groupID = 0;

    {
        groupID++;
        this.id = groupID;
    }

    public Post(String content) {
        this.content = content;
        this.created = LocalDateTime.now();
        this.updated = this.created;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public LocalDateTime getCreatingDateAndTime() {
        return created;
    }

    public LocalDateTime getUpdatingDateAndTime() {
        return updated;
    }

    public void setContent(String content) {
        this.content = content;
        this.updated = LocalDateTime.now();
    }

    @Override
    public int hashCode() {
        int hash = this.content.hashCode();
        return hash;
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

        if (! (obj instanceof Post)) {
            return false;
        }

        Post comparingObj = (Post) obj;
        return this.content.equals(comparingObj.content);
    }

    @Override
    public String toString() {
        return this.content;
    }
}
