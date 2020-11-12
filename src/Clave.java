public class Clave {
    int posicion1;
    int posicion2;
    int posicion3;

    char letra1;
    char letra2;

    @Override
    public String toString() {
        return "Clave{" +
                "posicionRotores=" + posicion1 + posicion2 + posicion3 +
                ", Clavija = " + letra1 +
                               + letra2 +
                '}';
    }
}
