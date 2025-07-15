package com.mobin.book.book;

import java.util.UUID;

public class BookResponse {
    private UUID id;
    private String title;
    private String authorName;
    private String isbn;
    private String synopsis;
    private String Owner;
    private byte[] cover;
    private double rate;
    private boolean archived;
    private boolean shareable;

    public BookResponse(UUID id, String title, String authorName, String isbn, String synopsis, String owner, byte[] cover, double rate, boolean archived, boolean shareable) {
        this.id = id;
        this.title = title;
        this.authorName = authorName;
        this.isbn = isbn;
        this.synopsis = synopsis;
        Owner = owner;
        this.cover = cover;
        this.rate = rate;
        this.archived = archived;
        this.shareable = shareable;
    }

    public BookResponse() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getOwner() {
        return Owner;
    }

    public void setOwner(String owner) {
        Owner = owner;
    }

    public byte[] getCover() {
        return cover;
    }

    public void setCover(byte[] cover) {
        this.cover = cover;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public boolean isArchived() {
        return archived;
    }

    public void setArchived(boolean archived) {
        this.archived = archived;
    }

    public boolean isShareable() {
        return shareable;
    }

    public void setShareable(boolean shareable) {
        this.shareable = shareable;
    }
}
