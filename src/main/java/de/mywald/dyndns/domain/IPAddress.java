package de.mywald.dyndns.domain;

public class IPAddress {
    private String value;

    public IPAddress(String value) {
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

        IPAddress ipAddress = (IPAddress) o;

        return value.equals(ipAddress.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
