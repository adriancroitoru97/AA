import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Reclame extends Task {

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

    public Reclame() {
        u = new ArrayList<>();
        v = new ArrayList<>();
        clauses = new ArrayList<>();
        nrOfClauses = 0;
        solution = new ArrayList<>();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Reclame reclame = new Reclame();
        reclame.solve();
    }

    @Override
    public void solve() throws IOException, InterruptedException {
        readProblemData();

        /* seeks all k values from 1 to n and stops when
           the first k value returns a TRUE result */
        for (int i = 1; i <= n; i++) {
            k = i;
            clauses.clear();
            nrOfClauses = 0;
            solution.clear();
            result="";

            formulateOracleQuestion();
            askOracle();
            decipherOracleAnswer();

            if (result.equals("True")) {
                break;
            }
        }
        
        writeAnswer();
    }

    @Override
    public void readProblemData() throws IOException {
        Scanner scanner = new Scanner(System.in);

        n = scanner.nextInt();
        m = scanner.nextInt();
        for (int i = 0; i < m; i++) {
            u.add(scanner.nextInt());
            v.add(scanner.nextInt());
        }

        scanner.close();
    }

     /**
     * Vertex cover problem reduced to SAT
     */
    @Override
    public void formulateOracleQuestion() throws IOException {
       
        nrOfVariables = n * k;

        /* I clause - there exist at least one node for
           every element of the cover */
        for (int i = 1; i <= k; i++) {
            for (int j = 1; j <= n; j++) {
                clauses.add(getVariable(j, i));
            }
            clauses.add(0);
            nrOfClauses++;
        }

        /* II clause - a node is in the cover at most once */
        for (int p = 1; p <= n; p++) {
            for (int i = 1; i < k; i++) {
                for (int j = i + 1; j <= k; j++) {
                        Integer neg1 = (-1)*getVariable(p, i);
                        Integer neg2 = (-1)*getVariable(p, j);
                        clauses.add(neg1);
                        clauses.add(neg2);
                        clauses.add(0);
                        nrOfClauses++;
                }
            }
        }

        for (int p = 1; p < n; p++) {
            for (int q = p + 1; q <= n; q++) {
                for (int i = 1; i <= k; i++) {
                    Integer neg1 = (-1)*getVariable(p, i);
                    Integer neg2 = (-1)*getVariable(q, i);
                    clauses.add(neg1);
                    clauses.add(neg2);
                    clauses.add(0);
                    nrOfClauses++;
                }
            }
        }

        /* III clause - any edge has at least one node in the cover */
        for (int i = 0; i < m; i++) {
            int u_m = u.get(i);
            int v_m = v.get(i);

            for (int p = 1; p <= k; p++) {
                clauses.add(getVariable(u_m, p));
            }
            for (int p = 1; p <= k; p++) {
                clauses.add(getVariable(v_m, p));
            }
            clauses.add(0);
            nrOfClauses++;
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
               if (x > 0) {
                   solution.add(getNode(x)); 
               }
           }
        }

        scanner.close();
    }

    @Override
    public void writeAnswer() throws IOException {
        StringBuilder builder = new StringBuilder();
        for (int value : solution) {
            builder.append(value);
            builder.append(" ");
        }
        System.out.println(builder.toString());
    }

    /* function which codifies a variable using the node and the k value */
    private Integer getVariable(int i, int r) {
        return (n * (r - 1) + i);
    }

    /* converts a variable into a node */
    private Integer getNode(int variable) {
        if (variable % n == 0) {
            return n;
        }

        return variable % n;
    }
}