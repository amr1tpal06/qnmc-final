package qnmc;

import java.util.Set;

public class QuineProcessor {
    public static String applyQuineMcCluskey(Set<String> mintermlist){
        Quine quine = new Quine();
        try {

            for (String mintermitem : mintermlist) {

                quine.addTerm(toBinary(MenuBar.bits, mintermitem));

                System.out.println(mintermitem);
            }

            quine.simplify();
            return quine.toString();

        } catch (ExceptionQuine e) {
            e.printStackTrace();
            return null;
        }
    }

    static public String toBinary (int bits, String mintermitem){
        int mintermnumber = Integer.parseInt(mintermitem);
        String binary= Integer.toBinaryString(mintermnumber);
        int zeros= bits-binary.length();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < zeros; i++) {
            result.append("0");
        }
        result.append(binary);
        return result.toString();
    };
}
