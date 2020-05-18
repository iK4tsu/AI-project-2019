package pt.ipleiria.estg.dei.ia.pl5.g13.ga.geneticOperators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.IntStream;

import pt.ipleiria.estg.dei.ia.pl5.g13.ga.GeneticAlgorithm;
import pt.ipleiria.estg.dei.ia.pl5.g13.ga.IntVectorIndividual;
import pt.ipleiria.estg.dei.ia.pl5.g13.ga.Problem;

public class RecombinationOrderBased<I extends IntVectorIndividual, P extends Problem<I>> extends Recombination<I, P> {

    public RecombinationOrderBased(double probability) {
        super(probability);
    }

    /** Order Based Crossover (OX2)
     *
     * The order based crossover operator (Syswerda 1991) selects at random
     *     several positions in a parent tour, and the order of the items in the
     *     selected positions of this parent is imposed on the other parent.
     *
     * For example, consider the parents:
     *     p1 = [1,2,3,4,5,6,7,8]
     *     p2 = [2,4,6,8,7,5,3,1]
     *
     * Suppose that in the second parent the second, third, and sixth positions
     * are selected. The items in these positions are 4, 6 and 5 respectively. In
     *     the ﬁrst parent these items are present at the fourth, ﬁfth and sixth
     *     positions. Now the offspring is equal to parent 1 except in the fourth,
     *     ﬁfth and sixth positions:
     *         child1 = [1,2,3,*,*,*,7,8]
     *
     * We add the missing items to the offspring in the same order in which they
     *     appear in the second parent tour. This results in
     *         child1 = [1,2,3,4,6,5,7,8]
     *
     * Exchanging the role of the ﬁrst parent and the second parent gives, using
     *     the same selected positions:
     *         child2 = [2,4,3,8,7,5,6,1]
     */
    @Override
    public void recombine(I ind1, I ind2) {
        int size = ind1.getNumGenes();
        int r = GeneticAlgorithm.random.nextInt(size);

        ArrayList<Integer> positions = new ArrayList<>();
        // select `r` random positions
        for (int i = 0; i < r; i++)
        {
            int p;
            do {
                p = GeneticAlgorithm.random.nextInt(size);
            } while (positions.contains(p));

            positions.add(p);
        }

        // indices of the the genes from positions p of the other parent
        ArrayList<Integer> indices1 = new ArrayList<>();
        ArrayList<Integer> indices2 = new ArrayList<>();
        for (int p : positions)
        {
            indices1.add(ind1.getIndexof(ind2.getGene(p)));
            indices2.add(ind2.getIndexof(ind1.getGene(p)));
        }

        Collections.sort(positions);

        // create childs
        ArrayList<Integer> child1 = new ArrayList<>();
        ArrayList<Integer> child2 = new ArrayList<>();
        int i1 = 0, i2 = 0;
        for (int i = 0; i < size; i++)
        {
            if (!indices1.contains(i))
                child1.add(ind1.getGene(i));
            else
                child1.add(ind2.getGene(positions.get(i1++)));

            if (!indices2.contains(i))
                child2.add(ind2.getGene(i));
            else
                child2.add(ind1.getGene(positions.get(i2++)));
        }

        // update parents with the childs genes
        for (int i = 0; i < size; i++)
        {
            ind1.setGene(i, child1.get(i));
            ind2.setGene(i, child2.get(i));
        }
    }

    @Override
    public String toString() {
        return "OX2";
    }
}