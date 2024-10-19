import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void calcularMedia() {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Double> notas = new ArrayList<>();
        double nota;
        double soma = 0;
        String continuar;

        // Loop para coletar notas do aluno até o professor decidir parar
        do {
            System.out.print("Digite a nota do aluno: ");
            nota = scanner.nextDouble();
            notas.add(nota);
            soma += nota;

            System.out.print("Deseja adicionar outra nota? (S/N): ");
            continuar = scanner.next();
        } while (continuar.equalsIgnoreCase("S"));

        // Calcula a média das notas
        double media = soma / notas.size();

        // Exibe a média e verifica se o aluno foi aprovado
        System.out.printf("Média do aluno: %.2f%n", media);
        if (media >= 6) {
            System.out.println("Aluno aprovado!");
        } else {
            System.out.println("Aluno reprovado.");
        }

        scanner.close();
    }

    public static void main(String[] args) {
        calcularMedia();
    }
}
