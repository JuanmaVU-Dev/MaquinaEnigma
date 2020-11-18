import javax.security.auth.login.Configuration;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

class Enigma {

    Rotor rotor1;
    Rotor rotor2;
    Rotor rotor3;
    Rotor reflector;
    int posicion1;
    int posicion2;
    int posicion3;
    boolean primeraPulsacion;
    Clavija clavija;

    public Enigma() {
        posicion1 = 0;
        posicion2 = 0;
        posicion3 = 0;
        primeraPulsacion = true;

        rotor1 = new Rotor('R');
        rotor2 = new Rotor('F');
        rotor3 = new Rotor('W');
    }

    public Enigma(int posicion1, int posicion2, int posicion3) {
        this.posicion1 = posicion1;
        this.posicion2 = posicion2;
        this.posicion3 = posicion3;
        primeraPulsacion = true;

        rotor1 = new Rotor('R');
        rotor2 = new Rotor('F');
        rotor3 = new Rotor('W');
    }

    public Enigma(char posicion1, char posicion2, char posicion3, int tipoRotor1, int tipoRotor2,int tipoRotor3) throws Exception {
        this.posicion1 = posicion1 - 65;
        this.posicion2 = posicion2 - 65;
        this.posicion3 = posicion3 - 65;
        primeraPulsacion = true;

        rotor1 = new Rotor(tipoRotor1);
        rotor2 = new Rotor(tipoRotor2);
        rotor3 = new Rotor(tipoRotor3);
        clavija = new Clavija();
    }

    public Enigma(char posicion1, char posicion2, char posicion3, int tipoRotor1, int tipoRotor2,int tipoRotor3, char letra1, char letra2) throws Exception {
        this.posicion1 = posicion1 - 65;
        this.posicion2 = posicion2 - 65;
        this.posicion3 = posicion3 - 65;
        primeraPulsacion = true;

        rotor1 = new Rotor(tipoRotor1);
        rotor2 = new Rotor(tipoRotor2);
        rotor3 = new Rotor(tipoRotor3);
        clavija = new Clavija(letra1, letra2);
    }

    private void girarRotores() {
        if(primeraPulsacion && rotor2.esLetraClave(posicion2+1)){
            posicion3 = (posicion3 + 1) % 26;
            posicion2 = (posicion2 + 1) % 26;
            posicion1  = (posicion1 +1) % 26;
            //System.out.println("Cambio en primera pulsacion");
        }else{
            posicion3 = (posicion3 + 1) % 26;
            if (rotor3.esLetraClave(posicion3)) {
                posicion2 = (posicion2 + 1) % 26;
                //System.out.println("gira rotor 2");
                if (rotor2.esLetraClave(posicion2)) {
                    posicion1 = (posicion1 + 1) % 26;
                    //System.out.println("gira rotor 1");
                }
            }

            // Doble paso
            if (rotor3.esLetraClave(posicion3 - 1) && rotor2.esLetraClave(posicion2 + 1)) {
                posicion2 = (posicion2 + 1) % 26;
                if (rotor2.esLetraClave(posicion2)) {
                    posicion1 = (posicion1 + 1) % 26;
                    //System.out.println("doble paso");
                }
            }
        }
        primeraPulsacion = false;
    }

