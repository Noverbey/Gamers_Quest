import java.util.ArrayList;

public class FuncDecl {
    Id id;
    Formulas f;
    StmtSeq sq;

    public void parse() {
        id = new Id();
        id.parse();
        Parser.expectedToken(Fun.LPAREN);
        Parser.scanner.nextTok();
        Parser.expectedToken(Fun.REF);
        Parser.scanner.nextTok();
        f = new Formulas();
        f.parse();
        Parser.expectedToken(Fun.RPAREN);
        Parser.scanner.nextTok();
        Parser.expectedToken((Fun.LBRACE));
        Parser.scanner.nextTok();
        sq = new StmtSeq();
        sq.parse();
        Parser.expectedToken(Fun.RBRACE);
        Parser.scanner.nextTok();
    }

    public void semantic() {
        if (Executor.funcNames.contains(id.identifier)) {
            System.out.println("ERROR: The function name \"" + id.identifier + "\" is defined twice!");
            System.exit(0);
        }
        Executor.funcNames.add(id.identifier);
        Executor.params.add(new ArrayList<String>());
        f.semantic();
        Executor.f.add(f);
        Executor.st.add(sq);
    }

    public void print(int indent) {
        System.out.print("\t");
        id.print();
        System.out.print("(reference ");
        f.print(indent);
        System.out.println(") { ");
        sq.print(indent + 1);
        for (int i=0; i<indent; i++) {
			System.out.print("\t");
		}
        System.out.println("}");
    }

    public void execute(int value) {
        this.semantic();
    }
}