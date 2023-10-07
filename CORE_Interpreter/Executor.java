import java.util.*;

class FunVar {
	Fun type;
	Integer value;
	
	public FunVar(Fun varType) {
		type = varType;

		if (type == Fun.INT) {
			value = 0;
		}
	}
}

class Executor {
	// Memory variables
	static HashMap<String, FunVar> globalSpace;
	static Stack<HashMap<String, FunVar>> stackSpace;
	static ArrayList<Integer> heapSpace;

	// Storing of funcDecl
	static ArrayList<String> funcNames = new ArrayList<>();
    static ArrayList<Formulas> f = new ArrayList<>();
    static ArrayList<StmtSeq> st = new ArrayList<>();

	// Used for semantic checking of formal params of a function
	static Stack<ArrayList<String>> params = new Stack<ArrayList<String>>();

	static Scanner dataFile;
	
	static void initialize(String dataFileName) {
		globalSpace = new HashMap<String, FunVar>();
		stackSpace = new Stack<HashMap<String, FunVar>>();
		heapSpace = new ArrayList<Integer>();
		//callStack = new Stack<Stack<HashMap<String, FunVar>>>();
		dataFile = new Scanner(dataFileName);
	}
	
	static void pushLocalScope() {
		stackSpace.push(new HashMap<String, FunVar>());
	}
	
	static void popLocalScope() {
		stackSpace.pop();
	}
	
	static int getNextData() {
		int data = 0;
		if (dataFile.currentTok() == Fun.EOS) {
			System.out.println("ERROR: data file is out of values!");
			System.exit(0);
		} else {
			data = dataFile.getCONST();
			dataFile.nextTok();
		}
		return data;
	}
	
	static void allocate(String identifier, Fun varType) {
		FunVar record = new FunVar(varType);
		// If we are in the DeclSeq, the local scope will not be created yet
		if (stackSpace.size() == 0) {
			globalSpace.put(identifier, record);
		} else {
			stackSpace.peek().put(identifier, record);
		}
	}
	
	static FunVar getStackOrStatic(String identifier) {
		int i = stackSpace.size() - 1;
		while (i >= 0 && !stackSpace.get(i).containsKey(identifier)) {
			i--;
		}
		if (i >= 0) {
			return stackSpace.get(i).get(identifier);
		}
			
		return globalSpace.get(identifier);
	}
	
	static void heapAllocate(String identifier) {
		FunVar val = getStackOrStatic(identifier);
		if (val.type != Fun.REF) {
			System.out.println("ERROR: " + identifier + " is not of type reference, cannot perform \"new\"-assign!");
			System.exit(0);
		}
		val.value = heapSpace.size();
		heapSpace.add(null);
	}
	
	static Fun getType(String identifier) {
		FunVar val = getStackOrStatic(identifier);
		return val.type;
	}
	
	static Integer getValue(String identifier) {
		FunVar val = getStackOrStatic(identifier);
		Integer value = val.value;
		if (val.type == Fun.REF) {
			try {
				value = heapSpace.get(value);
			} catch (Exception e) {
				System.out.println("ERROR: invalid heap read attempted!");
				System.exit(0);
			}
		}
		return value;
	}
	
	static void storeValue(String identifier, int value) {
		FunVar val = getStackOrStatic(identifier);
		if (val.type == Fun.REF) {
			try {
				heapSpace.set(val.value, value);
			} catch (Exception e) {
				System.out.println("ERROR: invalid heap write attempted!");
				System.exit(0);
			}
		} else {
			val.value = value;
		}
	}
	
	static void referenceCopy(String var1, String var2) {
		FunVar val1 = getStackOrStatic(var1);
		FunVar val2 = getStackOrStatic(var2);
		val1.value = val2.value;
	}

	// Begin the execution of the FuncCall
	static void copyIn(String funcName, Formulas actualParams) {
        int funCounter = funcNames.lastIndexOf(funcName);
        Formulas formalParams = f.get(funCounter);
        StmtSeq stmtSeq = st.get(funCounter);
		stackSpace.push(new HashMap<String, FunVar>());
		formalParams.execute(actualParams);
		stmtSeq.execute();
		formalParams.executeOut(actualParams);
		stackSpace.pop();
    }
}
