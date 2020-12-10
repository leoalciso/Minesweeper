package controller;

/**
 * Stores pair of generic objects. Works with two different types of objects.
 * @param <A>
 * @param <B>
 */

public class Pair<A, B> {
    private A first;
    private B second;

    /**
     * calls super and sets pair values. Part of composite design pattern.
     * @param first
     * @param second
     */

    public Pair(A first, B second) {
        super();
        this.first = first;
        this.second = second;
    }

    /**
     * gets first pair value
     * @return first
     */

    public A getFirst() {
        return first;
    }

    /**
     * sets first pair value
     */

    public void setFirst(A first) {
        this.first = first;
    }

    /**
     * gets second pair value
     * @return secoond
     */

    public B getSecond() {
        return second;
    }

    /**
     * sets second pair value
     */

    public void setSecond(B second) {
        this.second = second;
    }
}