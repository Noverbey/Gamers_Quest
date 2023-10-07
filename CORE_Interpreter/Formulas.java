import java.util.*;
public class Formulas {
    Id id;
    Formulas f;

    public void parse() {
        id = new Id();
        id.parse();
        if (Parser.scanner.currentTok() == Fun.COMMA) {
            Parser.scanner.nextTok(); 
            f = new Formulas();
            f.parse();
        }
    }

    public void semantic() {
        ArrayList<String> a = Executor.params.peek();
        if (a.contains(id.identifier)) {
            System.out.println("ERROR: There are duplicate formal params \"" + id.identifier + "\"!");
            System.exit(0);
        }
        a.add(id.identifier);
        if (f != null) {
            f.semantic();
        }
    }

    public void print(int indent) {
        id.print();
        if (f != null) {
            System.out.print(", ");
            f.print(indent);
        }
    }

    public void execute(Formulas actualParams) {
        int value = Executor.getValue(actualParams.id.identifier);
        Executor.allocate(id.identifier, Fun.REF);
        Executor.heapAllocate(id.identifier);
        Executor.storeValue(id.identifier, value);
        if (f != null) {
            f.execute(actualParams.f);
        }
    }

    public void executeOut(Formulas actualParams) {
        int value = Executor.getValue(id.identifier);
        HashMap<String, FunVar> m = Executor.stackSpace.pop();
        Executor.storeValue(actualParams.id.identifier, value);
        Executor.stackSpace.push(m);
        if (f != null) {
            f.executeOut(actualParams.f);
        }
    }
}
