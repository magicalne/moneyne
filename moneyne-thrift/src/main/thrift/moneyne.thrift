namespace java thrift.generated

enum Mode {
    NORMAL
}

enum Result {
    PASS,
    UNDEFINE,
    REJECT
}

struct RuleResult {
    1: string ruleName,
    2: Result result
}

struct PolicyExecutionReport {
    1: string policyName,
    2: string datetime,
    3: Result result,
    4: Mode mode,
    5: list<set<RuleResult>> steps
}