package example.antlr;

import java.util.List;

import example.antlr.ast.*;

/**
 * An AST visitor that converts an original AST into the If Normal Form.
 * See "Bounded Model Checking of Software using SMT Solvers instead of
 * SAT Solvers" for details about the If Normal Form 
 * (https://link.springer.com/chapter/10.1007/11691617_9).
 * 
 * There are several requirements for an AST to be in INF:
 * 1. There should be a single StatementSequence (SS) root node;
 * 2. Each child of the SS node should be an IfBlockNode;
 * 3. Variables in a subsequent child should be marked as "primed"
 *    versions of those in the previous child.
 * 
 * Limitations:
 * 1. The implementation does not take the scope into account.
 *    E.g. "int i = 0; { int j = 0; }" is treated the same as
 *    "int i = 0; int j = 0;".
 * 2. Due to the above limitation, the implementation assumes
 *    that each variable has a unique name.
 *    E.g. "{ int i = 0; }{ int i = 0; }" is an ill-formed program.
 */
public class IfNormalFormAstVisitor extends CBaseAstVisitor<Void> {

    public StatementSequenceNode INF = new StatementSequenceNode();
    
    @Override
    public Void visitStatementSequenceNode(StatementSequenceNode node, List<AstNode> conditions) {
        // Create a new StatementSequenceNode.        
        for (AstNode child : node.children) {
            visit(child, conditions);
        }
        return null;
    }

    @Override
    public Void visitAssignmentNode(AssignmentNode node, List<AstNode> conditions) {
        IfBlockNode ifNode = new IfBlockNode();

        // Set the condition of the if block node.
        AstNode conjunction = takeConjunction(conditions);
        ifNode.setCondition(conjunction);

        // Create a new body node.
        IfBodyNode body = new IfBodyNode();
        ifNode.right = body;

        // Set the then branch to the assignment.
        body.left = node;

        // Set the else branch to an EMPTY ast node.
        body.right = new AstNode();

        // Add the body to the INF node.
        this.INF.children.add(ifNode);

        System.out.println("Add a new IfNode: " + ifNode);
        return null;
    }

    private AstNode takeConjunction(List<AstNode> conditions) {
        if (conditions.size() == 0) {
            return new LiteralNode("true");
        } else if (conditions.size() == 0) {
            return conditions.get(0);
        } else {
            // Take the conjunction of all the conditions.
            LogicalAndNode top = new LogicalAndNode();
            LogicalAndNode cur = top;
            for (int i = 0; i < conditions.size()-1; i++) {
                cur.left = conditions.get(i);
                if (i == conditions.size()-2) {
                    cur.right = conditions.get(i+1);
                } else {
                    cur.right = new LogicalAndNode();
                    cur =(LogicalAndNode)cur.right;
                }
            }
            return top;
        }
    }
}
