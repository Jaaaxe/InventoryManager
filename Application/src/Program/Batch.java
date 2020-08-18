package Program;

/**
 * Batch class storing info on 1 batch of food
 */
public class Batch implements Comparable<Batch>{
    public String batchID;
    public Food foodItem;
    public int expiryDate;
    public double weight;

    /**
     * Batch constructor
     * @param weight batch weight
     * @param expiry batch expiry
     */
    public Batch(double weight, int expiry){
        this.expiryDate = expiry;
        this.weight = weight;
    }

    /**
     * This method defines the Comparable class, which allows lists of the
     * class type Batch to be sorted. We use this to sort the expiry dates so that
     * we can consume from the lowest
     * @param batch Batch we compare with
     * @return difference between the batches
     */
    @Override
    public int compareTo(Batch batch) {
        return this.expiryDate - batch.expiryDate;
    }
}
