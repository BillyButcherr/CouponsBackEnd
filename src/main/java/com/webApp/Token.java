package com.webApp;

import java.util.Objects;

public class Token {
    private String data;
    public Token(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Token token = (Token) o;
        return Objects.equals(data, token.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }
}
