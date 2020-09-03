package com.andersonoli;

import java.util.Scanner;

public class Main {
    private static final String[] pontuacoes = new String[12];

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        System.out.println("Verifique abaixo as maneiras de entrar com os dados do jogo:\n");

        System.out.printf("%-12s- ", "0");
        System.out.println("Falta ou zero pinos derrubados");

        System.out.printf("%-12s- ", "x");
        System.out.println("Strike");

        System.out.printf("%-12s- ", "[número]/");
        System.out.println("Spare ( Ex.: 4/ = 'spare' )");

        System.out.printf("%-12s- ", "[número]-");
        System.out.println("Arremesso com perca ( Ex.: 9- )");

        for (int i = 0; i < 10; i++) {
            System.out.println("\nRodada #" + (i + 1));
            System.out.print(":: ");
            pontuacoes[i] = scan.next();

            // Na 10° rodada, em caso de 'spare' ou 'strike'
            // Direito a uma ou mais duas jogadas
            if(i == 9 && (eStrike(i) || eSpare(i))){
                calcularBonus();
            }
        }

        System.out.println("\nPontuação total: " + calcularPontuacao());
    }

    // Retorna a pontuação total do jogo de acordo com as regras
    public static int calcularPontuacao(){
        int totalPontos = 0;

        for (int i = 0; i < 10; i++) {
            if(eStrike(i)){
                if(eSpare(i+1)){
                    totalPontos += 20;
                } else if(eStrike(i+1) && eStrike(i+2)){
                    totalPontos += 30;
                } else {
                    totalPontos += 10 + valorRodada(i + 1);
                }
            } else if(eSpare(i)){
                totalPontos += 10 + valorJogada(i + 1);
            } else {
                totalPontos += valorRodada(i);
            }
        }

        return totalPontos;
    }

    // Obs.: os índices 10, 11 do array 'posicoes'
    // estão reservados a pontuação bônus
    public static void calcularBonus(){
        Scanner scan = new Scanner(System.in);

        for (int i = 10; i < 12; i++) {
            System.out.print("-- Rodada bônus: ");
            pontuacoes[i] = scan.next();

            if(!pontuacoes[i].equals("x")){
                break;
            }
        }
    }

    // verifica se a jogada é um 'spare'
    public static boolean eSpare(int posicao){
        return pontuacoes[posicao].contains("/");
    }

    // verifica se a jogada é um 'strike'
    public static boolean eStrike(int posicao){
        return pontuacoes[posicao].contains("x");
    }

    // retorna o valor de uma 'jogada'
    public static int valorJogada(int posicao){
        String rodada = pontuacoes[posicao];

        try {
            return Integer.parseInt(rodada.substring(0, 1));
        } catch (Exception e){
            if(eStrike(posicao)) return 10;
        }

        return 0;
    }

    // retorna o valor de 'frames normais'
    public static int valorRodada(int posicao){
        String rodada = pontuacoes[posicao];

        if(rodada.contains("/") || rodada.contains("x")){
            return 10;
        } else if(rodada.contains("-")) {
            return Integer.parseInt(rodada.substring(0, 1));
        } else {
            try {
                return Integer.parseInt(rodada.substring(0, 1)) + Integer.parseInt(rodada.substring(1, 2));
            } catch (Exception e) {
                return Integer.parseInt(rodada.substring(0, 1));
            }
        }
    }
}
