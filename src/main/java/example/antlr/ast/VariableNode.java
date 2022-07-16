package example.antlr.ast;

public class VariableNode extends AstNode {

    public enum Type {
        UNKNOWN, INT, BOOLEAN
    }

    public Type type;
    public String name;

    public VariableNode(String name) {
        super();
        this.type = Type.UNKNOWN;
        this.name = name;
    }

    public VariableNode(Type type, String name) {
        super();
        this.type = type;
        this.name = name;
    }
}
