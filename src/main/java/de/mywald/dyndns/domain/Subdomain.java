package de.mywald.dyndns.domain;

public class Subdomain {
    private String value;

    public Subdomain(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Subdomain subdomain = (Subdomain) o;

        return value.equals(subdomain.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
