package com.devhub.api.file;

public class PilhaObj<T> {

    // 01) Atributos
    private T[] pilha;
    private int topo;

    // 02) Construtor
    public PilhaObj(int capacidade) {
        this.pilha = (T[]) new Object[capacidade];
        this.topo = -1;
    }

    // 03) MÃ©todo isEmpty
    public Boolean isEmpty() { return topo == -1; }

    // 04) MÃ©todo isFull
    public Boolean isFull() {
        return pilha.length-1 == topo;
    }

    // 05) MÃ©todo push
    public void push(T info) {
        if (pilha.length == topo+1) {
            throw new IllegalStateException("Pilha cheia!");
        }
        pilha[++topo] = info;
    }

    // 06) MÃ©todo pop
    public T pop() {
        return pilha[topo--];
    }

    // 07) MÃ©todo peek
    public T peek() {
        return !isEmpty() ? pilha[topo] : null;
    }

    // 08) MÃ©todo exibe
    public void exibe() {
        for (int i = topo; i >= 0; i--) {
            System.out.println(pilha[i]);
        }
    }

    //Getters & Setters (manter)
    public int getTopo() {
        return topo;
    }

    public int getTamanho() {
        return pilha.length;
    }

}