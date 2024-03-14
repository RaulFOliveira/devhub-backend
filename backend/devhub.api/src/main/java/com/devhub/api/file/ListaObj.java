package com.devhub.api.file;

public class ListaObj<T> {

    private T[] vetor;

    private int nroElem;

    public ListaObj(int tamanho) {
        this.vetor = (T[]) new Object[tamanho];
        this.nroElem = 0;
    }

    public void adiciona(T elemento) {
        if (vetor.length != nroElem) {
            vetor[nroElem] = elemento;
            nroElem++;
        } else {
            throw new IllegalStateException();
        }
    }

    public int busca(T elementoBuscado) {
        if (elementoBuscado == null) {
            return -1;
        }

        for (int i = 0; i < nroElem; i++) {
            if (vetor[i].equals(elementoBuscado)) {
                return i;
            }
        }
        return -1;
    }

    public boolean removePeloIndice(int indice) {
        if (indice >= 0 && indice < vetor.length) {
            for (int i = indice; i < vetor.length-1; i++) {
                vetor[i] = vetor[i+1];
            }
            nroElem--;
            return true;
        }
        return false;
    }

    public boolean removeElemento(T elementoARemover) {
        return removePeloIndice(busca(elementoARemover));
    }

    public int getTamanho() {
        return nroElem;
    }

    public T getElemento(int indice) {
        if (indice >= 0 && indice < nroElem) {
            return vetor[indice];
        }
        return null;
    }

    public void limpa() {
        nroElem = 0;
    }

    public void exibe() {
        for (int i = 0; i < nroElem; i++) {
            System.out.println(vetor[i]);
        }
    }

    public T[] getVetor() {
        return vetor;
    }

    public void substitui(int i, T valorNovo) {
        if (i >= 0 && i < nroElem) {
            vetor[i] = valorNovo;
        }
    }
}