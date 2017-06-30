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
    4: string ssn,
    5: string mobile
}

struct MobileContact {
    1: string name,
    2: list<string> number,
    3: i64 createTime,
    4: list<string> email
}

struct MobileCallRecord {
    1: string name,
    2: string number,
    3: string direction,
    4: i64 createTime,
    5: i64 duration
}

struct MobileSMS {
    1: string name,
    2: string number,
    3: string direction,
    4: i64 createTime,
    5: string content
}

struct MobileDataBody {
    1: string protocolVersion,
    2: string protocolName,
    3: i64 totalNumber,
    4: i64 latestTime,
    5: i64 earliestTime,
    6: list<binary> data
}

struct MobileDataBodyList {
    1: list<MobileDataBody> mobileDataBodyList
}

service MoneyneService {
    PolicyExecutionReport execute(1:string policyName, 2:binary object, 3:string clazzName, 4:string id),

    list<string> publish(),

    string ping()
}