import model.EmployeFixe;
import model.InvalidWorkDataException;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        try {
            EmployeFixe e1 = new EmployeFixe(
                    1,
                    "Ali",
                    "ali@gmail.com",
                    "IT",
                    LocalDate.of(2020, 1, 10),
                    5000,
                    1000
            );

            System.out.println("Nom : " + e1.getNom());
            System.out.println("Salaire brut : " + e1.calculerSalaireBrut());
            System.out.println("Prime anciennete : " + e1.calculerPrimeAnciennete());
            System.out.println("Net a payer : " + e1.calculerNetAPayer());

        } catch (InvalidWorkDataException e) {
            System.out.println("Erreur : " + e.getMessage());
        }
    }
}