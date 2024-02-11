package main;

import basket.*;
import config.ConnectionManager;
import config.Services;
import config.UtilCLI;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import menu.MenuDao;
import menu.MenuService;
import menu.PrintMenuDto;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final UtilCLI utilCLI = new UtilCLI();
        final EntityManager entityManager = ConnectionManager.getEntityManager();
        entityManager.close();

        utilCLI.printFirstStepCLI();
    }
}
