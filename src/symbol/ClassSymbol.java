package symbol;
import scope.Scope;

public class ClassSymbol extends Symbol {

    private Scope scopePointer;
    private int numberOfParentClasses;

    public ClassSymbol(String className, int tokenLine) {
        super(className, className + "$", tokenLine);
        this.scopePointer = null;
        this.numberOfParentClasses = 0;
    }

    public Scope getScopePointer() {
        return this.scopePointer;
    }

    public void setScopePointer(Scope scopePointer) {
        this.scopePointer = scopePointer;
    }

    public void setNumberOfParentClasses(int numberOfParentClasses) {
        this.numberOfParentClasses = numberOfParentClasses;
    }

    public int getNumberOfParentClasses() {
        return this.numberOfParentClasses;
    }

    @Override
    public String toString() {
        return "ClassSymbol{" +
                "name='" + name + '\'' +
                ", signature='" + signature + '\'' +
                ", numberOfParentClasses=" + numberOfParentClasses +
                ", codeFileLine=" + codeFileLine +
                '}';
    }
}
