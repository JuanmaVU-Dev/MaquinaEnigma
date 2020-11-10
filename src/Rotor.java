public class Rotor {

    public static final char[] alfabeto = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    private static final char[] I = "EKMFLGDQVZNTOWYHXUSPAIBRCJ".toCharArray();

    private static final char[] II = "AJDKSIRUXBLHWTMCQGZNPYFVOE".toCharArray();

    private static final char[] III = "BDFHJLCPRTXVZNYEIWGAKMUSQO".toCharArray();

    private static final char[] IV = "ESOVPZJAYQUIRHXLNFTGKDCMWB".toCharArray();

    private static final char[] V = "VZBRGITYUPSDNHLXAWMJQOFECK".toCharArray();

    public static final char[] Reflector = "YRUHQSLDPXNGOKMIEBFZCWVJAT".toCharArray();

    public static char[] letras;

    public int letraClave;

    public boolean esLetraClave(int posicion) {
        return posicion == letraClave;
    }

//    public Rotor(char letraClave){
//        this.letraClave = (letraClave - 'A');
//    }

    public Rotor(int tipoRotor) throws Exception {
        //Letras clave Royal Flags Wave Kings Above
        //             R     F     W    K     A
        switch(tipoRotor){
            case 1:
                this.letras = this.I;
                this.letraClave = ('R' - 'A');
                break;
            case 2:
                this.letras = this.II;
                this.letraClave = ('F' - 'A');
                break;
            case 3:
                this.letras = this.III;
                this.letraClave = ('W' - 'A');
                break;
            case 4:
                this.letras = this.IV;
                this.letraClave = ('K' - 'A');
                break;
            case 5:
                this.letras = this.V;
                this.letraClave = ('A' - 'A');
                break;
            default:
                throw new Exception("Rotor inválido");
        }
        this.letraClave = (letraClave - 'A');
    }
}
