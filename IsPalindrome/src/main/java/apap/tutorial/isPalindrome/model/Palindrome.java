package apap.tutorial.isPalindrome.model;

import java.io.Serializable;

public class Palindrome implements Serializable {

    private String kalimat;

    public Palindrome(String kalimat){
        this.kalimat = kalimat;
    }

    public String isPalindrome(){
        int n = kalimat.length();

        for (int i =0;i<(n/2);++i){
            if (kalimat.charAt(i) != kalimat.charAt(n - i - 1)){
                return "Kata anda bukanlah Palindrome";
            }
        }

        return "Kata anda adalah Palindrome";
    }

    public String getKalimat() {
        return kalimat;
    }
}