    private char codificarLetra(char letra) {
        girarRotores();
        int indice;
        //System.out.println("----------- CLAVIJERO -----------");
        //Cambiar una letra por otra
        char letraCodificada = clavija.intercambiarLetra(letra);
        //System.out.println("----------- IDA -----------");
        letraCodificada = rotor3.letras[(letraCodificada - 'A' + posicion3) % 26];

        if (((letraCodificada - 65) - posicion3) >= 0) {
            indice = (letraCodificada - 65) - posicion3;
        } else {
            indice = (letraCodificada - 65 + 26) - posicion3;
        }

        letraCodificada = Rotor.alfabeto[(indice + posicion2) % 26];
        letraCodificada = rotor2.letras[(letraCodificada - 'A') % 26];
        if ((letraCodificada - 65) - posicion2 >= 0) {
            indice = (letraCodificada - 65) - posicion2;
        } else {
            indice = (letraCodificada - 65 + 26) - posicion2;
        }
        letraCodificada = Rotor.alfabeto[(indice + posicion1) % 26];

        letraCodificada = rotor1.letras[(letraCodificada - 'A') % 26];

        //System.out.println("----------- REFLECTOR -----------");
        if ((letraCodificada - 'A' - posicion1) >= 0) {
            indice = letraCodificada - 'A' - posicion1;
        } else {
            indice = (letraCodificada - 'A' + 26) - posicion1;
        }
        letraCodificada = Rotor.Reflector[indice];
        //System.out.println("----------- VUELTA -----------");
        if (letraCodificada + posicion1 > 'Z') {
            indice = (letraCodificada + posicion1 - 'Z') + 64;
        } else {
            indice = letraCodificada + posicion1;
        }
        letraCodificada = (char) (new String(rotor1.letras).indexOf(indice) + 65);
        if ((letraCodificada + posicion2 - posicion1) > 'Z') {
            indice = (letraCodificada + posicion2 - posicion1 - 'Z') + 64;
        } else if ((letraCodificada + posicion2 - posicion1) < 'A') {
            indice = (letraCodificada + posicion2 - posicion1) + 26;
        } else {
            indice = letraCodificada + posicion2 - posicion1;
        }
        letraCodificada = (char) (new String(rotor2.letras).indexOf(indice) + 65);
        if (letraCodificada + posicion3 - posicion2 > 'Z') {
            indice = (letraCodificada + posicion3 - 'Z') + 64 - posicion2;
        } else if (letraCodificada + posicion3 - posicion2 < 'A') {
            indice = (letraCodificada + posicion3 - posicion2) + 26;
        } else {
            indice = letraCodificada + posicion3 - posicion2;
        }
        letraCodificada = (char) (new String(rotor3.letras).indexOf(indice) + 65);

        if (letraCodificada - posicion3 < 'A') {
            indice = 'Z' + letraCodificada - posicion3 - 64;
        } else {
            indice = letraCodificada - posicion3;
        }
        letraCodificada = (char) (indice);
        //System.out.println("----------- CLAVIJERO -----------");
        letraCodificada = clavija.intercambiarLetra(letraCodificada);
        return letraCodificada;
    }

    public String codificarTexto(String texto) {
        StringBuffer cadena = new StringBuffer();
        for (int i = 0; i < texto.length(); i++) {
            char letra = codificarLetra(Character.toUpperCase(texto.charAt(i)));
            cadena.append(letra);
        }
        return cadena.toString();
    }

    public void aplicarClave(Clave clave){
        this.posicion1 = clave.posicion1;
        this.posicion2 = clave.posicion2;
        this.posicion3 = clave.posicion3;
        this.primeraPulsacion = true;
        this.clavija.letra1 = clave.letra1;
        this.clavija.letra1 = clave.letra2;
    }

    public static void main(String[] args) throws Exception {
        Enigma maquina = new Enigma('A','A','A',1,2,3,'A','A');
        String[] palabras = {"AMBIGUO","OBVIO","TRIVIAL", "ESTUPENDO", "ESTHER", "BUGZILLA", "LUGAR",
                             "PACIFICO", "DIARREA", "HOLA", "MUNDO", "GARABATA", "CALABAZA", "HERPES",
                             "CELULA", "PORRO", "SUAVES", "ALBACETE", "FIESTA", "PATATA"};
        LinkedList<String> listaCodificaciones = new LinkedList<String>();
        LinkedList<String> listaClaves = new LinkedList<String>();
        long inicio = System.currentTimeMillis();
        for(int i=0;i<26;i++){
            for(int j=0;j<26;j++){
                for(int k=0;k<26;k++){
                    for(int a=0;a<26;a++){
                        for(int b=a+1;b<26;b++){
                            maquina.posicion1 = i;
                            maquina.posicion2 = j;
                            maquina.posicion3 = k;
                            maquina.primeraPulsacion = true;
                            maquina.clavija.letra1 = (char) (a+65);
                            maquina.clavija.letra2 = (char) (b+65);
                            String codificacion = maquina.codificarTexto("KHIVQBTCYRFAFWPLVSCAMMRFVDMSIIRRTRZTLAOMWHFQDTOFARWZYVPWPZBNKWAV");
                            for(String palabra : palabras) {
                                if (codificacion.contains(palabra)) {
                                    listaCodificaciones.add(codificacion);
                                    listaClaves.add((char)(i+65)+""+(char)(j+65)+""+(char)(k+65)+""+(char)(a+65)+""+(char) (b+65));
                                }
                            }
                        }
                    }
                }
            }
        }
        for(String codificacion : listaCodificaciones){
                    System.out.println(codificacion);
                    System.out.println(listaClaves.get(listaCodificaciones.indexOf(codificacion)));
        }
        long fin = System.currentTimeMillis();
        double tiempo = (double) ((fin - inicio)/1000);
        System.out.println(listaCodificaciones.size());
        System.out.println(tiempo +" segundos");
    }


}