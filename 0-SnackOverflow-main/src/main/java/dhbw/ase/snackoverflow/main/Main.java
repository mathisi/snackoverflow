package dhbw.ase.snackoverflow.main;

import dhbw.ase.snackoverflow.domain.TestDomain;

public class Main {
    public static void main(String[] args) {
        var testDomain = new TestDomain("Hallo");
        System.out.println(testDomain.getName());
    }
}
