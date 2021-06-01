package com.company;

import java.util.Objects;

public class Combo {
    private Item object;
    private Item subject;
    private Item result;
    private String message;

    public Combo(Item object, Item subject, Item result, String message) {
        this.object = object;
        this.subject = subject;
        this.result = result;
        this.message = message;
    }

    public Combo(Item object, Item subject) {
        this.object = object;
        this.subject = subject;
    }

    public Item getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Combo combo = (Combo) o;
        return Objects.equals(object, combo.object) && Objects.equals(subject, combo.subject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(object, subject);
    }
}
