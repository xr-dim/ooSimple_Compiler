package symbol;

public abstract class Symbol {

    final int codeFileLine;
    String signature;
    String name;

    public enum DataCategory{
        PRIMITIVE,
        CLASS,
    }

    public Symbol(String name, String signature, int tokenLine) {
        this.name = name;
        this.signature = signature;
        this.codeFileLine = tokenLine;
    }

    public String getName() {
        return this.name;
    }
    public String getSignature(){
        return this.signature;
    }
    public int getTokenLine() {
        return this.codeFileLine;
    }
}
