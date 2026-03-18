# Reboonté

Application Android de gestion de stock de médicaments développée en Kotlin, avec Jetpack Compose, Firebase et une architecture MVVM / Clean Architecture.

Projet réalisé dans un contexte de reprise d’un code existant ne respectant pas les standards de qualité.

![Kotlin](https://img.shields.io/badge/Kotlin-Android-blue) ![Compose](https://img.shields.io/badge/Jetpack%20Compose-UI-green) ![Firebase](https://img.shields.io/badge/Firebase-Backend-orange) ![Architecture](https://img.shields.io/badge/Architecture-MVVM%20%2B%20Clean-purple) ![CI/CD](https://img.shields.io/badge/CI%2FCD-GitHub%20Actions-black)

---

## Contexte

Ce projet s’inscrit dans un scénario professionnel au sein d’un groupe pharmaceutique.

Une première version de l’application existait, mais présentait plusieurs problèmes :

- code peu maintenable  
- absence d’architecture claire  
- non-respect des bonnes pratiques Android  
- difficulté d’évolution  

L’objectif était de reprendre ce projet et de le rendre exploitable dans un contexte professionnel.

---

## Objectifs

- Refactoriser le code existant  
- Mettre en place une architecture propre (MVVM + Clean Architecture)  
- Améliorer la qualité globale du projet  
- Ajouter des fonctionnalités manquantes  
- Mettre en place une intégration et livraison continue (CI/CD)  

---

## Fonctionnalités

- Création de compte utilisateur  
- Authentification  
- Gestion des rayons de stock  
- Gestion des médicaments  
- Suivi des quantités  
- Historique des modifications  

---

## Stack technique

- Kotlin  
- Jetpack Compose  
- MVVM  
- Clean Architecture  
- Hilt (Dependency Injection)  
- Firebase (Authentication, Firestore)  
- GitHub Actions (CI/CD)  
- Tests unitaires et instrumentés  

---

## Architecture

L’application suit une architecture MVVM associée à la Clean Architecture.

### Organisation

- View (Compose) : interface utilisateur  
- ViewModel : gestion de l’état  
- UseCases : logique métier  
- Repository : abstraction des données  
- Data sources : Firebase  

### Objectifs techniques

- séparation des responsabilités  
- code maintenable et évolutif  
- testabilité accrue  
- modularité  

---

## Améliorations apportées

- Refactorisation complète du projet  
- Mise en place d’une architecture claire  
- Nettoyage et structuration du code  
- Amélioration de la lisibilité et maintenabilité  
- Implémentation des bonnes pratiques Android  

---

## CI/CD

Mise en place de GitHub Actions :

- exécution des tests unitaires  
- exécution des tests instrumentés  
- analyse du code  
- génération d’un APK  
- distribution via Firebase App Distribution  

---

## Difficultés rencontrées

- Reprise d’un code existant non structuré  
- Gestion du temps de développement  
- Mise en place de la CI/CD  

### Solutions

- documentation Android officielle  
- accompagnement mentor  
- utilisation d’outils d’analyse et debug  

---

## Lancer le projet

```bash
git clone https://github.com/HRDF88/reboonte.git

```

## Auteur

Julien SEGUIN
Développeur Android – Kotlin & Jetpack Compose 

Email : julien.seguin@hotmail.fr 
GitHub : [HRDF88](https://github.com/HRDF88) 

---

Ce projet illustre ma capacité à reprendre un code existant, améliorer sa qualité et mettre en place une architecture moderne adaptée à un contexte professionnel.
