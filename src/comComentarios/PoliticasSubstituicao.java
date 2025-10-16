package comComentarios;

import java.util.*;

public class PoliticasSubstituicao {

    // Simula FIFO: fila que remove o mais antigo
    public static List<Integer> fifo(List<Integer> sequencia, int quadros) {
        LinkedList<Integer> memoria = new LinkedList<>();
        int maisAntigo = 0;
        for (int pagina : sequencia) {
            if (memoria.contains(pagina)) {
                // já está na memória -> nenhum page fault
                continue;
            }
            if (memoria.size() < quadros) {
                memoria.add(pagina);
            } else {                memoria.set(maisAntigo, pagina); //substitui o mais antigo pela nova pagina

                maisAntigo = maisAntigo < quadros-1 ? maisAntigo+1 : 0;
            }
        }
        return new ArrayList<>(memoria);
    }

    // Simula LRU: substitui a página menos recentemente usada (último elemento)
    public static List<Integer> lru(List<Integer> sequencia, int quadros) {
        LinkedList<Integer> memoria = new LinkedList<>(); //Lista para guardar o valor dos quadros posicionados
        LinkedList<Integer> maisRecentes = new LinkedList<>(); //Lista apra guardar os acessos mais recentes
        int index = 0;
        for (int pagina : sequencia) {
            if (memoria.contains(pagina)) {
                // ao acessar, move para o fim (mais recentemente usado)
                maisRecentes.removeFirstOccurrence(pagina);
                maisRecentes.addLast(pagina);
            } else {
                if (memoria.size() < quadros) {
                    memoria.addLast(pagina);
                    maisRecentes.addLast(pagina);
                } else {
                    int valorSubstituido = maisRecentes.get(maisRecentes.size() - quadros);  //pega o valor do item menos recente acessado
                    memoria.replaceAll(valor -> {
                        if (valor.equals(valorSubstituido)) {
                            return pagina;
                        }
                        return valor;
                    });
                    maisRecentes.removeFirstOccurrence(pagina);
                    maisRecentes.addLast(pagina);
                }
            }
            index++;
        }
        return new ArrayList<>(memoria);
    }

    // Simula MRU: substitui a página mais recentemente usada (último elemento)
    public static List<Integer> mru(List<Integer> sequencia, int quadros) {
        LinkedList<Integer> memoria = new LinkedList<>();
        LinkedList<Integer> maisRecentes = new LinkedList<>();
        int index = 0;
        for (int pagina : sequencia) {
            if (memoria.contains(pagina)) {
                // ao acessar, move para o fim (mais recentemente usado)
                maisRecentes.removeFirstOccurrence(pagina);
                maisRecentes.addLast(pagina);
            } else {
                if (memoria.size() < quadros) {
                    memoria.addLast(pagina);
                    maisRecentes.addLast(pagina);
                } else {
                    int valorSubstituido = maisRecentes.getLast(); //pega o valor do item mais recente acessado
                    memoria.replaceAll(valor -> {
                        if (valor.equals(valorSubstituido)) {
                            return pagina;
                        }
                        return valor;
                    });
                    maisRecentes.removeFirstOccurrence(pagina);
                    maisRecentes.addLast(pagina);
                }
            }
            index++;
        }
        return new ArrayList<>(memoria);
    }

    // Metodo para imprimir estado final com índices de quadro
    public static void imprimirEstadoFinal(String nomeAlgoritmo, List<Integer> listaFinal, int paginaProcurada) {
        System.out.println("== " + nomeAlgoritmo + " ==");
        for (int i = 0; i < listaFinal.size(); i++) {
            System.out.println("Quadro " + (i+1) + ": " + listaFinal.get(i));
        }
        if (listaFinal.contains(paginaProcurada)) {
            int indice = listaFinal.indexOf(paginaProcurada) + 1;
            System.out.println("A página " + paginaProcurada + " está no quadro " + indice + ".");
        } else {
            System.out.println("A página " + paginaProcurada + " NÃO está na memória (com essa política).");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        int quadros = 8;

        // sequências
        List<Integer> seqA = Arrays.asList(4,3,25,8,19,6,25,8,16,35,45,22,8,3,16,25,7);
        List<Integer> seqB = Arrays.asList(4,5,7,9,46,45,14,4,64,7,65,2,1,6,8,45,14,11);
        List<Integer> seqC = Arrays.asList(4,6,7,8,1,6,10,15,16,4,2,1,4,6,12,15,16,11);

        // Resultados, a) página 7 na seqA, b) página 11 na seqB, c) página 11 na seqC
        System.out.println("Sequência A (procurar página 7):");
        imprimirEstadoFinal("FIFO", fifo(seqA, quadros), 7);
        imprimirEstadoFinal("LRU", lru(seqA, quadros), 7);
        imprimirEstadoFinal("MRU", mru(seqA, quadros), 7);

        System.out.println("Sequência B (procurar página 11):");
        imprimirEstadoFinal("FIFO", fifo(seqB, quadros), 11);
        imprimirEstadoFinal("LRU", lru(seqB, quadros), 11);
        imprimirEstadoFinal("MRU", mru(seqB, quadros), 11);

        System.out.println("Sequência C (procurar página 11):");
        imprimirEstadoFinal("FIFO", fifo(seqC, quadros), 11);
        imprimirEstadoFinal("LRU", lru(seqC, quadros), 11);
        imprimirEstadoFinal("MRU", mru(seqC, quadros), 11);
    }
}
