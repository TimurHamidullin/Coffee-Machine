package machine;

import java.util.InputMismatchException;
import java.util.Scanner;

public class CoffeeMachine {
    private static class Resources {
        private static int water_InML = 400;
        private static int milk_InML = 540;
        private static int coffeeBeans_InGrams = 120;
        private static int cupsDisposable = 9;
        private static int money_In$ = 550;
    }

    private static class Coffee {
        int water_InML;
        int milk_InML;
        int coffeeBeans_InGrams;
        int price_In$;

        private Coffee(int water_InML, int milk_InML, int coffeeBeans_InGrams, int price_In$)
        {
            this.water_InML = water_InML;
            this.milk_InML = milk_InML;
            this.coffeeBeans_InGrams = coffeeBeans_InGrams;
            this.price_In$ = price_In$;
        }
    }

    /* Coffee ingredients can be changed */
    private static class Espresso {
        static final int water_InML = 250;
        static final int milk_InML = 0;
        static final int coffeeBeans_InGrams = 16;
        static final int price_In$ = 4;
    }

    private static class Latte {
        static final int water_InML = 350;
        static final int milk_InML = 75;
        static final int coffeeBeans_InGrams = 20;
        static final int price_In$ = 7;
    }

    private static class Cappuccino {
        static final int water_InML = 200;
        static final int milk_InML = 100;
        static final int coffeeBeans_InGrams = 12;
        static final int price_In$ = 6;
    }

    public static void main(String[] args) throws Exception {
        var sc = new Scanner(System.in);

        executeCommand(sc);
    }

    private static void executeCommand(Scanner sc) throws Exception {
        System.out.println("Write action (buy, fill, take, remaining, exit): ");
        String action = sc.next();

        switch (action) {
            case "buy":
                giveCoffeeToUser(sc);
                executeCommand(sc);
                break;

            case "fill":
                fillCoffeeMachine(sc);
                executeCommand(sc);
                break;

            case "take":
                giveMoney();
                executeCommand(sc);
                break;

            case "remaining":
                displayCoffeeMachineResources();
                executeCommand(sc);
                break;

            case "exit":
                sc.close();
                break;

            default:
                System.out.println("Invalid input. Try again.\n");
                executeCommand(sc);
        }
    }

    private static void giveCoffeeToUser(Scanner sc) throws Exception {
        System.out.println("What do you want to buy? 1 - espresso, 2 - latte, 3 - cappuccino, back - to main menu:");
        var action = sc.next();

        switch (action) {
            case "1":
                makeCoffee(new Coffee(
                        Espresso.water_InML,
                        Espresso.milk_InML,
                        Espresso.coffeeBeans_InGrams,
                        Espresso.price_In$));
                break;

            case "2":
                makeCoffee(new Coffee(
                        Latte.water_InML,
                        Latte.milk_InML,
                        Latte.coffeeBeans_InGrams,
                        Latte.price_In$));
                break;

            case "3":
                makeCoffee(new Coffee(
                        Cappuccino.water_InML,
                        Cappuccino.milk_InML,
                        Cappuccino.coffeeBeans_InGrams,
                        Cappuccino.price_In$));
                break;

            case "back":
                break;

            default:
                System.out.println("Invalid input. Try again.\n");
                giveCoffeeToUser(sc);
        }
    }

    private static void makeCoffee(Coffee selectedCoffee) throws Exception {
        switch (checkIngredients(selectedCoffee)) {

            case "just enough":
                System.out.println("I have enough resources, making you a coffee!\n");
                Resources.water_InML -= selectedCoffee.water_InML;
                Resources.milk_InML -= selectedCoffee.milk_InML;
                Resources.coffeeBeans_InGrams -= selectedCoffee.coffeeBeans_InGrams;
                Resources.cupsDisposable -= 1;
                Resources.money_In$ += selectedCoffee.price_In$;
                break;

            case "not enough coffee beans":
                System.out.println("Sorry, not enough coffee beans!\n");
                break;

            case "not enough water":
                System.out.println("Sorry, not enough water!\n");
                break;

            case "not enough milk":
                System.out.println("Sorry, not enough milk!\n");
                break;

            case "not enough cups":
                System.out.println("Sorry, not enough cups!\n");
                break;

            default:
                throw new Exception("Error: unexpected state.\n");
        }
    }

    private static String checkIngredients(Coffee coffee) {
        if (Resources.water_InML < coffee.water_InML) {
            return "not enough water";
        } else if (Resources.milk_InML < coffee.milk_InML) {
            return "not enough milk";
        } else if (Resources.coffeeBeans_InGrams < coffee.coffeeBeans_InGrams) {
            return "not enough coffee beans";
        } else if (Resources.cupsDisposable < 1) {
            return "not enough cups";
        } else return "just enough";
    }

    private static void fillCoffeeMachine(Scanner sc) {
        System.out.println("Write how many ml of water do you want to add:");
        try {
            Resources.water_InML += sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Error: invalid value of water.");
        }

        System.out.println("Write how many ml of milk do you want to add:");
        try {
            Resources.milk_InML += sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Error: invalid value of milk.");
        }

        System.out.println("Write how many grams of coffee beans do you want to add:");
        try {
            Resources.coffeeBeans_InGrams += sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Error: invalid value of coffee beans.");
        }

        System.out.println("Write how many disposable cups do you want to add:");
        try {
            Resources.cupsDisposable += sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Error: invalid value of disposable cups.");
        }
    }

    private static void giveMoney() {
        System.out.println(String.format("I gave you $%s\n", Resources.money_In$));
        Resources.money_In$ = 0;
    }

    private static void displayCoffeeMachineResources() {
        System.out.println("\nThe coffee machine has:");
        System.out.println(String.format("%s of water", Resources.water_InML));
        System.out.println(String.format("%s of milk", Resources.milk_InML));
        System.out.println(String.format("%s of coffee beans", Resources.coffeeBeans_InGrams));
        System.out.println(String.format("%s of disposable cups", Resources.cupsDisposable));
        System.out.println(String.format("$%s of money\n", Resources.money_In$));
    }
}