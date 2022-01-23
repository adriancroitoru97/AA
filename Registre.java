import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Registre extends Task {

    private int n;
    private int m;
    private int k;

    /* u, v used for storing the edges */
    private List<Integer> u;
    private List<Integer> v;

    private Integer nrOfVariables;
    private Integer nrOfClauses;
    private List<Integer> clauses;
    
    /* result is the true/false return of the oracle
       in case of true, solution is the effective combination */
    private String result;
    private List<Integer> solution;

    public Registre() {
        u = new ArrayList<>();
        v = new ArrayList<>();
        clauses = new ArrayList<>();
        nrOfClauses = 0;
        solution = new ArrayList<>();
    }
    
    public static void main(String[] args) throws IOException, InterruptedException {
        Registre registre = new Registre();
        registre.solve();
    }

    @Override
    public void solve() throws IOException, InterruptedException {
        readProblemData();
        formulateOracleQuestion();
        askOracle();
        decipherOracleAnswer();
        writeAnswer();
    }

    @Override
    public void readProblemData() throws IOException {
        Scanner scanner = new Scanner(System.in);

        n = scanner.nextInt();
        m = scanner.nextInt();
        k = scanner.nextInt();
        for (int i = 0; i < m; i++) {
            u.add(scanner.nextInt());
            v.add(scanner.nextInt());
        }

        scanner.close();
    }

    /**
     * Graph coloring problem reduced to SAT
     */
    @Override
    public void formulateOracleQuestion() throws IOException {

        /* I clause - there exist at least one node for
           every element of the cover */
        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= k; j++) {
                clauses.add(getVariable(i, j));
            }
            clauses.add(0);
            nrOfClauses++;
        }

        /* II clause - for an edge (u, w), u and w cannot both be in the coloring */
        for (int i = 0; i < m; i++) {
            int u_m = u.get(i);
            int v_m = v.get(i);
            for (int p = 1; p <= k; p++) {
                Integer neg1 = (-1)*getVariable(u_m, p);
                Integer neg2 = (-1)*getVariable(v_m, p);
                clauses.add(neg1);
                clauses.add(neg2);
                clauses.add(0);
                nrOfClauses++;
            }
        }

        nrOfVariables = n * k;

        BufferedWriter writer = new BufferedWriter(new FileWriter("sat.cnf"));
        writer.write("p cnf ");
        writer.append(nrOfVariables.toString());
        writer.append(" ");
        writer.append(nrOfClauses.toString());
        writer.append("\n");

        for (Integer i : clauses) {
            writer.append(i.toString());
            writer.append(" ");

            if (i == 0) {
                writer.append("\n");
            }
        }

        writer.close();
    }

    @Override
    public void decipherOracleAnswer() throws IOException {
        Scanner scanner = new Scanner(new File("sat.sol"));

        result = scanner.next();
        if (result.equals("True")) {
           int locN = scanner.nextInt();
           for (int i = 0; i < locN; i++) {
               int x = scanner.nextInt();
                solution.add(x);
           }
        }

        scanner.close();
    }

    @Override
    public void writeAnswer() throws IOException {
        System.out.println(result);
        if (result.equals("True")) {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < k; j++) {
                    if (solution.get(i + j * n) > 0) {
                        builder.append(j + 1);
                        builder.append(" ");
                        break;
                    }
                }
            }
            System.out.println(builder.toString());
        }
    }

    /* function which codifies a variable using the node and the k value */
    private Integer getVariable(int i, int r) {
        return (n * (r - 1) + i);
    }
}
