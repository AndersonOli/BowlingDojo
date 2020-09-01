package com.andersonoli;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Scanner;

public class Main {
    private static final int[] pontuacoes = new int[12];
    private static final boolean[] posicaoStrike = new boolean[12];

    public static void main(String[] args) {
        String nomeJogador;
        String entradaPontuacoes;

        int pontuacaoRodada;
        boolean ocorreuSpare = false;

        Scanner scan = new Scanner(System.in);

        System.out.println("-- Seja bem vindo(a) ao Boliche Counter --\n");
        System.out.print("Insira o nome do jogador: ");
        nomeJogador = scan.nextLine();

        System.out.println("\n-- Ok! Informe abaixo, a pontuação do jogador " + nomeJogador + " na rodada --");

        for (int i = 0; i < 10; i++) { // loop de 'frames'
            pontuacaoRodada = 0;
            System.out.println("Rodadada #" + (i+1));

            for (int j = 0; j < 2; j++) { // loop de arremessos
                System.out.print("-- Arremesso " + (j+1) + ": ");
                entradaPontuacoes = scan.next();

                if(entradaPontuacoes.contains("x")){
                    // guarda a posição dos 'strikes' para o cálculo
                    posicaoStrike[i] = true;

                    // caso um 'spare' tenha ocorrido na rodada anteior
                    // adicionar a pontuação da proxima jogada, neste caso foi um 'srike', então +10
                    if(ocorreuSpare){
                        pontuacoes[i - 1] += 10; // +10 para a jogada anterior
                        ocorreuSpare = false;
                    } else if(i == 9){
                        // 10° rodada, tem-se um bônus por ter feito 'strike'
                        // podendo jogar mais uma ou duas vezes
                        calcularBonus();
                    }

                    pontuacaoRodada = 10;
                    break;
                } else if(j == 1 && entradaPontuacoes.contains("/")){
                    // em caso de 'spare' e caso seja a última rodada,
                    // tem-se o bônus de mais uma ou duas jogadas
                    if(i == 9){
                        pontuacaoRodada = 10;
                        calcularBonus();
                        break;
                    }

                    // em caso de 'spare', a rodada vale 10 pontos
                    // mais os pontos da proxima jogada
                    pontuacaoRodada = 10;
                    ocorreuSpare = true;
                } else {
                    try {
                        if(ocorreuSpare){
                            // +quantidade de pontos feita na jogada subsequente
                            // para a rodada anterior
                            pontuacoes[i - 1] += Integer.parseInt(entradaPontuacoes);
                        }

                        pontuacaoRodada += Integer.parseInt(entradaPontuacoes);
                    } catch (NumberFormatException e) {
                        break;
                    }

                    ocorreuSpare = false;
                }
            }

            pontuacoes[i] = pontuacaoRodada;
        }

        System.out.println("\nO jogador " + nomeJogador + " pontuou: " + calcularPontuacao(pontuacoes));
        System.out.println(Arrays.toString(pontuacoes));
        System.out.println(Arrays.toString(posicaoStrike));
    }

    public static int calcularPontuacao(int[] pontuacao){
        int somaPontos = 0;

        for (int i = 0; i < pontuacao.length; i++) {
            if(posicaoStrike[i] && i < 9){
                // bonus por strike
                // 10 + bonus das duas proximas jogadas
                somaPontos += 10 + (pontuacao[i+1] + pontuacao[i+2]);
                continue;
            }

            somaPontos += pontuacao[i];
        }

        return somaPontos;
    }

    public static void calcularBonus(){
        int posicao = 10;
        Scanner scan = new Scanner(System.in);

        while (posicao != 12){
            System.out.print("-- Pontuação na rodada bônus: ");
            String entrada = scan.next();

            if(entrada.equals("x")){
                pontuacoes[posicao++] = 10;
            } else {
                pontuacoes[posicao] = Integer.parseInt(entrada);
                break;
            }
        }
    }
}
