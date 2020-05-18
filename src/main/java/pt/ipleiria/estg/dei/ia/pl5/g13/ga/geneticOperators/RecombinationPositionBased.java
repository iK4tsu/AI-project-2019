package pt.ipleiria.estg.dei.ia.pl5.g13.ga.geneticOperators;

import java.util.ArrayList;

import pt.ipleiria.estg.dei.ia.pl5.g13.ga.GeneticAlgorithm;
import pt.ipleiria.estg.dei.ia.pl5.g13.ga.IntVectorIndividual;
import pt.ipleiria.estg.dei.ia.pl5.g13.ga.Problem;

public class RecombinationPositionBased<I extends IntVectorIndividual, P extends Problem<I>> extends Recombination<I, P> {

    public RecombinationPositionBased(double probability) {
        super(probability);
    }

    /** Position Based Crossover (POS)
     *
     * The position based operator (Syswerda 1991) starts by selecting a random
     *     set of positions in the parent tours. However, this operator imposes
     *     the position of the selected items on the corresponding items of the
     *     other parent.
     *
     * For example, consider the parent tours:
     *     p1 = [1,2,3,4,5,6,7,8]
     *     p2 = [2,4,6,8,7,5,3,1]
     *
     * Suppose that the second, third and the sixth positions are selected. This
     *     leads to the following offspring:
     *         child1 = [*,4,6,*,*,5,*,*]
     *
     * We add the remaining missing items to first open space of the offspring,
     *     following the order in which they are represented in the parent:
     *         child1 = [1,4,6,2,3,5,7,8]
     *
     * Exchanging the role of the first parent and the second parent gives, using
     *     the same selected positions:
     *         child2 = [4,2,3,8,7,6,5,1]
     */
    @Override
    public void recombine(I ind1, I ind2) {
        int size = ind1.getNumGenes();
        int r = GeneticAlgorithm.random.nextInt(size);

        ArrayList<Integer> positions = new ArrayList<>(r);
        // select `r` random positions
        for (int i = 0; i < r; i++)
        {
            int p;
            do {
                p = GeneticAlgorithm.random.nextInt(size - 1);
            } while (positions.contains(p));
            positions.add(i, p);
        }

        // swap both parent genes at `p` position
        ArrayList<Integer> child1 = new ArrayList<>();
        ArrayList<Integer> child2 = new ArrayList<>();
        for (int i = 0; i < size; i++)
        {
            if (!positions.contains(i))
            {
                child1.add(null);
                child2.add(null);
            }
            else
            {
                child1.add(ind2.getGene(i));
                child2.add(ind1.getGene(i));
            }
        }

        // shift remaining indices to the first not used position
        int idx1 = 0;
        int idx2 = 0;
        for (int i = 0; i < size; i++)
        {
            if (child1.get(i) == null)
            {
                for (int j = idx1; j < size; j++, idx1++)
                {
                    if (!child1.contains(ind1.getGene(idx1))) {
                        child1.set(i, ind1.getGene(idx1++));
                        break;
                    }
                }
            }

            if (child2.get(i) == null)
            {
                for (int j = idx2; j < size; j++, idx2++)
                {
                    if (!child2.contains(ind2.getGene(idx2))) {
                        child2.set(i, ind2.getGene(idx2++));
                        break;
                    }
                }
            }
        }

        // update parents with childs genes
        for (int i = 0; i < size; i++)
        {
            ind1.setGene(i, child1.get(i));
            ind2.setGene(i, child2.get(i));
        }
    }

    @Override
    public String toString() {
        return "POS";
    }
}