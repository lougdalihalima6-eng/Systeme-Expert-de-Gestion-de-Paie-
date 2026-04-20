CREATE DATABASE gestion_paie;
USE gestion_paie;

CREATE TABLE employes (
    id INT PRIMARY KEY,
    nom VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    departement VARCHAR(100) NOT NULL,
    date_embauche DATE NOT NULL,
    type VARCHAR(20) NOT NULL,
    salaire_base DOUBLE,
    prime_performance DOUBLE,
    taux_horaire DOUBLE,
    heures_travaillees INT
);