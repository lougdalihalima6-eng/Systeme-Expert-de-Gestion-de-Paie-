import java.time.LocalDate;

public class EmployeHoraire extends Employe {
    private double tauxHoraire;
    private int heuresTravaillees;

    public EmployeHoraire(int id, String nom, String email, String departement, LocalDate dateEmbauche,
                          double tauxHoraire, int heuresTravaillees) throws InvalidWorkDataException {
        super(id, nom, email, departement, dateEmbauche);
        if (heuresTravaillees > 240) {
            throw new InvalidWorkDataException("Le nombre d'heures travaillees ne peut pas depasser 240.");
        }
        this.tauxHoraire = tauxHoraire;
        this.heuresTravaillees = heuresTravaillees;
    }

    @Override
    public double calculerSalaireBrut() {
        if (heuresTravaillees <= 180) {
            return tauxHoraire * heuresTravaillees;
        }

        int heuresNormales = 180;
        int heuresSupp = heuresTravaillees - 180;

        return (heuresNormales * tauxHoraire) + (heuresSupp * tauxHoraire * 1.25);
    }

    public double getTauxHoraire() {
        return tauxHoraire;
    }

    public int getHeuresTravaillees() {
        return heuresTravaillees;
    }
}