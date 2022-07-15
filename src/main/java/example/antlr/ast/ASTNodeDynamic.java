package example.antlr.ast;

import java.util.ArrayList;

public class ASTNodeDynamic extends ASTNode {

    public ArrayList<ASTNode> children = new ArrayList<>();

    public ASTNodeDynamic() {
        super();
    }
}
