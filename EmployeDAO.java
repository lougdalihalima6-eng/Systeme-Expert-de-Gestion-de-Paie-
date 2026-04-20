import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmployeDAO {
    private final Connection connection;

    public EmployeDAO() throws SQLException {
        this.connection = DBConnection.getConnection();
    }

    public void save(Employe e) throws SQLException {
        String sql = "INSERT INTO employes (id, nom, email, departement, date_embauche, type, salaire_base, prime_performance, taux_horaire, heures_travaillees) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setInt(1, e.getId());
        ps.setString(2, e.getNom());
        ps.setString(3, e.getEmail());
        ps.setString(4, e.getDepartement());
        ps.setDate(5, Date.valueOf(e.getDateEmbauche()));

        if (e instanceof EmployeFixe) {
            EmployeFixe ef = (EmployeFixe) e;
            ps.setString(6, "fixe");
            ps.setDouble(7, ef.getSalaireBase());
            ps.setDouble(8, ef.getPrimePerformance());
            ps.setNull(9, java.sql.Types.DOUBLE);
            ps.setNull(10, java.sql.Types.INTEGER);
        } else if (e instanceof EmployeHoraire) {
            EmployeHoraire eh = (EmployeHoraire) e;
            ps.setString(6, "horaire");
            ps.setNull(7, java.sql.Types.DOUBLE);
            ps.setNull(8, java.sql.Types.DOUBLE);
            ps.setDouble(9, eh.getTauxHoraire());
            ps.setInt(10, eh.getHeuresTravaillees());
        }

        ps.executeUpdate();
        ps.close();
    }

    public List<Employe> findAll() throws SQLException, InvalidWorkDataException {
        List<Employe> employes = new ArrayList<>();
        String sql = "SELECT * FROM employes";

        PreparedStatement ps = connection.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            String type = rs.getString("type");

            if ("fixe".equalsIgnoreCase(type)) {
                EmployeFixe ef = new EmployeFixe(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("email"),
                        rs.getString("departement"),
                        rs.getDate("date_embauche").toLocalDate(),
                        rs.getDouble("salaire_base"),
                        rs.getDouble("prime_performance")
                );
                employes.add(ef);
            } else if ("horaire".equalsIgnoreCase(type)) {
                EmployeHoraire eh = new EmployeHoraire(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getString("email"),
                        rs.getString("departement"),
                        rs.getDate("date_embauche").toLocalDate(),
                        rs.getDouble("taux_horaire"),
                        rs.getInt("heures_travaillees")
                );
                employes.add(eh);
            }
        }

        rs.close();
        ps.close();
        return employes;
    }

    public Map<String, Double> getMasseSalarialeParDept() throws SQLException, InvalidWorkDataException {
        Map<String, Double> resultat = new HashMap<>();

        List<Employe> employes = findAll();

        for (Employe e : employes) {
            String dept = e.getDepartement();
            double net = e.calculerNetAPayer();
            resultat.put(dept, resultat.getOrDefault(dept, 0.0) + net);
        }

        return resultat;
    }

    public void augmenterSalaireBase(double pourcentage) throws SQLException {
        String sql = "UPDATE employes SET salaire_base = salaire_base + (salaire_base * ? / 100) WHERE type = 'fixe'";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setDouble(1, pourcentage);
        ps.executeUpdate();
        ps.close();
    }
}