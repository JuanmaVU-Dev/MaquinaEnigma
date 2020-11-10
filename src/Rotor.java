public class Rotor {

    public static final char[] alfabeto = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    public static final char[] I = "EKMFLGDQVZNTOWYHXUSPAIBRCJ".toCharArray();

    public static final char[] II = "AJDKSIRUXBLHWTMCQGZNPYFVOE".toCharArray();

    public static final char[] III = "BDFHJLCPRTXVZNYEIWGAKMUSQO".toCharArray();

    public static final char[] IV = "ESOVPZJAYQUIRHXLNFTGKDCMWB".toCharArray();

    public static final char[] V = "VZBRGITYUPSDNHLXAWMJQOFECK".toCharArray();

    public static final char[] Reflector = "YRUHQSLDPXNGOKMIEBFZCWVJAT".toCharArray();

    public int letraClave;

    public boolean esLetraClave(int posicion) {
        return posicion == letraClave;
    }

    public Rotor(char letraClave){
        this.letraClave = (letraClave - 'A');
    }
}
