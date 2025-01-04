import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private Ranking ranking = new Ranking();    // Ranking de jugadores
    private List<String> movieList = new ArrayList<>();   // Lista de peliculas
    private Game game;    // Juego
    private Player player;  // Jugador

    public static void main(String[] args) {
        Main main = new Main();
        main.inicio();
    }

    /**
     * Inicia el juego
     */
    public void inicio(){
        ranking.read();     // Leer el ranking
      cargarPeliculas("peliculas.txt");     // Cargar las peliculas
      if (movieList.isEmpty()) {        // Verificar si hay peliculas
          System.out.println("No se encontraron peliculas");
          return;
      }
        iniciarJuego();     // Iniciar el juego
        jugar();        // Jugar
        finJuego();     // Fin del juego
    }

    /**
     * Carga las peliculas desde un archivo
     * @param archivo Archivo con las peliculas
     */
    public void cargarPeliculas(String archivo){
        File file = new File(archivo);
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (!line.isEmpty()) {
                    movieList.add(line);        // Agregar la pelicula a la lista
                }
            }
            System.out.println("\nPeliculas cargadas desde: " + archivo);
            System.out.println();
        }catch (Exception e){
            System.out.println("\nError al cargar el archivo: " + archivo);
        }
    }

    /**
     * Inicia el juego y crea un nuevo jugador
     */
    public void iniciarJuego(){
        Scanner scanner = new Scanner(System.in);
        System.out.println("\nIngresa tu nombre: ");
        String name = scanner.nextLine();
        player = new Player(name);      // Crear un nuevo jugador

        Random random = new Random();
        String peliculaElegida = movieList.get(random.nextInt(movieList.size()));       // Elegir una pelicula aleatoria
        game = new Game(peliculaElegida);       // Crear un nuevo juego

        System.out.println("\nBienvenido " + player.getName());
        System.out.println("\nAdivina la película: ");
    }

    /**
     * Permite al usuario jugar el juego hasta que se termine o decida salir
     */
    public void jugar(){
        Scanner scanner = new Scanner(System.in);
        boolean continuar = true;
        while (continuar && !game.isGameOver()) {
            game.mostrarProgreso(player);
            System.out.println("\nSelecciona una opcion: ");
            System.out.println("\n1. Adivinar una letra");
            System.out.println("\n2. Adivinar el titulo");
            System.out.println("\n3. Salir");
            System.out.println();

            // Leer la opcion
            int opcion = scanner.nextInt();
            scanner.nextLine();     // Limpiar el buffer

            switch (opcion) {
                case 1:
                    adivinarLetra(scanner);     // Adivinar una letra
                    break;
                case 2:
                    adivinarTitulo(scanner);        // Adivinar el titulo
                    break;
                case 3:
                    System.out.println("Gracias por jugar. Hasta la proxima");
                    continuar = false;      // Salir del juego
                    break;
                default:
                    System.out.println("Opcion no valida");
                    break;
            }
        }
    }

    /**
     * Permite al usuario adivinar una letra
     * @param scanner Scanner para leer la entrada del usuario
     */
    public void adivinarLetra(Scanner scanner) {
        System.out.print("Ingrese una letra: ");
        String entrada = scanner.nextLine();

        // Validar la entrada
        if (entrada.isEmpty() || entrada.length() > 1 || !Character.isLetter(entrada.charAt(0))) {      // Verificar si es una letra
            System.out.println("Letra no válida. Por favor, intenta nuevamente.");
            return;
        }

        char letra = Character.toLowerCase(entrada.charAt(0)); // Convertir a minúscula

        // Actualizar el progreso del juego
        if (game.actualizarProgreso(letra)) {
            System.out.println("¡Letra correcta!");
            player.actualizarScore(10);     // Actualizar la puntuacion
        } else {
            System.out.println("Letra incorrecta.");
            player.actualizarScore(-10);        // Actualizar la puntuacion
        }
    }

    /**
     * Permite al usuario adivinar el titulo de la pelicula
     * @param scanner Scanner para leer la entrada del usuario
     */
    public void adivinarTitulo(Scanner scanner){
        System.out.println("Ingrese el titulo de la pelicula: ");
        String titulo = scanner.nextLine();

        if (titulo.equalsIgnoreCase(game.getTitle())) {
            System.out.println("Felicidades, has adivinado el titulo de la pelicula!");
            player.actualizarScore(20);     // Actualizar la puntuacion
            game.revelarTitulo();       // Revelar el titulo
        }else {
            System.out.println("Titulo incorrecto!");
            player.actualizarScore(-20);        // Actualizar la puntuacion
        }
    }

    /**
     * Muestra el resultado final del juego y actualiza el ranking
     */
    public void finJuego(){
        System.out.println("\nJuego terminado!");
        System.out.println("El titulo era: " + game.getTitle());
        System.out.println("Puntuación final del jugador: " + player.getScore());

        // Verificar si entra en el ranking
        if (entraEnRanking(player.getScore())) {
            System.out.println("Felicidades, has entrado en el ranking!");
            solicitarNombre();      // Solicitar el nombre del jugador
            ranking.actualizarRanking(player);      // Actualizar el ranking
        } else {
            System.out.println("No has entrado en el ranking.");
        }

        // Mostrar el ranking actualizado
        ranking.mostrarRanking();

    }

    /**
     * Verifica si la puntuacion del jugador entra en el ranking
     * @param score Puntuacion del jugador
     * @return true si entra en el ranking, false si no
     */
    public boolean entraEnRanking(int score) {
        if (score <= 5) {       //Si la puntuacion es menor o igual a 5 entra en el ranking
            return false;
        }
        return ranking.getRankingList().size() < 5 || score > ranking.getRankingList().get(ranking.getRankingList().size() - 1).getScore();     // Permite que el jugador entre al ranking si hay espacio disponible, y si su puntuaje es mayor que el del ultimo jugador en el ranking
    }

    /**
     * Solicita un nombre único para el jugador
     */
    public void solicitarNombre() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("Ingresa un nombre único para el ranking: ");
            String nombre = scanner.nextLine();

            boolean existe = false; // Variable para almacenar si el nombre existe

            for (Player p : ranking.getRankingList()) {
                if (p.getName().equalsIgnoreCase(nombre)) {
                    existe = true; // Encontrado marcar como existente
                    break; // Salir del bucle ya que no es necesario continuar
                }
            }

            if (!existe) {
                player = new Player(nombre);        // Crear un nuevo jugador con el nombre
                player.actualizarScore(player.getScore()); // Mantener puntaje actual
                break;
            } else {
                System.out.println("El nombre ya está en el ranking. Por favor, intenta con otro.");
            }
        }
    }
}