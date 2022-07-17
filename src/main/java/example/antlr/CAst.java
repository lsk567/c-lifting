package example.antlr;

import java.util.ArrayList;
import java.util.List;

public class CAst {
    
    public static class AstNode implements Visitable {
        @Override public <T> T accept(AstVisitor<? extends T> visitor) {
            return ((CAstVisitor<? extends T>)visitor).visitAstNode(this);
        }
        @Override public <T> T accept(AstVisitor<? extends T> visitor, List<AstNode> nodeList) {
            return ((CAstVisitor<? extends T>)visitor).visitAstNode(this, nodeList);
        }
    }

    public static class AstNodeUnary extends AstNode implements Visitable {
        public AstNode child;
        @Override public <T> T accept(AstVisitor<? extends T> visitor) {
            return ((CAstVisitor<? extends T>)visitor).visitAstNodeUnary(this);
        }
        @Override public <T> T accept(AstVisitor<? extends T> visitor, List<AstNode> nodeList) {
            return ((CAstVisitor<? extends T>)visitor).visitAstNodeUnary(this, nodeList);
        }
    }
    
    public static class AstNodeBinary extends AstNode implements Visitable {
        public AstNode left;
        public AstNode right;
        @Override public <T> T accept(AstVisitor<? extends T> visitor) {
            return ((CAstVisitor<? extends T>)visitor).visitAstNodeBinary(this);
        }
        @Override public <T> T accept(AstVisitor<? extends T> visitor, List<AstNode> nodeList) {
            return ((CAstVisitor<? extends T>)visitor).visitAstNodeBinary(this, nodeList);
        }
    }

    public static class AstNodeDynamic extends AstNode implements Visitable {
        public ArrayList<AstNode> children = new ArrayList<>();
        @Override public <T> T accept(AstVisitor<? extends T> visitor) {
            return ((CAstVisitor<? extends T>)visitor).visitAstNodeDynamic(this);
        }
        @Override public <T> T accept(AstVisitor<? extends T> visitor, List<AstNode> nodeList) {
            return ((CAstVisitor<? extends T>)visitor).visitAstNodeDynamic(this, nodeList);
        }
    }

    public static class AssignmentNode extends AstNodeBinary implements Visitable {
        @Override public <T> T accept(AstVisitor<? extends T> visitor) {
            return ((CAstVisitor<? extends T>)visitor).visitAssignmentNode(this);
        }
        @Override public <T> T accept(AstVisitor<? extends T> visitor, List<AstNode> nodeList) {
            return ((CAstVisitor<? extends T>)visitor).visitAssignmentNode(this, nodeList);
        }
    }

    /**
     * AST node for an if block.
     * The left node is the condition.
     * The right node is the if body.
     */
    public static class IfBlockNode extends AstNodeBinary implements Visitable {
        public AstNode getCondition() {
            return this.left;
        }
        public void setCondition(AstNode node) {
            this.left = node;
        }
        @Override public <T> T accept(AstVisitor<? extends T> visitor) {
            return ((CAstVisitor<? extends T>)visitor).visitIfBlockNode(this);
        }
        @Override public <T> T accept(AstVisitor<? extends T> visitor, List<AstNode> nodeList) {
            return ((CAstVisitor<? extends T>)visitor).visitIfBlockNode(this, nodeList);
        }
    }

    /**
     * AST node for an if block.
     * The left node is the then branch.
     * The right node is the else branch.
     */
    public static class IfBodyNode extends AstNodeBinary implements Visitable {
        @Override public <T> T accept(AstVisitor<? extends T> visitor) {
            return ((CAstVisitor<? extends T>)visitor).visitIfBodyNode(this);
        }
        @Override public <T> T accept(AstVisitor<? extends T> visitor, List<AstNode> nodeList) {
            return ((CAstVisitor<? extends T>)visitor).visitIfBodyNode(this, nodeList);
        }
    }

    public static class LiteralNode extends AstNode implements Visitable {
        public String literal;      
        public LiteralNode(String literal) {
            super();
            this.literal = literal;
        }
        @Override public <T> T accept(AstVisitor<? extends T> visitor) {
            return ((CAstVisitor<? extends T>)visitor).visitLiteralNode(this);
        }
        @Override public <T> T accept(AstVisitor<? extends T> visitor, List<AstNode> nodeList) {
            return ((CAstVisitor<? extends T>)visitor).visitLiteralNode(this, nodeList);
        }
    }

    public static class LogicalAndNode extends AstNodeBinary implements Visitable {
        @Override public <T> T accept(AstVisitor<? extends T> visitor) {
            return ((CAstVisitor<? extends T>)visitor).visitLogicalAndNode(this);
        }
        @Override public <T> T accept(AstVisitor<? extends T> visitor, List<AstNode> nodeList) {
            return ((CAstVisitor<? extends T>)visitor).visitLogicalAndNode(this, nodeList);
        }
    }

    public static class LogicalOrNode extends AstNodeBinary implements Visitable {
        @Override public <T> T accept(AstVisitor<? extends T> visitor) {
            return ((CAstVisitor<? extends T>)visitor).visitLogicalOrNode(this);
        }
        @Override public <T> T accept(AstVisitor<? extends T> visitor, List<AstNode> nodeList) {
            return ((CAstVisitor<? extends T>)visitor).visitLogicalOrNode(this, nodeList);
        }
    }

    /**
     * An Ast node that indicates the code
     * represented by this node is unanalyzable.
     */
    public static class OpaqueNode extends AstNode implements Visitable {  
        @Override public <T> T accept(AstVisitor<? extends T> visitor) {
            return ((CAstVisitor<? extends T>)visitor).visitOpaqueNode(this);
        }
        @Override public <T> T accept(AstVisitor<? extends T> visitor, List<AstNode> nodeList) {
            return ((CAstVisitor<? extends T>)visitor).visitOpaqueNode(this, nodeList);
        }
    }

    public static class StatementSequenceNode extends AstNodeDynamic implements Visitable {
        @Override public <T> T accept(AstVisitor<? extends T> visitor) {
            return ((CAstVisitor<? extends T>)visitor).visitStatementSequenceNode(this);
        }
        @Override public <T> T accept(AstVisitor<? extends T> visitor, List<AstNode> nodeList) {
            return ((CAstVisitor<? extends T>)visitor).visitStatementSequenceNode(this, nodeList);
        }
    }

    public static class VariableNode extends AstNode implements Visitable {
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
        @Override public <T> T accept(AstVisitor<? extends T> visitor) {
            return ((CAstVisitor<? extends T>)visitor).visitVariableNode(this);
        }
        @Override public <T> T accept(AstVisitor<? extends T> visitor, List<AstNode> nodeList) {
            return ((CAstVisitor<? extends T>)visitor).visitVariableNode(this, nodeList);
        }
    }
}
