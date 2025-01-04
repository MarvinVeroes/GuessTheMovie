import java.io.Serializable;

public class Player implements Serializable {

    private String name;        // Nombre del jugador
    private int score;      // Puntuacion del jugador

    /**
     * Constructor de la clase Player
     * Inicializa los atributos de la clase
     * @param name Nombre del jugador
     */
    public Player(String name) {
        this.name = name;       // Inicializar el nombre
        this.score = 0;     // Inicializar la puntuacion
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    /**
     * Actualiza la puntuacion del jugador
     * @param puntos Puntos a sumar o restar
     */
    public void actualizarScore(int puntos){
        this.score += puntos;
        if (this.score < 0) {
            this.score = 0;
        }
    }

    /**
     * Representacion de la clase Player
     * @return String con los atributos de la clase
     */
    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", score=" + score +
                '}';
    }
}
