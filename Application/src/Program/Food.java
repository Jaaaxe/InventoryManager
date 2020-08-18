package Program;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Food class which stores the batches array and names
 */
public class Food {
    public ArrayList<Batch> Batches;
    public String name;

    /**
     * Constructor for the food class
     * @param name product name
     */
    public Food(String name) {
        this.Batches = new ArrayList<>();
        this.name = name;
    }

    /**
     * Adds batches to the product
     * @param batch Batch
     */
    public void Add(Batch batch) {
        this.Batches.add(batch);
        Collections.sort(Batches);
    }

    /**
     * Finds the total weight of the product group
     * @return Total weight
     */
    public double GetTotalWeight() {
        double totalWeight = 0;
        for (Batch batch : this.Batches) {
            totalWeight += batch.weight;
        }
        return totalWeight;
    }

    /**
     * Consume food
     * @param weight weight of the food user would like to consume
     */
    public void Consume(double weight) {
        double consumed = 0;
        int index = 0;

        while (consumed < weight) {
            Batch cb = this.Batches.get(index);
            if (cb.weight >= weight) {
                cb.weight -= (weight - consumed);
                consumed = (weight - consumed);
                if (cb.weight < 0) {
                    this.Batches.remove(cb);
                }
                break;
            } else if (cb.weight < weight) {
                // Error for if we have more weight being consumed than available
                // Printed to console since javafx doesn't allow setting non static labels
                System.out.println("You're consuming more food than the available quantity");
                break;
            } else {
                consumed += cb.weight;
                this.Batches.remove(cb);
            }
        }
    }
}
