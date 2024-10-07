package com.example.myapplication;


import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/*
public class GeneticAlgorithm {
    private List<Message> population;
    private final int populationSize = 10; // Define your population size
    private final Random random = new Random();

    public GeneticAlgorithm() {
        population = new ArrayList<>();
        Log.d("GA","Genetic algorithm initialized");
    }

    // Initialize the population
    public void initializePopulation(List<Message> requests) {
        // Randomly select from the requests
        for (int i = 0; i < populationSize && i < requests.size(); i++) {
            int randomIndex = random.nextInt(requests.size());
            population.add(requests.get(randomIndex));
        }
        Log.d("GA","Population initialized with size:"+population.size());
    }

    // Run the genetic algorithm
    public void runAlgorithm() {
        // Define the number of generations to run
        int generations = 100;
        Log.d("ga","starting genetic for"+generations+"generations");

        for (int i = 0; i < generations; i++) {
            Log.d("Ga","Starting Generation"+(i+1));
            List<Message> newPopulation = new ArrayList<>();

            // Selection, Crossover, and Mutation
            for (int j = 0; j < populationSize; j++) {
                Message parent1 = selectParent();
                Message parent2 = selectParent();
                Log.d("GA", "Selected parents: Parent1 fitness = " + calculateFitness(parent1) +
                        ", Parent2 fitness = " + calculateFitness(parent2));

                Message child = crossover(parent1, parent2);
                mutate(child);
                assignIncentiveBasedOnTime(child);
                newPopulation.add(child);
                Log.d("GA", "Created child with fitness: " + calculateFitness(child));
            }
            population = newPopulation; // Update population for the next generation
            Log.d("GA", "Generation " + (i + 1) + " completed.");
        }
        Log.d("GA", "Genetic algorithm completed.");
    }

    // Select parent based on fitness criteria (lower incentive and time taken are better)
    private Message selectParent() {
        double bestFitness = Double.MAX_VALUE;
        Message bestCandidate = null;

        // Use tournament selection for better diversity
        for (int i = 0; i < 3; i++) { // Tournament size of 3
            Message candidate = population.get(random.nextInt(population.size()));
            double fitness = calculateFitness(candidate);
            if (fitness < bestFitness) {
                bestFitness = fitness;
                bestCandidate = candidate;
            }
        }
        Log.d("GA", "Selected parent with fitness: " + bestFitness);
        return bestCandidate;
    }

    // Crossover two parents to create a child
    private Message crossover(Message parent1, Message parent2) {
        // Create a new message by combining aspects of the parents
        String request = parent1.getRequest(); // Keep the request from one parent
        String deadline = parent1.getDeadline(); // Keep the deadline from one parent
        //String incentive = parent1.getIncentive();
        // Randomly choose incentive and time taken from either parent
        String incentive = random.nextDouble() < 0.5 ? parent1.getIncentive() : parent2.getIncentive();
        String timeTaken = random.nextDouble() < 0.5 ? parent1.getTimeTaken() : parent2.getTimeTaken();

        Message child = new Message(request, deadline, incentive, timeTaken);
        Log.d("GA", "Crossover created a child with incentive: " + incentive +
                ", time taken: " + timeTaken);
        return child;
    }

    // Mutate a message by randomly changing its properties
    private void mutate(Message message) {
        if (random.nextDouble() < 0.1) { // 10% chance to mutate
            // Randomly change the incentive or time taken
            //
            if (random.nextBoolean()) {
                // Set new incentive value randomly for demonstration
                message.setIncentive(String.valueOf(random.nextInt(100000))); // Random incentive value between 0-99
                Log.d("GA", "Mutated incentive to: " + message.getIncentive());

            } else {
                // Set new time taken value randomly for demonstration
                message.setTimeTaken(String.valueOf(random.nextInt(100))); // Random time value between 0-99
                Log.d("GA", "Mutated time taken to: " + message.getTimeTaken());

            }
        }
    }
    // Custom logic: Assign the incoming request's incentive to the person with the least time
    private void assignIncentiveBasedOnTime(Message message) {
        // Parse the time taken for the current message
        double minTime = Double.parseDouble(message.getTimeTaken());

        // Iterate through the population to find the individual with the least time taken
        Message individualWithLeastTime = null;
        for (Message individual : population) {
            double individualTime = Double.parseDouble(individual.getTimeTaken());
            if (individualWithLeastTime == null || individualTime < minTime) {
                minTime = individualTime;
                individualWithLeastTime = individual; // Store the individual with the least time
            }
        }

        // If we found an individual with the least time, assign the current message's incentive to them
        if (individualWithLeastTime != null) {
            Log.d("GA", "Assigning incentive: " + message.getIncentive() +
                    " to individual with least time: " + individualWithLeastTime.getTimeTaken());

            individualWithLeastTime.setIncentive(message.getIncentive()); // Set the incentive from the message
        } else {
            Log.d("GA", "No valid individual found to assign the incentive.");
        }
    }


    // Calculate fitness based on incentive and time taken
    private double calculateFitness(Message message) {
        // Assuming lower values for incentive and time taken are better
        double incentiveValue = Double.parseDouble(message.getIncentive());
        double timeValue = Double.parseDouble(message.getTimeTaken());
        double fitness = incentiveValue + timeValue; // You can adjust the weights here if necessary
        Log.d("GA", "Calculated fitness for message: " + fitness);
        return fitness;
    }

    // Send messages after processing
    public void sendMessages(MainActivity mainActivity) {
        for (Message message : population) {
            String fullMessage = "Assigned Work: " + message.getRequest() +
                    "\nDeadline: " + message.getDeadline() +
                    "\nIncentive: " + message.getIncentive() +
                    "\nTime Taken: " + message.getTimeTaken();

            String phoneNumber = "Receiver's Phone Number";  // You need to define how to get phone numbers

            // Send the message using MainActivity's sendMsg method
            mainActivity.sendMsg(phoneNumber, fullMessage);
            Log.d("GA", "Sent message to " + phoneNumber + ": " + fullMessage);

        }
    }

}*/
public class GeneticAlgorithm {
    private List<Message> population;
    private final int populationSize = 10; // Define your population size
    private final Random random = new Random();

