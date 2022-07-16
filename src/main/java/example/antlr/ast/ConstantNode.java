package example.antlr.ast;

public class ConstantNode extends AstNode {
    public String constant;
    public ConstantNode(String constant) {
        super();
        this.constant = constant;
    }
}
