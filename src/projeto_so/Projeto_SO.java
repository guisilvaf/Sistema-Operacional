package projeto_so;

import java.util.concurrent.Semaphore;
    
    public class Projeto_SO extends Thread {
    private int nThread;
    private Semaphore semaforo;
 
    public Projeto_SO(int id, Semaphore semaphore) {
        this.nThread = id;
        this.semaforo = semaphore;
    }
    
    private void processar() {
        try {
            System.out.println("Thread " + nThread + " está processando.");
            Thread.sleep((long) (Math.random() * 100000));
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void RegiaoNaoCritica() {
            System.out.println("Thread " + nThread + " não está em uma região crítica.");
            processar();       
    }
    private void RegiaoCritica() {
            System.out.println("Thread " + nThread + " está entrando na região crítica.");
            processar();
            System.out.println("Thread " + nThread + " está saindo da região crítica.");
    }    
    
    public void run() {
    RegiaoNaoCritica();
        try {
            semaforo.acquire();
            RegiaoCritica();
        } catch (InterruptedException e) {
        } finally {
            semaforo.release();
        }
    }
        
    public static void main(String[] args) {
        
    int numeroDePermicoes = 3;
    int numeroDeProcessos = 10;
    Semaphore semaphore = new Semaphore(numeroDePermicoes);
    Projeto_SO[] processos = new Projeto_SO[numeroDeProcessos];
        for (int i = 0; i < numeroDeProcessos; i++) {
            processos[i] = new Projeto_SO(i, semaphore);
            processos[i].start();
        }
    }
}