    public GeneticAlgorithm() {
        population = new ArrayList<>();
        Log.d("GA", "Genetic algorithm initialized");
    }

    // Initialize the population
    public void initializePopulation(List<Message> requests) {
        // Randomly select from the requests
        for (int i = 0; i < populationSize && i < requests.size(); i++) {
            int randomIndex = random.nextInt(requests.size());
            population.add(requests.get(randomIndex));
        }
        Log.d("GA", "Population initialized with size:" + population.size());
    }

    // Run the genetic algorithm
    public void runAlgorithm() {
        // Define the number of generations to run
        int generations = 100;
        Log.d("GA", "Starting genetic for " + generations + " generations");

        for (int i = 0; i < generations; i++) {
            Log.d("GA", "Starting Generation " + (i + 1));
            List<Message> newPopulation = new ArrayList<>();

            // Selection, Crossover, and Mutation
            for (int j = 0; j < populationSize; j++) {
                Message parent1 = selectParent();
                Message parent2 = selectParent();
                Log.d("GA", "Selected parents: Parent1 fitness = " + calculateFitness(parent1) +
                        ", Parent2 fitness = " + calculateFitness(parent2));

                Message child = crossover(parent1, parent2);
                // No mutation needed for incentive or time
                assignMinTimeAndIncentive(child); // Assign based on minimum time
                newPopulation.add(child);
                Log.d("GA", "Created child with fitness: " + calculateFitness(child));
            }
            population = newPopulation; // Update population for the next generation
            Log.d("GA", "Generation " + (i + 1) + " completed.");
        }
        Log.d("GA", "Genetic algorithm completed.");
    }

    // Select parent based on fitness criteria (lower incentive and time taken are better)
    private Message selectParent() {
        double bestFitness = Double.MAX_VALUE;
        Message bestCandidate = null;

        // Use tournament selection for better diversity
        for (int i = 0; i < 3; i++) { // Tournament size of 3
            Message candidate = population.get(random.nextInt(population.size()));
            double fitness = calculateFitness(candidate);
            if (fitness < bestFitness) {
                bestFitness = fitness;
                bestCandidate = candidate;
            }
        }
        Log.d("GA", "Selected parent with fitness: " + bestFitness);
        return bestCandidate;
    }

    // Crossover two parents to create a child (no randomization of incentive/time)
    private Message crossover(Message parent1, Message parent2) {
        // Create a new message by combining aspects of the parents
        String request = parent1.getRequest(); // Keep the request from one parent
        String deadline = parent1.getDeadline(); // Keep the deadline from one parent
        // Directly take incentive and time from one parent (no randomization)
        String incentive = parent1.getIncentive();
        String timeTaken = parent1.getTimeTaken();

        Message child = new Message(request, deadline, incentive, timeTaken);
        Log.d("GA", "Crossover created a child with incentive: " + incentive +
                ", time taken: " + timeTaken);
        return child;
    }

    // Custom logic: Assign the minimum time and its corresponding incentive
    private void assignMinTimeAndIncentive(Message child) {
        // Find the individual with the minimum time taken
        Message individualWithLeastTime = null;
        double minTime = Double.MAX_VALUE;

        // Iterate through the population to find the individual with the least time
        for (Message individual : population) {
            double individualTime = Double.parseDouble(individual.getTimeTaken());
            if (individualTime < minTime) {
                minTime = individualTime;
                individualWithLeastTime = individual; // Store the individual with the least time
            }
        }

        // If we found an individual with the least time, assign their time and incentive to the child
        if (individualWithLeastTime != null) {
            child.setTimeTaken(individualWithLeastTime.getTimeTaken());
            child.setIncentive(individualWithLeastTime.getIncentive());
            Log.d("GA", "Assigned incentive: " + individualWithLeastTime.getIncentive() +
                    " and time taken: " + individualWithLeastTime.getTimeTaken() +
                    " to the child.");
        } else {
            Log.d("GA", "No valid individual found to assign incentive and time.");
        }
    }

    // Calculate fitness based on incentive and time taken
    private double calculateFitness(Message message) {
        // Assuming lower values for incentive and time taken are better
        double incentiveValue = Double.parseDouble(message.getIncentive());
        double timeValue = Double.parseDouble(message.getTimeTaken());
        double fitness = incentiveValue + timeValue; // You can adjust the weights here if necessary
        Log.d("GA", "Calculated fitness for message: " + fitness);
        return fitness;
    }

    // Send messages after processing
    public void sendMessages(MainActivity mainActivity) {
        for (Message message : population) {
            String fullMessage = "Assigned Work: " + message.getRequest() +
                    "\nDeadline: " + message.getDeadline() +
                    "\nIncentive: " + message.getIncentive() +
                    "\nTime Taken: " + message.getTimeTaken();

            String phoneNumber = "Receiver's Phone Number";  // You need to define how to get phone numbers

            // Send the message using MainActivity's sendMsg method
            mainActivity.sendMsg(phoneNumber, fullMessage);
            Log.d("GA", "Sent message to " + phoneNumber + ": " + fullMessage);

        }
    }
}
