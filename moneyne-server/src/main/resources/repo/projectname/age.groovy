package repo.projectname

import thrift.generated.Result

/**
 * Created by magiclane on 23/06/2017.
 */
def age(person) {
    return person.age > 22 ? Result.PASS : Result.REJECT
}