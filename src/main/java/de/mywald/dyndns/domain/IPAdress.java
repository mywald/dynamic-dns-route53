package de.mywald.dyndns.domain;

public class IPAdress {
    private String value;

    public IPAdress(String value) {
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

        IPAdress ipAdress = (IPAdress) o;

        return value.equals(ipAdress.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
