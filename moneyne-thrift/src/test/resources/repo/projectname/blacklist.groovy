package repo.projectname

/**
 * Created by magiclane on 23/06/2017.
 */
def blacklist(person) {
    def list = ["zhangsan", "lisi", "wangwu"]
    return person.name in list
}
