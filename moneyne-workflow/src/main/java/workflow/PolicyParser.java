package workflow;

import org.parboiled.BaseParser;
import org.parboiled.Rule;
import org.parboiled.annotations.BuildParseTree;
import org.parboiled.support.Var;

/**
 * Created by magiclane on 20/06/2017.
 */
@BuildParseTree
class PolicyParser extends BaseParser<Object> {

    Rule Policy(String policyId) {
        final Policy policy = new Policy();
        policy.setPolicyId(policyId);
        final Var<Policy> policyVar = new Var<>(policy);
        return Sequence(
                Namespace(policyVar),
                InitBlock(policyVar),
                StepBlockList(policyVar),
                EOI,
                push(policyVar.get())
        );
    }

    Rule Namespace(Var<Policy> policyVar) {
        return Sequence(
                String("namespace"),
                OneOrMore(Whitespace()),
                OneOrMore(TestNot(Spacing()), ANY),
                policyVar.set(policyVar.get().setNamespace(match())),
                OneOrMore(NewLine())
        );
    }

    Rule InitBlock(Var<Policy> policyVar) {
        final Var<Step> stepVar = new Var<>(new Step());
        return Sequence(
                ZeroOrMore(Spacing()),
                String("init:"),
                NewLine(),
                Step(stepVar),
                policyVar.set(policyVar.get().setInitStep(stepVar.get()))
        );
    }

    Rule StepBlockList(Var<Policy> policyVar) {
        return ZeroOrMore(
                StepBlock(policyVar)
        );
    }

    Rule StepBlock(Var<Policy> policyVar) {
        final Var<Step> stepVar = new Var<>(new Step());
        return Sequence(
                ZeroOrMore(Spacing()),
                String("step"),
                Spacing(),
                StepName(stepVar),
                ColonChar(),
                NewLine(),
                Step(stepVar),
                ZeroOrMore(NewLine()),
                policyVar.set(policyVar.get().addStep(stepVar.get().getName(), stepVar.get()))
        );
    }

    Rule StepName(Var<Step> stepVar) {
        return Sequence(
                OneOrMore(
                        TestNot(Spacing()),
                        TestNot(ColonChar()),
                        ANY
                ),
                stepVar.set(stepVar.get().setName(match()))

        );
    }

    Rule ColonChar() {
        return Ch(':');
    }

    Rule Step(Var<Step> stepVar) {
        return Sequence(
                RuleSet(stepVar),
                Optional(Mode(stepVar)),
                Optional(PassFlow(stepVar)),
                Optional(RejectFlow(stepVar)),
                Optional(UndefineFlow(stepVar))
        );
    }

    Rule RuleSet(Var<Step> stepVar) {
        return Sequence(
                IgnoreCase("ruleSet"),
                Spacing(),
                EqualChar(),
                Spacing(),
                Array(stepVar),
                Optional(NewLine())
        );
    }

    Rule Array(Var<Step> stepVar) {
        return Sequence(
                Ch('['),
                Element(stepVar),
                ZeroOrMore(Sequence(CommaChar(), Element(stepVar))),
                Ch(']')
        );
    }

    Rule Element(Var<Step> stepVar) {
        Var<RuleElement> ruleElementVar = new Var<>(new RuleElement());
        return Sequence(
                ZeroOrMore(Whitespace()),
                OneOrMore(
                        TestNot(Ch('[')),
                        TestNot(Ch(']')),
                        TestNot(Whitespace()),
                        TestNot(CommaChar()),
                        ANY),
                ruleElementVar.set(ruleElementVar.get().setRuleName(match())),
                ZeroOrMore(Whitespace()),
                Optional(Sequence(
                        ColonChar(),
                        ZeroOrMore(Whitespace()),
                        OneOrMore(
                                TestNot(Whitespace()),
                                TestNot(Ch(']')),
                                TestNot(CommaChar()),
                                ANY
                                ),
                        ruleElementVar.set(ruleElementVar.get().setDependOn(match()))
                )),
                stepVar.set(stepVar.get().addRule(ruleElementVar.get()))
        );
    }

    Rule Mode(Var<Step> stepVar) {
        return Sequence(
                ZeroOrMore(Spacing()),
                String("mode"),
                ZeroOrMore(Spacing()),
                EqualChar(),
                ZeroOrMore(Spacing()),
                OneOrMore(
                        TestNot(Spacing()),
                        TestNot(NewLine()),
                        TestNot(EOI),
                        ANY
                ),
                stepVar.set(stepVar.get().setMode(Mode.valueOf(match()))),
                ZeroOrMore(Spacing())
        ).label("Mode");
    }

    Rule PassFlow(Var<Step> stepVar) {
        return Sequence(
                ZeroOrMore(Spacing()),
                IgnoreCase("pass"),
                ZeroOrMore(Spacing()),
                ArrowString(),
                ZeroOrMore(Spacing()),
                OneOrMore(
                        TestNot(Spacing()),
                        TestNot(NewLine()),
                        TestNot(EOI),
                        ANY
                ),
                stepVar.set(stepVar.get().setPassStep(match())),
                ZeroOrMore(Spacing())
        );
    }

    Rule RejectFlow(Var<Step> stepVar) {
        return Sequence(
                ZeroOrMore(Spacing()),
                IgnoreCase("reject"),
                ZeroOrMore(Spacing()),
                ArrowString(),
                ZeroOrMore(Spacing()),
                OneOrMore(
                        TestNot(Spacing()),
                        TestNot(NewLine()),
                        TestNot(EOI),
                        ANY
                ),
                stepVar.set(stepVar.get().setRejectStep(match())),
                ZeroOrMore(Spacing())
        );
    }

    Rule UndefineFlow(Var<Step> stepVar) {
        return Sequence(
                ZeroOrMore(Spacing()),
                IgnoreCase("undefine"),
                ZeroOrMore(Spacing()),
                ArrowString(),
                ZeroOrMore(Spacing()),
                OneOrMore(
                        TestNot(Spacing()),
                        TestNot(NewLine()),
                        TestNot(EOI),
                        ANY
                ),
                stepVar.set(stepVar.get().setUndefineStep(match())),
                ZeroOrMore(Spacing())
        );
    }

    Rule ArrowString() {
        return String("->");
    }

    Rule EqualChar() {
        return Ch('=');
    }

    Rule CommaChar() {
        return Ch(',');
    }

    Rule Spacing() {
        return OneOrMore(AnyOf(" \t\r\n\f").label("BlankSpace"));
    }

    Rule NewLine() {
        return FirstOf('\n', Sequence('\r', Optional('\n')));
    }


    Rule Whitespace() {
        return AnyOf(" \t");
    }

}
