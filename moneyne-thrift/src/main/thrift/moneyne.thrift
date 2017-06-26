namespace java thrift.generated

enum Result {
    PASS,
    UNDEFINE,
    REJECT
}

struct RuleResult {
    1: string ruleName,
    2: Result result
}

struct StepResult {
    1: string stepName,
    2: set<RuleResult> ruleResults
    3: Result stepResult
}

struct PolicyExecutionReport {
    1: string policyName,
    2: string datetime,
    3: Result result,
    4: list<StepResult> steps
}

struct Person {
    1: string name,
    2: i32 age,
    3: string city,
    4: string ssn
}
service MoneyneService {
    PolicyExecutionReport execute(1:string policyName, 2:binary object, 3:string clazzName, 4:string id),

    string ping()
}