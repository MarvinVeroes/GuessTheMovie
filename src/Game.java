import java.util.HashSet;
import java.util.Set;

public class Game {

    private String title;       // Titulo de la película
    private StringBuilder progress;     // Progreso del juego
    private Set<Character> guessLetters;        // Letras correctas
    private Set<Character> wrongLetters;        // Letras incorrectas
    private int attempts;       // Intentos restantes
    private int score;      // Puntuacion

    /**
     * Constructor de la clase Game
     * Inicializa los atributos de la clase
     * @param title Titulo de la película
     */

    public Game(String title) {
        this.title = title.toLowerCase();       // Convertir a minusculas
        this.progress = new StringBuilder();        // Inicializar el progreso
        this.guessLetters = new HashSet<>();        // Inicializar las letras correctas
        this.wrongLetters = new HashSet<>();        // Inicializar las letras incorrectas
        this.attempts = 10;     // Inicializar los intentos
        this.score = 0;     // Inicializar la puntuacion

        verificarProgreso();        // Representa el progreso

    }

    /**
     * Verifica el progreso del juego
     * Reemplaza las letras por '*'
     */
    public void verificarProgreso(){
        for (int i = 0; i < title.length(); i++) {      // Recorrer el titulo de a pelicula
            char c = title.charAt(i);
            if (Character.isLetter(c)) {        // Verificar si es una letra
                this.progress.append("*");      // Reemplazar por '*'
            } else {
                this.progress.append(c);
            }
        }
    }

    /**
     * Actualiza el progreso del juego
     * @param guessLetter Letra ingresada por el usuario
     * @return true si la letra es correcta, false si es incorrecta
     */
    public boolean actualizarProgreso(char guessLetter) {
        guessLetter = Character.toLowerCase(guessLetter);       // Convertir a minuscula

        // Verificar si la letra ya fue ingresada
        if (guessLetters.contains(guessLetter) || wrongLetters.contains(guessLetter)) {
            System.out.println("Ya has intentado con esta letra");
            return false;
        }

        // Verificar si la letra es correcta
        if (title.contains(String.valueOf(guessLetter))) {
            for (int i = 0; i < title.length(); i++) {
                if (title.charAt(i) == guessLetter) {
                    progress.setCharAt(i, guessLetter);
                }
            }
            guessLetters.add(guessLetter);    // Agregar la letra a las letras correctas
            return true;
        } else {
            wrongLetters.add(guessLetter);    // Agregar la letra a las letras incorrectas
            attempts--;    // Decrementar los intentos
            return false;
        }
    }

    /**
     * Muestra el progreso del juego
     * @param player Jugador
     */
    public void mostrarProgreso(Player player) {
        System.out.println("Progreso: " + progress);    // Mostrar el progreso
        System.out.println("Intentos restantes: " + attempts);  // Mostrar los intentos restantes
        System.out.println("Letras incorrectas: " + wrongLetters);  // Mostrar las letras incorrectas
        System.out.println("Puntuación: " + player.getScore()); // Mostrar la puntuacion
    }

    /**
     * Revela el titulo de la película
     */
    public void revelarTitulo(){
        progress = new StringBuilder(title);    // Revelar el titulo
    }

    /**
     * Verifica si el juego ha terminado
     * @return true si el juego ha terminado, false si no
     */
    public boolean isGameOver() {
        return attempts <= 0 || progress.toString().equalsIgnoreCase(title);    // Verificar si los intentos son menores o iguales a 0 o si el progreso es igual al titulo
    }

    /**
     * Obtiene el titulo de la película
     * @return Titulo de la película
     */
    public String getTitle() {
        return title;
    }

    // Getters

    public String getProgress() {
        return progress.toString();
    }

    public int getAttempts() {
        return attempts;
    }

    public int getScore() {
        return score;
    }

}
