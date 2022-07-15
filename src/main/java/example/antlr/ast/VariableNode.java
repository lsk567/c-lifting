package example.antlr.ast;

public class VariableNode extends ASTNode {

    public enum Type {
        INT, BOOLEAN
    }

    public Type type;
    public String name;

    public VariableNode(Type type, String name) {
        super();
        this.type = type;
        this.name = name;
    }
}
