package repo.projectname

import thrift.generated.Result

/**
 * Created by magiclane on 23/06/2017.
 */
def blacklist(person) {
    def list = ["zhangsan", "lisi", "wangwu"]
    return person.name in list ? Result.REJECT : Result.PASS
}
