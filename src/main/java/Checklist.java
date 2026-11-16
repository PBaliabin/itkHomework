public class Checklist {

    @TestId(value = "TestCase 101", order = 1)
    void checkLogin() {}

    @TestId(value = "TestCase 103", order = 3)
    void checkPassword() {}

    @TestId(value = "TestCase 105", order = 5)
    void checkName() {}

    void checkEmail() {}

    @TestId(value = "TestCase 104", order = 4)
    void checkAddress() {}
}
