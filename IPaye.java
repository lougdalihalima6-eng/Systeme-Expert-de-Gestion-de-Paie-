public interface IPaye {
    double calculerSalaireBrut();

    int getAnciennete();

    default double calculerPrimeAnciennete() {
        double brut = calculerSalaireBrut();
        int anciennete = getAnciennete();

        if (anciennete < 2) {
            return 0;
        } else if (anciennete <= 5) {
            return brut * 0.05;
        } else {
            return brut * 0.10;
        }
    }

    default double calculerCharges(double taux) {
        return (calculerSalaireBrut() + calculerPrimeAnciennete()) * taux;
    }

    default double calculerCharges() {
        return calculerCharges(0.20);
    }

    default double calculerNetAPayer() {
        return (calculerSalaireBrut() + calculerPrimeAnciennete()) - calculerCharges();
    }
}