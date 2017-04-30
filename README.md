# moneyne
Rule engine system, not very simple one .

I develop softwares to control risk on borrowing moneny online. A good rule engine is really important. And as domain business developer, I have a good sense of what a kind of rule engine is a **GOOD** one.

## Things you should concern.

Old fashion rule engine systems generally need at least two environments, one for testing, the other one for production using. Look, this is problem one. You need two enviorments for each "environment" actually. You need to write your rule and test it on testing env. If it passes the testing phase, then you publish it to production env anyway.

Testing is sensitive. You can write unit tests and integretion tests on your rule engine. And we all know testing is good and essential. You try so hard to find all the bugs. But how about the bugs that you can't find? And even worse, there's no bug, no exception logs are been printed out. The rules just run quietly, in a way which is **wrong**.

If your company is doing money business. If there's some bug, people maybe just cannot borrow the money. It's not OK. But it's acceptable. You write programs, you write bugs, then you debugging. If you just somehow write a wrong program and no exception is thrown. And all the people can pass this rule becaues it doesn't work. Maybe it's not your fault, just some guy make a mistake and your company just lost hundreds, thouthands money... Maybe millions.

You say your job is to control risk, but you just cannot control the risk of rule engine. Maybe it's not rule engine's blame. And you blame on the people who write the rule. But I say, since you provide service online, then you take the risk, and try to lower the risk as much as possible.

### Wthat are the problems we shold foucing on?

1. Only one environment each. You don't maintain two envs at the same time.
2. Don't publish new rules as soon as your update the rule. Use A/b testing all the time. Auto upgrading and downgrading if needed.
3. Better performance. We should have a goal for tps and throughput.