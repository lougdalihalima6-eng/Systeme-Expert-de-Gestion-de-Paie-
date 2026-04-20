import java.time.LocalDate;

public class EmployeFixe extends Employe {
    private double salaireBase;
    private double primePerformance;

    public EmployeFixe(int id, String nom, String email, String departement, LocalDate dateEmbauche,
                       double salaireBase, double primePerformance) throws InvalidWorkDataException {
        super(id, nom, email, departement, dateEmbauche);
        if (salaireBase < 3000) {
            throw new InvalidWorkDataException("Le salaire de base ne peut pas etre inferieur a 3000 DH.");
        }
        this.salaireBase = salaireBase;
        this.primePerformance = primePerformance;
    }

    @Override
    public double calculerSalaireBrut() {
        return salaireBase + primePerformance;
    }

    public double getSalaireBase() {
        return salaireBase;
    }

    public double getPrimePerformance() {
        return primePerformance;
    }
}