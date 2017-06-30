import thrift.generated.Person
import thrift.generated.Result

/**
 * Created by magiclane on 23/06/2017.
 */
def personTest(Person person) {
    return person.age > 22 ? Result.PASS : Result.REJECT
}
