public class Clavija {

    public Clavija(){
        letra1 = 'A';
        letra2 = 'A';
    }

    public Clavija(char letra1, char letra2){
        this.letra1 = letra1;
        this.letra2 = letra2;
    }

    public char letra1;
    public char letra2;

    public char intermcambiarLetra(char letra){
        if(letra == letra1){
            return letra2;
        }
        else if(letra == letra2){
            return letra1;
        }
        else{
            return letra;
        }
    }
}
