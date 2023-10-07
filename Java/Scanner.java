import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

class Scanner {
    final String id = "[a-zA-Z][a-zA-Z0-9]*";
    final String constant = "[0-9]|[1-9][0-9]*";
    BufferedReader in;
    StringBuilder charSequence;
    Fun token;

    // Initialize the scanner
    Scanner(String filename) {
        try {
            this.in = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: File not found");
        }
        this.nextTok();
    }

    // Advance to the next token
    public void nextTok() {
        try {
			// Skip white space
			int character = this.skipWhiteSpace();
			
            // End of stream?
            if (character == -1) {
                this.token = Fun.EOS;
            } 
			else 
			{
				switch ((char) character) {
                    // Single character special symbols
                    case ';':
                        this.token = Fun.SEMICOLON;
                        break;

                    case '(':
                        this.token = Fun.LPAREN;
                        break;

                    case ')':
                        this.token = Fun.RPAREN;
                        break;

                    case ',':
                        this.token = Fun.COMMA;
                        break;
						
					case '}':
                        this.token = Fun.RBRACE;
                        break;

                    case '{':
                        this.token = Fun.LBRACE;
                        break;
						
					case '!':
                        this.token = Fun.NEGATION;
                        break;

                    case '+':
                        this.token = Fun.ADD;
                        break;

                    case '-':
                        this.token = Fun.SUB;
                        break;

                    case '*':
                        this.token = Fun.MULT;
                        break;

                    // Special symbols with one or more characters
                    case '=': {
                        this.in.mark(1);
                        if ((char) this.in.read() == '=') {
                            this.token = Fun.EQUAL;
                        } else {
                            this.in.reset();
                            this.token = Fun.ASSIGN;
                        }
                        break;
                    }

                    case '<': {
                        this.in.mark(1);
                        if ((char) this.in.read() == '=') {
                            this.token = Fun.LESSEQUAL;
                        } else {
                            this.in.reset();
                            this.token = Fun.LESS;
                        }
                        break;
                    }
					// ID, CONST, or keyword
                    default: {
						matchLongToken(character);
                    }
                }
            }
        }
		catch (IOException e) {
            System.out.println("ERROR: Reading program file");
            this.token = Fun.ERROR;
        }
		catch (RuntimeException e) {
            System.out.println("ERROR: Invalid input " + this.charSequence.toString());
            this.token = Fun.ERROR;
        }
    }

    // Return the current token
    public Fun currentTok() {
        return this.token;
    }

    public String getID() {
        return this.charSequence.toString();
    }

    public int getCONST() {
        return Integer.parseInt(this.charSequence.toString());
    }
	
	// Helper methods
	private int skipWhiteSpace() throws IOException {
		int character = this.in.read();
		while (Character.isWhitespace(character) && character != -1) {
			character = this.in.read();
		}
		return character;
	}
	
	// ID, CONST, or keyword
	private void matchLongToken(int character) throws IOException, RuntimeException {
		this.charSequence = new StringBuilder();
		// First character is a digit? Stop at something non-digit
		if (Character.isDigit((char) character)) {
			do {
				this.charSequence.append((char) character);
				this.in.mark(1);
				character = this.in.read();								
			} while (character != -1 && Character.isDigit((char) character));
			this.in.reset();
		}
		// First character is a letter? Stop at something non-letter and non-digit
		else if (Character.isLetter((char) character)) {
			do {
				this.charSequence.append((char) character);
				this.in.mark(1);
				character = this.in.read();								
			} while (character != -1 && Character.isLetterOrDigit((char) character));
			this.in.reset();
		}
		// First character is not a letter, digit, or any special symbol? Stop reading
		else {
			this.charSequence.append((char) character);
		}

		switch (this.charSequence.toString()) {
			// Keywords
			case "program":
				this.token = Fun.PROGRAM;
				break;

			case "begin":
				this.token = Fun.BEGIN;
				break;

			case "end":
				this.token = Fun.END;
				break;
				
			case "new":
				this.token = Fun.NEW;
				break;
				
			case "int":
				this.token = Fun.INT;
				break;
				
			case "reference":
				this.token = Fun.REF;
				break;
			
			case "inst":
				this.token = Fun.INSTANCE;
				break;

			case "if":
				this.token = Fun.IF;
				break;

			case "then":
				this.token = Fun.THEN;
				break;

			case "else":
				this.token = Fun.ELSE;
				break;

			case "while":
				this.token = Fun.WHILE;
				break;

			case "or":
				this.token = Fun.OR;
				break;
				
			case "and":
				this.token = Fun.AND;
				break;

			case "read":
				this.token = Fun.READ;
				break;

			case "write":
				this.token = Fun.WRITE;
				break;
				
			case "share":
				this.token = Fun.SHARE;
				break;

			default: {
				// Identifier
				if (this.charSequence.toString().matches(this.id)) {
					this.token = Fun.ID;

				}
				// Constant less than 256
				else if (this.charSequence.toString().matches(this.constant) && 
						 Integer.parseInt(this.charSequence.toString()) < 256) {
					this.token = Fun.CONST;
				}
				// All invalid input including invalid symbols, leading zeros, 
				// identifier with digit 0, constant greater than 255 and etc.
				else {
					throw new RuntimeException();
				}
			}
		}		
	}
}
