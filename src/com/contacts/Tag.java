package com.contacts;

import java.util.Objects;

public class Tag {

    private String name;

    public Tag(String name) {
        this.name = name.toLowerCase().trim();
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) return true;

        if (!(obj instanceof Tag)) return false;

        Tag other = (Tag) obj;

        return Objects.equals(name, other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }
}