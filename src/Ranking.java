import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Ranking {
    private static final String RANKING_FILE = "ranking.txt";       // Archivo de ranking
    private List<Player> rankingList;       // Lista de jugadores

    /**
     * Constructor de la clase Ranking
     * Inicializa la lista para el ranking
     */
    public Ranking() {
        rankingList = new ArrayList<>();
    }

    /**
     * Guarda el ranking en un archivo binario
     */
    public void write(){
        try (FileOutputStream fileOut = new FileOutputStream(RANKING_FILE);
             ObjectOutputStream output = new ObjectOutputStream(fileOut)) {
            for (Player player : rankingList) {
                output.writeObject(player);     // Escribir el jugador
            }
            System.out.println("Ranking guardado en: " + RANKING_FILE);
        }catch (Exception e){
            System.out.println("Error al guardar el ranking");
        }
    }

    /**
     * Lee el ranking desde un archivo binario
     */
    public void read() {
        rankingList = new ArrayList<>();
        File file = new File(RANKING_FILE);
        if (!file.exists()) {
            System.out.println("No se encontro el archivo de ranking");
            return;
        }
        System.out.println("Leyendo ranking...");
        try (FileInputStream fileIn = new FileInputStream(RANKING_FILE);
             ObjectInputStream input = new ObjectInputStream(fileIn)) {
            while (true) {
                Player player = (Player) input.readObject();
                rankingList.add(player);
            }
        } catch (EOFException e) {
            System.out.println("Ranking leido correctamente");
        } catch (Exception e) {
            System.out.println("Error al leer el ranking");
        }
    }

    /**
     * Actualiza el ranking con un nuevo jugador.
     * Si el nombre del jugador ya existe, se actualiza su puntaje si el nuevo es mayor.
     * Si hay más de 5 jugadores en el ranking, elimina el último jugador.
     * @param player El jugador a agregar o actualizar en el ranking.
     */
    public void actualizarRanking(Player player) {
        boolean jugadorActualizado = false;

        // Verifica si el jugador ya existe en el ranking
        for (Player p : rankingList) {
            if (p.getName().equalsIgnoreCase(player.getName())) {
                if (player.getScore() > p.getScore()) {
                    // Actualizar puntaje
                    p.actualizarScore(player.getScore() - p.getScore());
                }
                jugadorActualizado = true; // el jugador fue encontrado
                break;
            }
        }

        // Si el jugador no estaba en el ranking, agregarlo
        if (!jugadorActualizado) {
            rankingList.add(player);
        }

        // Ordenar el ranking de mayor a menor puntaje
        rankingList.sort((p1, p2) -> Integer.compare(p2.getScore(), p1.getScore()));

        // Mantener solo las 5 mejores puntuaciones
        if (rankingList.size() > 5) {
            rankingList.remove(rankingList.size() - 1);
        }

        // Guardar el ranking actualizado
        write();
    }

    /**
     * Muestra el ranking
     */
    public void mostrarRanking(){

        if (rankingList.isEmpty()) {
            System.out.println("No hay jugadores en el ranking");
            return;
        }

        System.out.println("Ranking: ");
        for (int i = 0; i < rankingList.size(); i++) {
            Player player = rankingList.get(i);
            System.out.println((i + 1) + ". " + player.getName() + " - Puntuacion " + player.getScore());
        }
    }
    public List<Player> getRankingList() {
        return rankingList;
    }

}
