package repo.projectname

import thrift.generated.Result

/**
 * Created by magiclane on 23/06/2017.
 */
def city(person) {
    return person.city == "shanghai" ? Result.PASS : Result.REJECT
}
