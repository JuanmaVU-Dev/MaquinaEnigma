class Enigma {

    Rotor rotor1;
    Rotor rotor2;
    Rotor rotor3;
    Rotor reflector;
    int posicion1;
    int posicion2;
    int posicion3;
    boolean primeraPulsacion;

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

    private void girarRotores() {
        if(primeraPulsacion && rotor2.esLetraClave(posicion2+1)){
            posicion3 = (posicion3 + 1) % 26;
            posicion2 = (posicion2 + 1) % 26;
            posicion1  = (posicion1 +1) % 26;
            System.out.println("Cambio en primera pulsacion");
        }else{
            posicion3 = (posicion3 + 1) % 26;
            if (rotor3.esLetraClave(posicion3)) {
                posicion2 = (posicion2 + 1) % 26;
                System.out.println("gira rotor 2");
                if (rotor2.esLetraClave(posicion2)) {
                    posicion1 = (posicion1 + 1) % 26;
                    System.out.println("gira rotor 1");
                }
            }

            // Doble paso
            if (rotor3.esLetraClave(posicion3 - 1) && rotor2.esLetraClave(posicion2 + 1)) {
                posicion2 = (posicion2 + 1) % 26;
                if (rotor2.esLetraClave(posicion2)) {
                    posicion1 = (posicion1 + 1) % 26;
                    System.out.println("doble paso");
                }
            }
        }
        primeraPulsacion = false;
    }

    public char codificarLetra(char letra) {
        girarRotores();
        int indice;
        //System.out.println("----------- CLAVIJERO -----------");
        //Cambiar una letra por otra
        //System.out.println("----------- IDA -----------");
        char letraCodificada = Rotor.III[(letra - 'A' + posicion3) % 26];

        if (((letraCodificada - 65) - posicion3) >= 0) {
            indice = (letraCodificada - 65) - posicion3;
        } else {
            indice = (letraCodificada - 65 + 26) - posicion3;
        }

        letraCodificada = Rotor.alfabeto[(indice + posicion2) % 26];
        letraCodificada = Rotor.II[(letraCodificada - 'A') % 26];
        if ((letraCodificada - 65) - posicion2 >= 0) {
            indice = (letraCodificada - 65) - posicion2;
        } else {
            indice = (letraCodificada - 65 + 26) - posicion2;
        }
        letraCodificada = Rotor.alfabeto[(indice + posicion1) % 26];

        letraCodificada = Rotor.I[(letraCodificada - 'A') % 26];

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
        letraCodificada = (char) (new String(Rotor.I).indexOf(indice) + 65);
        if ((letraCodificada + posicion2 - posicion1) > 'Z') {
            indice = (letraCodificada + posicion2 - posicion1 - 'Z') + 64;
        } else if ((letraCodificada + posicion2 - posicion1) < 'A') {
            indice = (letraCodificada + posicion2 - posicion1) + 26;
        } else {
            indice = letraCodificada + posicion2 - posicion1;
        }
        letraCodificada = (char) (new String(Rotor.II).indexOf(indice) + 65);
        if (letraCodificada + posicion3 - posicion2 > 'Z') {
            indice = (letraCodificada + posicion3 - 'Z') + 64 - posicion2;
        } else if (letraCodificada + posicion3 - posicion2 < 'A') {
            indice = (letraCodificada + posicion3 - posicion2) + 26;
        } else {
            indice = letraCodificada + posicion3 - posicion2;
        }
        letraCodificada = (char) (new String(Rotor.III).indexOf(indice) + 65);

        if (letraCodificada - posicion3 < 'A') {
            indice = 'Z' + letraCodificada - posicion3 - 64;
        } else {
            indice = letraCodificada - posicion3;
        }
        letraCodificada = (char) (indice);
        //System.out.println("----------- CLAVIJERO -----------");
        return letraCodificada;
    }

    private String codificarTexto(String texto) {
        StringBuffer cadena = new StringBuffer();
        for (int i = 0; i < texto.length(); i++) {
            char letra = codificarLetra(texto.charAt(i));
            cadena.append(letra);
        }
        return cadena.toString();
    }

    public static void main(String[] args) {
        Enigma maquina = new Enigma('A' - 65, 'E' - 65, 'V' - 65);
        String texto = maquina.codificarTexto("AAAAAAAAAAA");
        System.out.println(texto.equals("GIBMGFJBWZF"));
        System.out.println(texto);
    }
}