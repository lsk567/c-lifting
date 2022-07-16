package example.antlr;

import example.antlr.ast.AstNode;

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
public class IfNormalFormAstVisitor extends CBaseAstVisitor<AstNode> {
    
}
